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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MealListFragment extends Fragment implements IOnBackPressed {

    ListView listView;
    ArrayList<Meal> mealList;
    MealListAdapter adapter;
    private Switch b, l, d;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_meals, container, false);
        setHasOptionsMenu(true);

        // VARIABLES

        listView = view.findViewById(R.id.mealListView);
        b = getActivity().findViewById(R.id.swBreakfastCM);
        l = getActivity().findViewById(R.id.swLunchCM);
        d = getActivity().findViewById(R.id.swDinnerCM);

        // CREATE VIEW

        mealList = ((MainActivity) getActivity()).getFullMealList();
        loadListView(mealList);

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

        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterListView();
            }
        });

        l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterListView();
            }
        });

        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterListView();
            }
        });

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goHome();
        return true;
    }

    // CREATE VIEW

    public void loadListView(ArrayList<Meal> mList) {
        adapter = new MealListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, mList);
        listView.setAdapter(adapter);
    }

    public void filterListView() {
        ArrayList<Meal> mealListFilter = new ArrayList<>();
        for (Meal m: mealList) {
            if((m.getBreakfast() && b.isChecked()) || (m.getLunch() && l.isChecked()) || (m.getDinner() && d.isChecked())) {
                mealListFilter.add(m.copy());
            }
        }

        loadListView(mealListFilter);
    }
}
