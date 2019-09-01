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
    ArrayList<Meal> mealList;
    ArrayList<Food> ingredientList;
    ArrayList<Meal> selectedMealList;
    ArrayList<Food> createMealList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //clearMeals();
        loadSelectedMeals();
        loadAllMeals();
        loadAllIngredients();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
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

    public void goCreate() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new CreateFragment()).commit();
    }

    public void goHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    public void goFoodList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FoodFragment()).commit();
    }

    public void goIngredients() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new IngredientsFragment()).commit();
    }

    public ArrayList<Meal> getMealList() { return mealList; }
    public ArrayList<Food> getIngredientList() { return ingredientList; }
    public ArrayList<Meal> getSelectedMealList() { return selectedMealList; }

    public void loadSelectedMeals() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String meals = prefs.getString("meals", "");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        selectedMealList = databaseAccess.getMeals(meals);

        databaseAccess.close();
    }

    public void loadAllMeals() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        mealList = databaseAccess.getMeals();

        databaseAccess.close();
    }

    public void loadAllIngredients() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        ingredientList = databaseAccess.getIngredients();

        databaseAccess.close();
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

    public void popMeal(int id) {
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

    public void addCreateMeal(Food food) {
        createMealList.add(food);
    }

    public void popCreateMeal(int i) {
        createMealList.remove(i);
    }

    public ArrayList<Food> getCreateMealList() {
        return createMealList;
    }

    public void resetCreateMealList() {
        createMealList = new ArrayList<>();
    }
}
