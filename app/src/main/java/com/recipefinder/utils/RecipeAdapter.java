package com.recipefinder.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recipefinder.R;
import com.recipefinder.utils.spoonacularJsonUtils.SearchData;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private final RecipeAdapterOnClickHandler mClickHandler;

    private spoonacularJsonUtils.SearchData[] searchData;

    public interface RecipeAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView tvRecipeName;
        public final ImageView ivRecipeImg;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            tvRecipeName = view.findViewById(R.id.recipe_name_item);
            ivRecipeImg = view.findViewById(R.id.image_item);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = searchData[adapterPosition].getTitle();
            mClickHandler.onClick(weatherForDay);
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder forecastAdapterViewHolder, int position) {
        String recipeTittlestr = searchData[position].getTitle();
        String imageURL = searchData[position].getImageURL();
        forecastAdapterViewHolder.tvRecipeName.setText(recipeTittlestr);
        if (imageURL != null) {
            Picasso.get().load("https://spoonacular.com/recipeImages/"+ imageURL).fit().centerCrop().into(forecastAdapterViewHolder.ivRecipeImg);
        }
    }

    @Override
    public int getItemCount() {
        if (null == searchData){
            return 0;
        }
        return searchData.length;
    }

    public void setRecipeData(SearchData[] recipeData) {
        searchData = recipeData;
        Log.d(TAG, searchData[1].getTitle());
        notifyDataSetChanged();
    }
}
