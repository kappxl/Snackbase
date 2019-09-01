package de.kaikappel.snackbase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

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

    public ArrayList<Meal> getMeals(String meals) {
        mealList = new ArrayList<>();
        String query = "SELECT * " +
                "FROM meals m " +
                "WHERE m.id IN (" + meals + ");";

        Cursor selectedMeals = db.rawQuery(query, new String[]{});

        //"WHERE m.breakfast = '1' " +
        // Wichtig die Gaensefuesschen!!!

        while(selectedMeals.moveToNext()) {
            Meal meal = new Meal(selectedMeals.getInt(0),
                    selectedMeals.getString(1),
                    selectedMeals.getInt(2),
                    selectedMeals.getInt(3),
                    selectedMeals.getInt(4));
            ArrayList<Food> recipe = getRecipe(selectedMeals.getInt(0));

            meal.addAllIngredients(recipe);
            mealList.add(meal);
        }

        return mealList;
    }

    public ArrayList<Food> getRecipe(int id) {
        ArrayList<Food> recipeList = new ArrayList<>();
        String query = "SELECT i.*, r.amount FROM recipes r " +
                "JOIN ingredients i ON i.id = r.ingredient_id " +
                "WHERE meal_id = '" + id + "';";

        Cursor cRecipe = db.rawQuery(query, new String[]{});

        while(cRecipe.moveToNext()) {
            recipeList.add(new Food(cRecipe.getString(1),
                    cRecipe.getInt(0),
                    cRecipe.getInt(2),
                    cRecipe.getFloat(3),
                    cRecipe.getFloat(4),
                    cRecipe.getFloat(5),
                    cRecipe.getInt(6),
                    cRecipe.getFloat(7)));
        }
        return recipeList;
    }

    public ArrayList<Meal> getMeals() {
        db.execSQL("DELETE FROM meals WHERE id > '13';", new String[]{});
        db.execSQL("DELETE FROM recipes WHERE meal_id > '13';", new String[]{});
        mealList = new ArrayList<>();
        String query = "SELECT * " +
                "FROM meals m ORDER BY name;";
        Cursor cMeals = db.rawQuery(query, new String[]{});

        while(cMeals.moveToNext()) {
            int meal_id = cMeals.getInt(0);
            Meal meal = new Meal(meal_id,
                    cMeals.getString(1),
                    cMeals.getInt(2),
                    cMeals.getInt(3),
                    cMeals.getInt(4));
            ArrayList<Food> recipe = getRecipe(meal_id);
            for(Food x: recipe) {
                meal.addIngredient(x);
            }

            mealList.add(meal);
        }

        return mealList;
    }

    public ArrayList<Food> getIngredients() {
        foodList = new ArrayList<>();

        Cursor getIngredients = db.rawQuery("SELECT * FROM ingredients;", new String[]{});
        while(getIngredients.moveToNext()) {
            int iCals = getIngredients.getInt(2);
            float fCarbs = getIngredients.getFloat(3);
            float fProtein = getIngredients.getFloat(4);
            float fFat = getIngredients.getFloat(5);
            int iGrams = getIngredients.getInt(6);
            foodList.add(new Food(getIngredients.getString(1),
                    getIngredients.getInt(0),
                    (int)(iCals / (float) 100 * (float) iGrams),
                    fCarbs / (float) 100 * (float) iGrams,
                    fProtein / (float) 100 * (float) iGrams,
                    fFat / (float) 100 * (float) iGrams,
                    iGrams));
        }
        return foodList;
    }

    public void createIngredient(String name, int iCals, float fCarbs, float fProtein, float fFat, int iGrams) {
        String query = "INSERT INTO ingredient (name, cals, carbs, protein, fat, grams) " +
                "VALUES ('"+name+"', '"+iCals+"', '"+fCarbs+"', '"+fProtein+"', '"+fFat+"', '"+iGrams+"');";
        Cursor insertIngredient = db.rawQuery(query, new String[]{});
        if (insertIngredient.moveToFirst()){
            Log.e("Create Ingredient", "Result of insertion is not empty.");
        }
    }

    public void createMeal(Meal meal) {
        Cursor cIdx = db.rawQuery("SELECT max(id) FROM meals;", new String[]{});
        cIdx.moveToFirst();
        int food_id = cIdx.getInt(0) + 1;

        String query = "INSERT INTO meals (id, name, breakfast, lunch, dinner) " +
                "VALUES ('" + food_id + "', '"+meal.getName()+"', '"+meal.breakfast+"', '"+meal.lunch+"', '"+meal.dinner+"');";
        db.execSQL(query, new String[]{});


        for(Food x: meal.getIngredients()) {
            query = "INSERT INTO recipes (meal_id, ingredient_id, amount) " +
                    "VALUES ('"+ food_id +"', '"+x.getId()+"', '"+x.getAmount()+"');";
            db.execSQL(query, new String[]{});
        }
    }
}
