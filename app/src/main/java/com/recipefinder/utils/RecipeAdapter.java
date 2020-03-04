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
        void onClick(int recipeid, String title);
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
            int RecipeId = searchData[adapterPosition].getID();
            String title = searchData[adapterPosition].getTitle();
            mClickHandler.onClick(RecipeId, title);
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
        String recipeTittlestr = null;
        if(searchData[position].getTitle() == null){
            recipeTittlestr = "Recipe name is not found.";
        } else{
            recipeTittlestr = searchData[position].getTitle();
        }
        forecastAdapterViewHolder.tvRecipeName.setText(recipeTittlestr);


        String imageURL = searchData[position].getImageURL();
        if (imageURL != null && !imageURL.contains("https://spoonacular.com/recipeImages/")) {
            Picasso.get().load("https://spoonacular.com/recipeImages/"+ imageURL).fit().centerCrop().into(forecastAdapterViewHolder.ivRecipeImg);
        } else if(imageURL.contains("https://spoonacular.com/recipeImages/") && imageURL != null){
            Picasso.get().load(imageURL).fit().centerCrop().into(forecastAdapterViewHolder.ivRecipeImg);
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
        notifyDataSetChanged();
    }
}
