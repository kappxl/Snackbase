package de.kaikappel.snackbase;

import java.util.ArrayList;

public class Meal {
    String name;
    int id;
    int grams, cals;
    float carbs, protein, fat;
    int breakfast, lunch, dinner;
    private ArrayList<Food> ingredients;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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


    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public Meal (int id, String name, int breakfast, int lunch, int dinner) {
        this.id = id;
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        ingredients = new ArrayList<>();
    }

    public Meal (String name, int breakfast, int lunch, int dinner) {
        this.id = -1;
        this.name = name;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        ingredients = new ArrayList<>();
    }

    public void addIngredient(Food ingredient) {
        this.ingredients.add(ingredient);
        update();
    }

    public void addIngredient(Food ingredient, float amount) {
        ingredient.setAmount(amount);
        this.ingredients.add(ingredient);
        update();
    }

    public void addAllIngredients(ArrayList<Food> ingredients) {
        this.ingredients.addAll(ingredients);
        update();
    }

    public void update () {
        cals=0;grams=0;
        carbs=0;protein=0;fat=0;
        float calories = 0;
        for (Food x: ingredients) {
            float ixGrams = (float) x.getGrams() * x.getAmount();

            calories += ((float) x.getCals() / (float) 100 * ixGrams);
            this.carbs += x.getCarbs() / (float) 100 * ixGrams;
            this.protein += x.getProtein() / (float) 100 * ixGrams;
            this.fat += x.getFat() / (float) 100 *  ixGrams;
            this.grams += (int) ixGrams;
        }
        cals = (int) calories;
    }

    public void removeIngredient (float amount, Food ingredient) {
        for (Food x: ingredients) {
            if (x == ingredient) {
                if ((x.getAmount() - amount) <= 0) {
                    ingredients.remove(ingredients.indexOf(x));
                    update();
                } else {
                    x.setAmount(x.getAmount() - amount);
                    update();
                }
            }
        }
    }

    public void removeIngredient (Food ingredient) {
        for (Food x: ingredients) {
            if (x == ingredient) {
                ingredients.remove(ingredients.indexOf(x));
                update();
            }
        }
    }
}
