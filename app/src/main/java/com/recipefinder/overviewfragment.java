package com.recipefinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class overviewfragment extends Fragment {
    private TextView tvRecipeTitle;
    private TextView tvSource;
    private ImageView ivRecipeimg;
    private TextView tvCookTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview_fragment, container, false);

        tvRecipeTitle = view.findViewById(R.id.title_rcp);
        tvSource = view.findViewById(R.id.source);
        ivRecipeimg = view.findViewById(R.id.recipe_img);
        tvCookTime = view.findViewById(R.id.cooktimeTV);

        String title = this.getArguments().getString("title");
        String imageurl = this.getArguments().getString("imageurl");
        int preptime = this.getArguments().getInt("preptime") + this.getArguments().getInt("cooktime");
        String totaltime = "Ready in: " + Integer.toString(preptime) + " minutes";
        String source = "<a href=\'" +  this.getArguments().getString("sourceurl")  + "\'>Source</a>";


        tvRecipeTitle.setText(title);

        tvSource.setClickable(true);
        tvSource.setMovementMethod(LinkMovementMethod.getInstance());
        tvSource.setText(Html.fromHtml(source));

        Picasso.get().load(imageurl).fit().centerCrop().into(ivRecipeimg);

        tvCookTime.setText(totaltime);


        return view;
    }
}
