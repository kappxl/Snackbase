package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateIngredientFragment extends Fragment implements IOnBackPressed {

    EditText etName, etCals, etCarbs, etProtein, etFat, etGrams;
    Button btSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ingredient, container, false);

        // VARIABLES

        etName = view.findViewById(R.id.etNameCI);
        etCals = view.findViewById(R.id.etCalsCI);
        etCarbs = view.findViewById(R.id.etCarbsCI);
        etProtein = view.findViewById(R.id.etProteinCI);
        etFat = view.findViewById(R.id.etFatCI);
        etGrams = view.findViewById(R.id.etGramsCI);
        btSave = view.findViewById(R.id.btSaveCI);

        // LISTENER

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // VARIABLES

                String name;
                float cals, carbs, protein, fat, grams;

                name = etName.getText().toString();
                cals = Float.valueOf(etCals.getText().toString());
                carbs = Float.valueOf(etCarbs.getText().toString());
                protein = Float.valueOf(etProtein.getText().toString());
                fat = Float.valueOf(etFat.getText().toString());
                grams = Float.valueOf(etGrams.getText().toString());

                // DO SAVE

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();

                if (!databaseAccess.insertIngredient(name, cals, carbs, protein, fat, grams)) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.error_msg_default, Toast.LENGTH_SHORT).show();
                }

                databaseAccess.close();

                ((MainActivity) getActivity()).loadAllIngredients();
                ((MainActivity) getActivity()).goHome();
            }
        });

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goHome();
        return true;
    }
}
