package com.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.recipefinder.utils.Ingridient;
import com.recipefinder.utils.NetworkUtils;
import com.recipefinder.utils.spoonacularJsonUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class DetailRecipe extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout mTab;
    private ViewPager mViewpager;
    private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        String recipeID = receivedIntent.getStringExtra("recipeID");
        String recipetitle = receivedIntent.getStringExtra("title");


        setContentView(R.layout.activity_detail_recipe);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(recipetitle);
        setSupportActionBar(toolbar);

        mTab = findViewById(R.id.tabs);
        mViewpager = findViewById(R.id.viewpager);
        mProgress = findViewById(R.id.recipe_progress);

        loadTheRecipe(DetailRecipe.this, recipeID);
    }

    private void loadTheRecipe(Context context, String idoftherecipe){
        Request SpoonacularRequest = NetworkUtils.buildRecipeUrl(context, idoftherecipe);
        new SpoonacularInfoTask().execute(SpoonacularRequest);
    }

    private void setupViewPager(ViewPager pager, Fragment overview, Fragment ingrdients, Fragment steps){
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(overview, "Overview");
        adapter.addFragment(ingrdients, "Ingredients");
        adapter.addFragment(steps, "Steps");
        pager.setAdapter(adapter);
    }

    public class SpoonacularInfoTask extends AsyncTask<Request, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Request... params) {
            Request searchUrl = params[0];
            String githubSearchResults = null;
            githubSearchResults = NetworkUtils.getResponse(searchUrl);
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            spoonacularJsonUtils.RecipeData data = spoonacularJsonUtils.getRecipeInfo(githubSearchResults);
            if (githubSearchResults != null && !githubSearchResults.equals("") && data != null) {
                Bundle overviewbundle = new Bundle();
                Bundle ingridientsbundle = new Bundle();
                Bundle stepsbundle = new Bundle();

                overviewbundle.putString("title", data.getTitle());
                overviewbundle.putString("imageurl", data.getImageURL());
                overviewbundle.putInt("preptime", data.getPrepTime());
                overviewbundle.putInt("cooktime", data.getCookingTime());
                overviewbundle.putString("sourceurl", data.getSourceURL());

                ingridientsbundle.putStringArray("names", data.getIngridientName());
                ingridientsbundle.putStringArray("img", data.getIngridientImg());
                ingridientsbundle.putStringArray("amount", data.getIngridientammount());
                ingridientsbundle.putStringArray("unit", data.getIngridientUnit());

                stepsbundle.putStringArray("instructions", data.getInstructions());

                Fragment overview = new overviewfragment();
                Fragment ingridients = new ingridientsfragment();
                Fragment instructions = new stepsfragment();

                overview.setArguments(overviewbundle);
                ingridients.setArguments(ingridientsbundle);
                instructions.setArguments(stepsbundle);

                setupViewPager(mViewpager, overview, ingridients, instructions);
                mTab.setupWithViewPager(mViewpager);
                mTab.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.INVISIBLE);
            } else {

            }
        }
    }

    static class Adapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String>  mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm){super(fm);}

        public void addFragment(Fragment fragment, String Title){
            mFragments.add(fragment);
            mFragmentTitles.add(Title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


}
