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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateMealFragment extends Fragment implements IOnBackPressed {

    Button btSave, btAdd;
    ArrayList<Ingredient> ingredientList;
    Meal meal;
    EditText etName;
    Switch b, l, d;
    ListView listView;
    IngredientListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_meal, container, false);

        etName = view.findViewById(R.id.etNameCM);
        btSave = view.findViewById(R.id.btSaveCM);
        btAdd = view.findViewById(R.id.btAddMealCM);
        b = view.findViewById(R.id.swBreakfastCM);
        l = view.findViewById(R.id.swLunchCM);
        d = view.findViewById(R.id.swDinnerCM);
        listView = view.findViewById(R.id.lv_ingredientsCM);

        loadItems();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).goIngredients();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "title missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!b.isChecked() && !l.isChecked() && !d.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(), "time to eat missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ingredientList.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "ingredients missing", Toast.LENGTH_SHORT).show();
                    return;
                }
/*                meal = new Meal(name,
                        (b.isChecked() ? 1 : 0),
                        (l.isChecked() ? 1 : 0),
                        (d.isChecked() ? 1 : 0));

                for (Food x: ingredientList) {
                    meal.addIngredient(x, (float) 1.0);
                }

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();
                databaseAccess.createMeal(meal);
                databaseAccess.close();

                ((MainActivity) getActivity()).loadAllMeals();
                ((MainActivity) getActivity()).resetCreateMealList();*/
                ((MainActivity) getActivity()).goHome();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).popSelectedMealInCreateMeal(position);
                loadItems();

                return false;
            }
        });
        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goHome();
        return true;
    }

    public void loadItems() {
        ingredientList = ((MainActivity) getActivity()).getCreateMealIngredientList();
        adapter = new IngredientListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, ingredientList);
        listView.setAdapter(adapter);
    }
}