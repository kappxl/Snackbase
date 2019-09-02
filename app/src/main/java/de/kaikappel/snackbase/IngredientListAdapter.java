package de.kaikappel.snackbase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private int resource;

    public IngredientListAdapter(Context context, int resource, ArrayList<Ingredient> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        convertView = inflater.inflate(this.resource, parent, false);

        // VARIABLES

        String name = getItem(position).getName();
        float cals = getItem(position).getCalsAbs();
        float carbs = getItem(position).getCarbsAbs();
        float protein = getItem(position).getProteinAbs();
        float fat = getItem(position).getFatAbs();

        TextView tvName = convertView.findViewById(R.id.mlv_name);
        TextView tvCals = convertView.findViewById(R.id.mlv_cals);
        TextView tvCarbs = convertView.findViewById(R.id.mlv_carbs);
        TextView tvProtein = convertView.findViewById(R.id.mlv_protein);
        TextView tvFat = convertView.findViewById(R.id.mlv_fat);

        // CREATE VIEW

        tvName.setText(name);
        tvCals.setText(String.format("(%.1f kcals)", cals));
        tvCarbs.setText(String.format("%.1f", carbs));
        tvProtein.setText(String.format("%.1f", protein));
        tvFat.setText(String.format("%.1f", fat));

        return convertView;
    }

}
