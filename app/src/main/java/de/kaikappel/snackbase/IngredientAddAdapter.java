package de.kaikappel.snackbase;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class IngredientAddAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Ingredient> ingredients;
    private LayoutInflater inflater;

    public IngredientAddAdapter(Context mContext, ArrayList<Ingredient> ingredients) {
        this.mContext = mContext;
        this.ingredients = ingredients;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
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

        tvName.setText(name);
        tvCals.setText(String.format("(%.1f kcals)", cals));
        tvCarbs.setText(String.format("%.1f", carbs));
        tvProtein.setText(String.format("%.1f", protein));
        tvFat.setText(String.format("%.1f", fat));
    */

    @Override
    public int getGroupCount() {
        return ingredients.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Ingredient getGroup(int groupPosition) {
        return ingredients.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ingredients.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_list_layout, null);
        }

        String name = ingredients.get(groupPosition).getName();
        float cals = ingredients.get(groupPosition).getCalsAbs();
        float carbs = ingredients.get(groupPosition).getCarbsAbs();
        float protein = ingredients.get(groupPosition).getProteinAbs();
        float fat = ingredients.get(groupPosition).getFatAbs();

        TextView tvName = convertView.findViewById(R.id.mlv_name);
        TextView tvCals = convertView.findViewById(R.id.mlv_cals);
        TextView tvCarbs = convertView.findViewById(R.id.mlv_carbs);
        TextView tvProtein = convertView.findViewById(R.id.mlv_protein);
        TextView tvFat = convertView.findViewById(R.id.mlv_fat);

        tvName.setText(name);
        tvCals.setText(String.format("(%.1f kcals)", cals));
        tvCarbs.setText(String.format("%.1f", carbs));
        tvProtein.setText(String.format("%.1f", protein));
        tvFat.setText(String.format("%.1f", fat));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.ingredients_sub_item, null);
        }

        final EditText unit = convertView.findViewById(R.id.etUnit);
        final EditText amount = convertView.findViewById(R.id.etAmount);
        Button btnAdd = convertView.findViewById(R.id.btnAddIngredient);

        unit.setText(Integer.toString((int) getGroup(groupPosition).getGrams()));
        amount.setText(Integer.toString((int) getGroup(groupPosition).getAmount()));

        final Ingredient ing = getGroup(groupPosition);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ing.getAmount() != Float.parseFloat(amount.getText().toString()) || ing.getGrams() !=  Float.parseFloat(unit.getText().toString())) {
                    ing.setEdited(true);
                    ing.setAmount((ing.getAmount() / ing.getGrams()) * (Float.parseFloat(amount.getText().toString()) * Float.parseFloat(unit.getText().toString())));
                }

                ((MainActivity) mContext).addIngredientToMask(new Ingredient(ing.getId(),
                        ing.getName(),
                        ing.getCals(),
                        ing.getCarbs(),
                        ing.getProtein(),
                        ing.getFat(),
                        ing.getGrams(),
                        ing.getAmount())
                );

                ((MainActivity) mContext).goCreate();

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
