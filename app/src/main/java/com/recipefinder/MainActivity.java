package com.recipefinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.recipefinder.utils.NetworkUtils;
import com.recipefinder.utils.RecipeAdapter;
import com.recipefinder.utils.RecipeAdapter.RecipeAdapterOnClickHandler;
import com.recipefinder.utils.spoonacularJsonUtils;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler{
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private SearchView mSearchQuery;
    private RecyclerView mRecyclerView;
    private TextView mErrorTV;
    private ProgressBar mProgressBar;
    private RecipeAdapter mRecipeAdapter;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mSearchQuery = findViewById(R.id.et_search_box);
        mRecyclerView = findViewById(R.id.recyclerview);
        mErrorTV = findViewById(R.id.errorTV);
        mProgressBar = findViewById(R.id.progress_bar);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(this);

        mRecyclerView.setAdapter(mRecipeAdapter);

        InitializerandomRecipe(MainActivity.this);

        mSearchQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    makeSpoonacularSearchQuery(MainActivity.this, query);
                }
                else{
                    ShowError(MainActivity.this,"Query length must be longer than 2 characters");
                }

                mSearchQuery.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });
    }

    public class SpoonacularQueryTask extends AsyncTask<Request, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setAlpha(0);
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
            spoonacularJsonUtils.SearchData[] data = spoonacularJsonUtils.getRecipe(githubSearchResults);
            if (githubSearchResults != null && !githubSearchResults.equals("") && data.length != 0) {
                    mRecipeAdapter.setRecipeData(data);
                    showResult();
                mProgressBar.setVisibility(View.INVISIBLE);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                showError();
            }
        }
    }

    public class SpoonacularRandomTask extends AsyncTask<Request, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setAlpha(0);
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
            spoonacularJsonUtils.SearchData[] data = spoonacularJsonUtils.getRandomRecipe(githubSearchResults);
            if (githubSearchResults != null && !githubSearchResults.equals("") && data.length != 0) {
                mRecipeAdapter.setRecipeData(data);
                showResult();
                mProgressBar.setVisibility(View.INVISIBLE);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                showError();
            }
        }
    }

    private void makeSpoonacularSearchQuery(Context context ,String query) {
        Request SpoonacularRequest = NetworkUtils.buildSearchUrl(context, query);
        new SpoonacularQueryTask().execute(SpoonacularRequest);
    }

    private void InitializerandomRecipe(Context context) {
        Request SpoonacularRequest = NetworkUtils.buildRandomUrl(context);
        new SpoonacularRandomTask().execute(SpoonacularRequest);
    }

    private void showResult(){
        mErrorTV.setVisibility(View.INVISIBLE);
        mRecyclerView.setAlpha(1);
    }

    private void showError(){
        mErrorTV.setVisibility(View.VISIBLE);
        mRecyclerView.setAlpha(0);
    }

    static public void ShowError(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Error");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onClick(int recipeid, String title) {
        Intent i = new Intent(MainActivity.this, DetailRecipe.class);
        i.putExtra("recipeID", Integer.toString(recipeid));
        i.putExtra("title", title);
        startActivity(i);
    }



}
