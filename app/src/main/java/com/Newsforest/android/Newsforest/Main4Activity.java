package com.Newsforest.android.Newsforest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.Newsforest.android.Newsforest.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main4Activity extends AppCompatActivity{

public String mError_reason;
public ProgressDialog mdialog;
private NewsModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mdialog=new ProgressDialog(Main4Activity.this);
        mdialog.setMessage("Loading...");
        mdialog.setCancelable(false);
        mdialog.show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        model=(NewsModel)getIntent().getSerializableExtra("NewsModel");
        //final String ApIurl ;
        //ApIurl = getIntent().getStringExtra("URL");
        //final String desc = getIntent().getStringExtra("desc");
        Log.e("URL>>>>",model.getUrl());
        //Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(ApIurl));
        //startActivity(browser);
       // new newsitem().execute(ApIurl);
        final WebView myWebView = (WebView) findViewById(R.id.webview4);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        myWebView.getSettings().setJavaScriptEnabled(true);
        //myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView w,String url){
                super.onPageFinished(w,url);
                mdialog.dismiss();
            }
        });
        myWebView.loadUrl(model.getUrl());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, model.getUrl()+" " + model.getDescription());
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        /*AdView madview = (AdView) findViewById(R.id.Specific_news_Ads);
        AdRequest adRequest= new AdRequest.Builder().build();
        madview.loadAd(adRequest);

         madview.setAdListener(new AdListener(){
            public void onAdLoaded(){
                Toast.makeText(getApplicationContext(),"onAdLoaded()",Toast.LENGTH_LONG).show();}
            public void onAdClosed(){Toast.makeText(getApplicationContext(),"onAdClosed()",Toast.LENGTH_LONG).show();}
            public void onAdLeftApplication(){Toast.makeText(getApplicationContext(),"onAdLeftApplication()",Toast.LENGTH_LONG).show();}
            public void onAdFailedToLoad(int error_code){
                mError_reason="";
                switch (error_code){
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        mError_reason="Internal Code";
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        mError_reason="Invalid Request";
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        mError_reason="Network Error";
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        mError_reason="No fill";
                        break;
                }
                Toast.makeText(getApplicationContext(),String.format("onAdFailedToLoad(%s)",mError_reason),Toast.LENGTH_LONG).show();}
        });
        */
    }
    //public void setwebview(String url){
      //  WebView myWebView = (WebView) findViewById(R.id.webview4);
        //myWebView.loadUrl(url);
        //myWebView.getSettings().setJavaScriptEnabled(true);
    //}
    class newsitem extends AsyncTask<String,Void,String>{
        private String str;

        @Override
        protected String doInBackground(String... strings) {
            try {
                org.apache.http.client.HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
                HttpGet httpget = new HttpGet("http://yoururl.com"); // Set the action you want to do
                HttpResponse response = httpclient.execute(httpget); // Executeit
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent(); // Create an InputStream with the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) // Read line by line
                    sb.append(line + "\n");

                String resString = sb.toString(); // Result is here
                str=resString;
                is.close();// Close the stream
            }catch (IOException e){
                e.printStackTrace();
            }
            return str;
        }
        @Override
        protected void onPostExecute(String str){
            //TextView News_item= findViewById(R.id.webview4);
            //News_item.setText(str);
        }
    }
 }
