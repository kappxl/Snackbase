package de.kaikappel.snackbase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    ArrayList<Meal> mealList;
    ArrayList<Ingredient> ingredientList;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    // LOAD FROM DATABASE

    public ArrayList<Meal> getAllMeals() {

        mealList = new ArrayList<>();
        String query = "SELECT * " +
                "FROM meals m ORDER BY name;";
        Cursor cMeals = db.rawQuery(query, new String[]{});

        while(cMeals.moveToNext()) {
            int meal_id = cMeals.getInt(0); // id
            Meal meal = new Meal(meal_id,
                    cMeals.getString(1),    // name
                    cMeals.getInt(2),       // breakfast
                    cMeals.getInt(3),       // lunch
                    cMeals.getInt(4));      // dinner

            ArrayList<Ingredient> i = getIngredientsFromMeal(meal_id);
            meal.addAllIngredients(i);

            mealList.add(meal);
        }
        cMeals.close();

        return mealList;
    }

    public ArrayList<Ingredient> getIngredientsFromMeal(int id) {
        ArrayList<Ingredient> recipeList = new ArrayList<>();
        String query = "SELECT i.*, r.amount FROM recipes r " +
                "JOIN ingredients i ON i.id = r.ingredient_id " +
                "WHERE meal_id = '" + id + "';";

        Cursor cRecipe = db.rawQuery(query, new String[]{});

        while(cRecipe.moveToNext()) {
            float grams = cRecipe.getFloat(6);
            recipeList.add(new Ingredient(
                    cRecipe.getInt(0),      // id
                    cRecipe.getString(1),   // name
                    cRecipe.getFloat(2),    // cals
                    cRecipe.getFloat(3),    // carbs
                    cRecipe.getFloat(4),    // protein
                    cRecipe.getFloat(5),    // fat
                    grams,                              // grams
                    cRecipe.getFloat(7)));  // amount
        }
        cRecipe.close();
        return recipeList;
    }

    public ArrayList<Meal> getSelectedMeals(String meals) {
        mealList = new ArrayList<>();
        String query = "SELECT * " +
                "FROM meals " +
                "WHERE id IN (" + meals + ");";

        Cursor selectedMeals = db.rawQuery(query, new String[]{});

        while(selectedMeals.moveToNext()) {
            Meal meal = new Meal(selectedMeals.getInt(0),
                    selectedMeals.getString(1),
                    selectedMeals.getInt(2),
                    selectedMeals.getInt(3),
                    selectedMeals.getInt(4));
            ArrayList<Ingredient> recipe = getIngredientsFromMeal(selectedMeals.getInt(0));

            meal.addAllIngredients(recipe);
            mealList.add(meal);
        }

        selectedMeals.close();

        return mealList;
    }

    public ArrayList<Ingredient> getAllIngredients() {
        ingredientList = new ArrayList<>();

        Cursor getIngredients = db.rawQuery("SELECT * FROM ingredients;", new String[]{});
        while(getIngredients.moveToNext()) {
            float fGrams = getIngredients.getFloat(6);
            ingredientList.add(new Ingredient(getIngredients.getInt(0),
                    getIngredients.getString(1),
                    getIngredients.getFloat(2),
                    getIngredients.getFloat(3),
                    getIngredients.getFloat(4),
                    getIngredients.getFloat(5),
                    fGrams,
                    1));
        }
        getIngredients.close();
        return ingredientList;
    }

    // INSERT INTO DATABASE

    public boolean insertMeal(MaskCreateMeal meal) {

        // DEBUGGING ONLY

        // db.execSQL("DELETE FROM meals WHERE id > 13;");
        // db.execSQL("DELETE FROM recipes WHERE meal_id > 13;");

        // INSERT MEAL

        String query = "INSERT INTO meals (name, breakfast, lunch, dinner) " +
                "VALUES ('"+meal.getName()+"',"+
                "'"+meal.getBreakfast()+"', " +
                "'"+meal.getLunch()+"', " +
                "'"+meal.getDinner()+"');";

        db.execSQL(query);

        // GET ID FROM INSERTED MEAL

        Cursor cIdx = db.rawQuery("SELECT last_insert_rowid()", new String[]{});
        int meal_id;

        if(cIdx.moveToFirst()) {
            meal_id = cIdx.getInt(0);
            Log.d("Create Meal", "inserted meal id: " + meal_id);
        } else {
            Log.e("Create Meal", "no id found for inserted meal. can't insert ingredients.");
            return false;
        }

        cIdx.close();

        // ADD RECIPE FOR ALL INGREDIENTS OF INSERTED MEAL

        String insertIngredient;
        for(Ingredient i: meal.getIngredientList()) {
            insertIngredient = "INSERT INTO recipes (meal_id, ingredient_id, amount) VALUES (" +
                    "'" + meal_id + "', " +
                    "'" + i.getId() + "', " +
                    "'" + 1 + "')";

            db.execSQL(insertIngredient);
        }

        return true;
    }
}