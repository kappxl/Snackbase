package de.kaikappel.snackbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FoodFragment extends Fragment implements IOnBackPressed {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        tabLayout = view.findViewById(R.id.tabLayout_id);
        viewPager = view.findViewById(R.id.viewPager_id);
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        vpAdapter.AddFragment(new MealsFragment(), "meals");
        vpAdapter.AddFragment(new FavsFragment(), "ingredients");

        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity) getActivity()).goHome();
        return true;
    }
}
