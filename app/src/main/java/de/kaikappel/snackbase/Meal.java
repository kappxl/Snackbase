package de.kaikappel.snackbase;

import java.util.ArrayList;

public class Meal {
    private String name;
    private int id;
    private int grams, cals;
    private float carbs, protein, fat;
    private int breakfast, lunch, dinner;
    private ArrayList<Ingredient> ingredients;

    public Meal (int id, String name, int breakfast, int lunch, int dinner) {
        this.id = id;
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        ingredients = new ArrayList<>();
    }

    public void addAllIngredients(ArrayList<Ingredient> i) {
        this.ingredients.addAll(i);
        recalculateValues();
    }

    public void recalculateValues() {
        this.grams=0;this.cals=0;
        this.carbs=0;this.protein=0;this.fat=0;

        for(Ingredient i: this.ingredients) {
            this.cals += i.getCalsAbs();
            this.carbs += i.getCarbsAbs();
            this.protein += i.getProteinAbs();
            this.fat += i.getFatAbs();
            this.cals += i.getCalsAbs();
            this.grams += i.getGramsAbs();
        }
    }

    // GETTER

    public int getGrams() {
        return grams;
    }

    public int getCals() {
        return cals;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }

    public float getFat() {
        return fat;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
}
