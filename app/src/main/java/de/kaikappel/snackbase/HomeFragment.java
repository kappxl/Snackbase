package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Meal> mealList;
    TextView cals, carbs, protein, fat;
    ListView listView;
    MealListAdapter adapter;
    Button btAddMeal;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        cals = view.findViewById(R.id.tvCals);
        carbs = view.findViewById(R.id.tvCarbs);
        protein = view.findViewById(R.id.tvProtein);
        fat = view.findViewById(R.id.tvFat);
        listView = view.findViewById(R.id.listView);
        btAddMeal = view.findViewById(R.id.btAddMeal);


        refreshPage();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ((MainActivity) getActivity()).popMeal(mealList.get(position).getId());
                refreshPage();
                return false;
            }
        });

        btAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goFoodList();
            }
        });


        return view;
    }

    private void refreshPage() {
        int icals = 0;
        float fcarbs = 0, fProtein = 0, fFat = 0;

        mealList = ((MainActivity) getActivity()).getSelectedMealList();

        for(Meal x: mealList) {
            icals += x.getCals();
            fcarbs += x.getCarbs();
            fProtein += x.getProtein();
            fFat += x.getFat();
        }
        cals.setText(Integer.toString(icals));
        carbs.setText(String.format("%.0f", fcarbs));
        protein.setText(String.format("%.0f", fProtein));
        fat.setText(String.format("%.0f", fFat));

        adapter = new MealListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, mealList);
        listView.setAdapter(adapter);
    }
}
