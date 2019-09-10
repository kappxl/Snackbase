package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class IngredientListFragment extends Fragment implements IOnBackPressed {

    private ExpandableListView expandableListView;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAddAdapter adapter;
    private int lastPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        setHasOptionsMenu(true);

        // VARIABLES

        expandableListView = view.findViewById(R.id.ingredientListView);

        // CREATE VIEW

        ingredientList = ((MainActivity) getActivity()).getIngredientList();

        adapter = new IngredientAddAdapter(getActivity(), ingredientList);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastPosition != -1
                        && groupPosition != lastPosition) {
                    expandableListView.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;
            }
        });

        // LISTENER

/*        expandableListViewistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Get amount.
                ((MainActivity) getActivity()).addIngredientToMask(ingredientList.get(position));
                ((MainActivity) getActivity()).goCreate();
            }
        });*/

        // TODO: OnItemLongClickListener -> Show relative nutrition values

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goCreate();
        return true;
    }
}
