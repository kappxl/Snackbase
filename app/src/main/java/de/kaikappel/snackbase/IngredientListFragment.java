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

public class IngredientListFragment extends Fragment implements IOnBackPressed {

    ListView listView;
    ArrayList<Ingredient> ingredientList;
    IngredientListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        setHasOptionsMenu(true);

        // VARIABLES

        listView = view.findViewById(R.id.ingredientListView);

        // CREATE VIEW

        ingredientList = ((MainActivity) getActivity()).getIngredientList();

        adapter = new IngredientListAdapter(getActivity().getApplicationContext(),
                R.layout.food_list_layout, ingredientList);
        listView.setAdapter(adapter);

        // LISTENER

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) getActivity()).addIngredientToMask(ingredientList.get(position));
                ((MainActivity) getActivity()).goCreate();
            }
        });

        // TODO: OnItemLongClickListener -> Show relative nutrition values

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goCreate();
        return true;
    }
}
