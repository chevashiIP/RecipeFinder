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

public class IngridientAdapter extends RecyclerView.Adapter<IngridientAdapter.IngridientAdapterViewHolder> {
    private ArrayList<Ingridient> arrayList;


    public IngridientAdapter(){}

    public class IngridientAdapterViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ingridientimg;
        private final TextView tvAmmount;
        public IngridientAdapterViewHolder(View view){
            super(view);
            ingridientimg = view.findViewById(R.id.icon);
            tvAmmount = view.findViewById(R.id.ingridient_ammount);
        }
    }

    @Override
    public IngridientAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Context context = viewGroup.getContext();
        int layout = R.layout.ingridient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layout, viewGroup, false);
        return new IngridientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngridientAdapterViewHolder viewHolder, int position) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("#.###", otherSymbols);

        String name = arrayList.get(position).getmTitle();
        String imageurl = arrayList.get(position).getmImageurl();
        double ammount = Double.valueOf(arrayList.get(position).getmAmmount());
        String unit = arrayList.get(position).getmUnit();

        String ingridient = df.format(ammount) + " " + unit + " " + name;

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + imageurl).fit().into(viewHolder.ingridientimg);
        viewHolder.tvAmmount.setText(ingridient);
    }

    @Override
    public int getItemCount(){
        if(null == arrayList){
            return  0;
        } else {
            return arrayList.size();
        }
    }

    public void setIngridientData(ArrayList<Ingridient> list) {
        arrayList = list;
        notifyDataSetChanged();
    }
}
