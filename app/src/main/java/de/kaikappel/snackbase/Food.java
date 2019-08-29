package de.kaikappel.snackbase;

public class Food {
    private int id;
    private String name;
    private int cals;
    private float carbs;
    private float protein;
    private float fat;
    private int grams;

    public Food(String name, int id, int cals, float carbs, float protein, float fat, int grams) {
        this.name = name;
        this.id = id;
        this.cals = cals;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.grams = grams;
    }

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

    public int getGrams() {
        return grams;
    }
}
