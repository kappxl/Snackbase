package de.kaikappel.snackbase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;
    ArrayList<Food> foodList = null;

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

    public ArrayList<Food> getMeals() {
        foodList = new ArrayList<>();
        c = db.rawQuery("SELECT m.name, m.id as ID, " +
                "SUM((CAST(i.cals AS FLOAT) / 100) * (i.grams * r.amount)) as cals, " +
                "SUM((i.carbs / 100) * (i.grams * r.amount)) as carbs, " +
                "SUM((i.protein / 100) * (i.grams * r.amount)) as protein, " +
                "SUM((i.fat / 100) * (i.grams * r.amount)) as fat, " +
                "SUM(i.grams * r.amount) as grams " +
                "FROM recipes r " +
                "JOIN ingredients i ON i.id = r.ingredient_id " +
                "JOIN meals m ON m.id = r.food_id " +
                "GROUP BY m.name, m.id", new String[]{});

        //"WHERE m.breakfast = '1' " +

        while(c.moveToNext()) {
            foodList.add(new Food(c.getString(0), c.getInt(2),
                    c.getFloat(3), c.getFloat(4),
                    c.getFloat(5), c.getInt(6)));
        }

        return foodList;
    }

    public String getName(String id) {
        c = db.rawQuery("SELECT name FROM meals WHERE id = '" + id + "'", new String[]{});
        while(c.moveToNext()) {
            return c.getString(0);
        }
        return "No result";
    }
}
