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
    ArrayList<Food> foodList;
    ArrayList<Meal> mealList;

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
            recipeList.add(new Ingredient(
                    cRecipe.getInt(0),      // id
                    cRecipe.getString(1),   // name
                    cRecipe.getInt(2),      // cals
                    cRecipe.getFloat(3),    // carbs
                    cRecipe.getFloat(4),    // protein
                    cRecipe.getFloat(5),    // fat
                    cRecipe.getInt(6),      // grams
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
        ArrayList<Ingredient> ingredientList = new ArrayList<>();

        Cursor getIngredients = db.rawQuery("SELECT * FROM ingredients;", new String[]{});
        while(getIngredients.moveToNext()) {

            ingredientList.add(new Ingredient(getIngredients.getInt(0),
                    getIngredients.getString(1),
                    getIngredients.getInt(2),
                    getIngredients.getFloat(3),
                    getIngredients.getFloat(4),
                    getIngredients.getFloat(5),
                    getIngredients.getInt(6),
                    -1));

/*            foodList.add(new Food(getIngredients.getString(1),
                    getIngredients.getInt(0),
                    (int)(iCals / (float) 100 * (float) iGrams),
                    fCarbs / (float) 100 * (float) iGrams,
                    fProtein / (float) 100 * (float) iGrams,
                    fFat / (float) 100 * (float) iGrams,
                    iGrams));*/
        }
        getIngredients.close();
        return ingredientList;
    }

    public void createIngredient(String name, int iCals, float fCarbs, float fProtein, float fFat, int iGrams) {
        String query = "INSERT INTO ingredient (name, cals, carbs, protein, fat, grams) " +
                "VALUES ('"+name+"', '"+iCals+"', '"+fCarbs+"', '"+fProtein+"', '"+fFat+"', '"+iGrams+"');";
        Cursor insertIngredient = db.rawQuery(query, new String[]{});
        if (insertIngredient.moveToFirst()){
            Log.e("Create Ingredient", "Result of insertion is not empty.");
        }
    }

/*    public void createMeal(Meal meal) {
        db.execSQL("DELETE FROM meals WHERE id > 13;");
        db.execSQL("DELETE FROM recipes WHERE meal_id > 13;");

        ContentValues mealValues = new ContentValues();
        mealValues.put("name", meal.getName());
        mealValues.put("breakfast", meal.getBreakfast());
        mealValues.put("lunch", meal.getLunch());
        mealValues.put("dinner", meal.getLunch());

        try {
            db.beginTransaction();
            db.insert("meals", null, mealValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        Cursor cIdx = db.rawQuery("SELECT max(id) FROM meals;", new String[]{});
        cIdx.moveToFirst();
        int meal_id = cIdx.getInt(0);
        meal.setId(meal_id);
        String query = "INSERT INTO meals (name, breakfast, lunch, dinner) " +
                "VALUES ('"+meal.getName()+"', '"+meal.getBreakfast()+"', '"+meal.getLunch()+"', '"+meal.getDinner()+"');";
        db.execSQL(query);



        for(Food x: meal.getIngredients()) {
            query = "INSERT INTO recipes (meal_id, ingredient_id, amount) " +
                    "VALUES ('"+ meal.getId() +"', '"+x.getId()+"', '"+x.getAmount()+"');";
            db.execSQL(query);
        }
    }*/
}
