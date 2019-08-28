package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MealsFragment extends Fragment {

    ListView listView;
    EditText etFilter;
    ArrayList<Food> foodList = null;
    FoodListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_meals, container, false);
        listView = view.findViewById(R.id.mealListView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        databaseAccess.open();

        foodList = databaseAccess.getMeals();

        databaseAccess.close();

        adapter = new FoodListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, foodList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // mealListener.onMealSent(foodList.get(position));
                Toast.makeText(getActivity().getApplicationContext(), foodList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
