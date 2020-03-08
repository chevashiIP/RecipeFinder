package com.recipefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipefinder.utils.Ingredient;
import com.recipefinder.utils.IngredientAdapter;
import java.util.ArrayList;

public class ingredientsfragment extends Fragment {
    private RecyclerView mIngredientList;
    private IngredientAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_fragment, container, false);
        mIngredientList = view.findViewById(R.id.ingredient_list);

        String[] name = this.getArguments().getStringArray("names");
        String[] imgurl = this.getArguments().getStringArray("img");
        String[] amount = this.getArguments().getStringArray("amount");
        String[] unit = this.getArguments().getStringArray("unit");

        ArrayList<Ingredient> list = new ArrayList<>();

        for(int i = 0; i < name.length; i++){
            list.add(new Ingredient(name[i], imgurl[i], amount[i], unit[i]));
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mIngredientList.setLayoutManager(layoutManager);
        mIngredientList.setHasFixedSize(true);
        adapter = new IngredientAdapter();
        adapter.setIngredientData(list);

        mIngredientList.setAdapter(adapter);

        return view;
    }
}
