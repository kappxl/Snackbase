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
import java.util.Map;

public class IngredientsFragment extends Fragment implements IOnBackPressed {

    ListView listView;
    ArrayList<Food> foodList;
    FoodListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        setHasOptionsMenu(true);
        listView = view.findViewById(R.id.ingredientListView);

        foodList = ((MainActivity) getActivity()).getIngredientList();

        adapter = new FoodListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, foodList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).addCreateMeal(foodList.get(position));
                ((MainActivity) getActivity()).goCreate();
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

                ArrayList<Food> filteredFoods = new ArrayList<>();

                for(Food x: foodList) {
                    if (x.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredFoods.add(x);
                    }
                }
                adapter = new FoodListAdapter(getActivity().getApplicationContext(),
                        R.layout.food_list_layout, filteredFoods);
                listView.setAdapter(adapter);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goCreate();
        return true;
    }
}
