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

public class MealsFragment extends Fragment implements IOnBackPressed {

    ListView listView;
    ArrayList<Meal> mealList;
    MealListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_meals, container, false);
        setHasOptionsMenu(true);
        listView = view.findViewById(R.id.mealListView);

        mealList = ((MainActivity) getActivity()).getMealList();

        adapter = new MealListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, mealList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).appendMeal(mealList.get(position).getId());
                ((MainActivity) getActivity()).showMeals();
                ((MainActivity) getActivity()).goHome();
                //Toast.makeText(getActivity().getApplicationContext(), foodList.get(position).getName() + " added", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.filterMenu);
        SearchView searchView = (SearchView) menuItem.getActionView();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Meal> filteredFoods = new ArrayList<>();

                for(Meal x: mealList) {
                    if (x.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredFoods.add(x);
                    }
                }
                adapter = new MealListAdapter(getActivity().getApplicationContext(),
                        R.layout.food_list_layout, filteredFoods);
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
}
