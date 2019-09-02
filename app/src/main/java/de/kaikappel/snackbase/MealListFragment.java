package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MealListFragment extends Fragment implements IOnBackPressed {

    ListView listView;
    ArrayList<Meal> mealList;
    MealListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_meals, container, false);
        setHasOptionsMenu(true);

        // VARIABLES

        listView = view.findViewById(R.id.mealListView);

        // CREATE VIEW

        loadListView();

        // LISTENER

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).appendMeal(mealList.get(position).getId());
                ((MainActivity) getActivity()).goHome();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO: Expand and show ingredients from Meal

                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.filterMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();

        // SEARCH VIEW (MENU)

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Meal> filteredMeals = new ArrayList<>();

                for(Meal x: mealList) {
                    if (x.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredMeals.add(x);
                    }
                }
                adapter = new MealListAdapter(getActivity().getApplicationContext(),
                        R.layout.food_list_layout, filteredMeals);
                listView.setAdapter(adapter);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goHome();
        return true;
    }

    // CREATE VIEW

    public void loadListView() {
        mealList = ((MainActivity) getActivity()).getFullMealList();

        adapter = new MealListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, mealList);
        listView.setAdapter(adapter);
    }
}
