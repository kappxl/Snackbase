package de.kaikappel.snackbase;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private static  final String SHARED_PREFS = "sharedPrefs";
    ArrayList<Meal> fullMealList;
    ArrayList<Meal> selectedMealList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<Ingredient> cmIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // VARIABLES

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // LOAD LISTS

        loadAllMeals();
        loadSelectedMeals();
        loadAllIngredients();
        //clearMeals();

        // CREATE VIEW

        goHome();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_favs:
                            selectedFragment = new FavsFragment();
                            break;
                        case R.id.nav_create:
                            resetCreateMealList();
                            selectedFragment = new CreateFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    // LOAD FROM DATABASE

    public void loadAllMeals() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        fullMealList = databaseAccess.getAllMeals();
        databaseAccess.close();
    }

    public void loadSelectedMeals() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String meals = prefs.getString("meals", "");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        selectedMealList = databaseAccess.getSelectedMeals(meals);

        databaseAccess.close();
    }

    public void loadAllIngredients() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        ingredientList = databaseAccess.getAllIngredients();

        databaseAccess.close();
    }

    // HANDLE FOOD LISTS

    public void resetCreateMealList() {
        cmIngredients = new ArrayList<>();
    }

    public void appendMeal(int mealId) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String meals = prefs.getString("meals", "");
        if (meals.contains("'" + mealId + "'")) {
            Toast.makeText(this, "already added", Toast.LENGTH_SHORT).show();
            return;
        }
        String newMeals;

        if (meals.isEmpty()){
            newMeals = "'" + mealId + "'";
        } else {
            newMeals = meals + ", '" + mealId + "'";
        }
        editor.putString("meals", newMeals);
        editor.apply();
        loadSelectedMeals();
    }

    public void popSelectedMeal(int id) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String meals = prefs.getString("meals", "");
        meals = meals.replaceAll("'" + id + "', ", "");
        meals = meals.replaceAll(", '" + id + "'", "");
        meals = meals.replaceAll("'" + id + "'", "");
        editor.putString("meals", meals);
        editor.apply();
        loadSelectedMeals();
    }

    public void popSelectedMealInCreateMeal(int i) {
            cmIngredients.remove(i);
        }

    // GETTER OF LISTS

    public ArrayList<Meal> getFullMealList() { return fullMealList; }
    public ArrayList<Meal> getSelectedMealList() { return selectedMealList; }
    public ArrayList<Ingredient> getIngredientList() { return ingredientList; }
    public ArrayList<Ingredient> getCreateMealIngredientList() { return cmIngredients; }

/*    public ArrayList<Food> getIngredientList() { return ingredientList; }
    */


/*    public void addCreateMeal(Food food) {
        cmIngredients.add(food);
    }

    public ArrayList<Food> getCmIngredients() {
        return cmIngredients;
    }*/

    // NAVIGATION

    public void goCreate() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CreateFragment()).commit();
    }

    public void goHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    public void goFoodList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FoodFragment()).commit();
    }

    public void goIngredients() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new IngredientsFragment()).commit();
    }

    // DEBUGGING ONLY

    public void clearMeals() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("meals", "");
        editor.apply();
    }

    public void showMeals() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String meals = prefs.getString("meals", "");
        Toast.makeText(this, meals, Toast.LENGTH_SHORT).show();
    }
}
