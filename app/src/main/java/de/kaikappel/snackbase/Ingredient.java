package de.kaikappel.snackbase;

public class Ingredient {
    private int id;
    private String name;
    private float cals;
    private float carbs;
    private float protein;
    private float fat;
    private float grams;
    private float amount;

    // If amount not known, use 1
    //  - Special case when loading all ingredients
    //    in an meal independent list
    public Ingredient(int id, String name, float cals, float carbs, float protein, float fat, float grams, float amount) {
        this.id = id;
        this.name = name;
        this.cals = cals;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.grams = grams;
        this.amount = grams / (float) 100 * amount;

    }

    // GETTER OF ABSOLUTE CALCULATED VALUES

    public String getName() {
        return name;
    }
    public float getCalsAbs() {
        return cals * amount;
    }
    public float getCarbsAbs() {
        return carbs * amount;
    }
    public float getProteinAbs() {
        return protein * amount;
    }
    public float getFatAbs() {
        return fat * amount;
    }
    public float getGramsAbs() {
        return grams * amount;
    }
}
