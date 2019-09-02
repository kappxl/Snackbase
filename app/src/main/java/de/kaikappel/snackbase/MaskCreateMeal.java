package de.kaikappel.snackbase;

import java.util.ArrayList;

public class MaskCreateMeal {
    private String name;
    ArrayList<Ingredient> ingredientList;
    int breakfast, lunch, dinner;

    public MaskCreateMeal() {
        name = "";
        ingredientList = new ArrayList<>();
        breakfast=0;
        lunch=0;
        dinner=0;
    }

    public void save(String name, ArrayList<Ingredient> ingredients, boolean breakfast, boolean lunch, boolean dinner) {
        this.name = name;
        this.ingredientList = ingredients;
        this.breakfast = breakfast ? 1 : 0;
        this.lunch = lunch ? 1 : 0;
        this.dinner = dinner ? 1 : 0;
    }

    // GETTER

    public String getName() {
        return name;
    }
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }
    public boolean getBreakfast() {
        return (breakfast > 0 ? true : false);
    }
    public boolean getLunch() {
        return (lunch > 0 ? true : false);
    }
    public boolean getDinner() {
        return (dinner > 0 ? true : false);
    }

    // FUNCTIONS

    public void addIngredient(Ingredient i) {
        this.ingredientList.add(i);
    }
    public void popIngredient(int index) {
        ingredientList.remove(index);
    }
}
