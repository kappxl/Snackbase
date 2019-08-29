package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Food> foodList;
    TextView cals, carbs, protein, fat;
    ListView listView;
    FoodListAdapter adapter;
    Button btAddMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        cals = view.findViewById(R.id.tvCals);
        carbs = view.findViewById(R.id.tvCarbs);
        protein = view.findViewById(R.id.tvProtein);
        fat = view.findViewById(R.id.tvFat);
        listView = view.findViewById(R.id.listView);
        btAddMeal = view.findViewById(R.id.btAddMeal);
        foodList = new ArrayList<>();

        foodList.add(new Food("Fleisch", 500, (float) 15.55, (float) 80.1, 0, 100));
        foodList.add(new Food("Fisch", 300, (float) 55.75, (float) 80.1, 0, 100));
        foodList.add(new Food("Ananas", 200, (float) 0.55, (float) 20.61, (float) 18.44, 100));

        adapter = new FoodListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, foodList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                foodList.remove(position);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        btAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goFoodlist();
            }
        });


        refresh();

        return view;
    }

    private void refresh() {
        int icals = 0;
        float fcarbs = 0, fProtein = 0, fFat = 0;

        for(Food x: foodList) {
            icals += x.getCals();
            fcarbs += x.getCarbs();
            fProtein += x.getProtein();
            fFat += x.getFat();
        }
        cals.setText(Integer.toString(icals));
        carbs.setText(String.format("%.0f", fcarbs));
        protein.setText(String.format("%.0f", fProtein));
        fat.setText(String.format("%.0f", fFat));
    }
}
