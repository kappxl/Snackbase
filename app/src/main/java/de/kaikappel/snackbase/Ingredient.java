package de.kaikappel.snackbase;

public class Ingredient {
    private int id;
    private String name;
    private int cals;
    private float carbs;
    private float protein;
    private float fat;
    private int grams;
    private float amount;

    public Ingredient(int id, String name, int cals, float carbs, float protein, float fat, int grams, float amount) {
        this.id = id;
        this.name = name;
        this.cals = cals;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.grams = grams;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    // GETTER OF ABSOLUTE CALCULATED VALUES

    public int getCalsAbs() {
        return (int)(((float) this.cals) / ((float) 100) * (this.amount * this.grams));
    }
    public float getCarbsAbs() {
        return (this.carbs / ((float) 100) * (this.amount * this.grams));
    }
    public float getProteinAbs() {
        return (this.protein / ((float) 100) * (this.amount * this.grams));
    }
    public float getFatAbs() {
        return (this.fat / ((float) 100) * (this.amount * this.grams));
    }
    public int getGramsAbs() {
        return (int)(this.amount * this.grams);
    }

}
