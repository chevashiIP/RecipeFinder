package com.recipefinder.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.recipefinder.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {
    private ArrayList<Ingredient> arrayList;


    public IngredientAdapter(){}

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ingredientimg;
        private final TextView tvAmmount;
        public IngredientAdapterViewHolder(View view){
            super(view);
            ingredientimg = view.findViewById(R.id.icon);
            tvAmmount = view.findViewById(R.id.ingredient_amount);
        }
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layout = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layout, viewGroup, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder viewHolder, int position) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.###", otherSymbols);

        String name = arrayList.get(position).getmTitle();
        String imageurl = arrayList.get(position).getmImageurl();
        double ammount = Double.valueOf(arrayList.get(position).getmAmmount());
        String unit = arrayList.get(position).getmUnit();

        String ingredient = df.format(ammount) + " " + unit + " " + name;

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + imageurl).fit().into(viewHolder.ingredientimg);
        viewHolder.tvAmmount.setText(ingredient);
    }

    @Override
    public int getItemCount(){
        if(null == arrayList){
            return  0;
        } else {
            return arrayList.size();
        }
    }

    public void setIngredientData(ArrayList<Ingredient> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}
