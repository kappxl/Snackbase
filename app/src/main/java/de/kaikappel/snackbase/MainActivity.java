package de.kaikappel.snackbase;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private static  final String SHARED_PREFS = "sharedPrefs";
    ArrayList<Food> mealList;
    ArrayList<Food> selectedMealList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        loadSelectedMeals();
        loadAllMeals();

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
                        case R.id.nav_create:
                            selectedFragment = new MealsFragment();
                            break;
                        case R.id.nav_favs:
                            selectedFragment = new FavsFragment();
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

    public void goHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    public void goFoodList() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FoodFragment()).commit();
    }

    public ArrayList<Food> getMealList() { return mealList; }
    public ArrayList<Food> getSelectedMealList() { return selectedMealList; }

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

    public void appendMeal(int mealId) {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String meals = prefs.getString("meals", "");
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
}
