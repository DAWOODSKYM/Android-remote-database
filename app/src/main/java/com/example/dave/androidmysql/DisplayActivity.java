package com.example.dave.androidmysql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;

public class DisplayActivity extends AppCompatActivity {
TextView tvdisplay;
SpotsDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        tvdisplay=findViewById(R.id.tvdisplay) ;
         progress =new SpotsDialog(this);
         String url= "http://66.228.55.80/fetch.php";

         progress.show();
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.dismiss();
                tvdisplay.setText("Something bad happened");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progress.dismiss();
                tvdisplay.setText(responseString);

            }
        });
    }
}
