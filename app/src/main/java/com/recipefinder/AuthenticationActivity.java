package com.recipefinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;


public class AuthenticationActivity extends AppCompatActivity {

    private final String TAG = AuthenticationActivity.class.getSimpleName();
    private TextView noInternet;

    boolean internet_connection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        noInternet = findViewById(R.id.nointernetTV);

        if(internet_connection()) {
            AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i(TAG, userStateDetails.getUserState().toString());
                    switch (userStateDetails.getUserState()) {
                        case SIGNED_IN:
                            Intent i = new Intent(AuthenticationActivity.this, MainActivity.class);
                            startActivity(i);
                            break;
                        case SIGNED_OUT:
                            showSignIn();
                            break;
                        default:
                            AWSMobileClient.getInstance().signOut();
                            showSignIn();
                            break;
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, e.toString());
                }
            });
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    private void showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder().nextActivity(MainActivity.class).build());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
