package com.recipefinder;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.recipefinder.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private SearchView mSearchQuery;
    private RecyclerView mRecyclerView;
    private TextView mErrorTV;
    private ProgressBar mProgressBar;
    private static final String TAG = NetworkUtils.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchQuery = findViewById(R.id.et_search_box);
        mRecyclerView = findViewById(R.id.recyclerview);
        mErrorTV = findViewById(R.id.errorTV);
        mProgressBar = findViewById(R.id.progress_bar);

        mSearchQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    // Getting query
                    makeSpoonacularSearchQuery(MainActivity.this, query);
                }
                else{
                    ShowError(MainActivity.this,"Query length must be longer than 2");
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
            mProgressBar.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                showResult();
                Log.d(TAG, githubSearchResults);
            } else {
                showError();
            }
        }
    }

    private void makeSpoonacularSearchQuery(Context context ,String query) {
        String SpoonacularQuery = query;
        Request SpoonacularRequest = NetworkUtils.buildUrl(context, query);
        new SpoonacularQueryTask().execute(SpoonacularRequest);
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
}
