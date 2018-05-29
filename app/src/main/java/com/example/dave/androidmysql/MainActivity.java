package com.example.dave.androidmysql;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
EditText inputNames,inputEmail;
SpotsDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNames=findViewById(R.id.inputNames);
        inputEmail=findViewById(R.id.inputEmail);
        progress=new SpotsDialog(this);
    }

    public void Save(View view) {
        String names=inputNames.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        if (names.isEmpty()|| email.isEmpty()){
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isNetworkAvailable())
        {
            Toast.makeText(this, "Turn on Data", Toast.LENGTH_SHORT).show();
            return;
        }
        //save to db   google loopj
        //compile 'com.loopj.android:android-async-http:1.4.9'
        //sports dialog implementation 'com.github.d-max:spots-dialog:0.7@aar'

        progress.show();
        AsyncHttpClient client= new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("names",names);
        params.put("email",email);
        String url = "http://66.228.55.80/save.php";//toget the ip address of your comp cmd( ipconfig /all)
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Failed to send", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, ""+responseString, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void Display(View view) {
        Intent x=new Intent(this,DisplayActivity.class);
        startActivity(x);
    }
}
