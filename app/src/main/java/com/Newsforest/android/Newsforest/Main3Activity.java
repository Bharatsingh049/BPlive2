package com.Newsforest.android.Newsforest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.Newsforest.android.Newsforest.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONObject;

import java.util.Collections;

public class Main3Activity extends AppCompatActivity {
    public ProgressDialog mProgressDialog;
    public TextView score;
    public TextView require;
    public TextView scoret1;
    public TextView scoret2;
    public String mError_reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        score =(TextView) findViewById(R.id.scores_b);
        require =(TextView) findViewById(R.id.require);
        scoret1 =(TextView) findViewById(R.id.team1score);
        scoret2 =(TextView) findViewById(R.id.team2score);
        String UIP ;
        String t1,t2;
        t1=getIntent().getStringExtra("t1");
        t2=getIntent().getStringExtra("t2");
        UIP=getIntent().getStringExtra("UI");
        final String url="http://cricapi.com/api/cricketScore/?apikey=37UZslM2YmOIsHPI5pmnD8Nz5ki1&unique_id="+UIP;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "score refresh", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Gathering_scores(url);
            }
        });
        Gathering_scores(url);
        scoret1.setText(t1);
        scoret2.setText(t2);
       /*AdView madview = (AdView) findViewById(R.id.Live_matches_ads);
        AdRequest adRequest= new AdRequest.Builder().build();
        madview.loadAd(adRequest);

        madview.setAdListener(new AdListener(){
            public void onAdLoaded(){Toast.makeText(getApplicationContext(),"onAdLoaded()",Toast.LENGTH_LONG).show();}
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
    public void Gathering_scores(String url){
        mProgressDialog = new ProgressDialog(Main3Activity.this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

       // StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET,url, new Response  .Listener<String>() {
        //}
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>(){
                    @Override
                public void onResponse(JSONObject response) {
                        mProgressDialog.dismiss();
                        Log.e("SCORE ",response.toString());
                        //res = response.toString();
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        score.setText(jsonObject.getString("score"));
                        require.setText(jsonObject.getString("innings-requirement"));
                        //Log.i((TAG), jsonObject.toString());
                        //jsonArray = jsonObject.getJSONObject("data").getJSONArray("detections");
                        //string = jsonArray.getJSONObject(0).getString("language");

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Main3Activity.this, "please retry", Toast.LENGTH_LONG).show();
                    }

                }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Main3Activity.this, "Failed to fetch data." +
                            " Please check your network connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //TODO
                } else if (error instanceof ServerError) {
                    //TODO
                    Log.e("Server error",error.toString());
                } else if (error instanceof NetworkError) {
                    //TODO
                    Log.e("Network error",error.toString());
                } else if (error instanceof ParseError) {
                    //TODO
                    Log.e("Parse Error ",error.toString());
                }
            }
        }){

        @Override
           protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            if (response.headers == null)
            {
                // cant just set a new empty map because the member is final.
                response = new NetworkResponse(
                        response.statusCode,
                        response.data,
                        Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                        response.notModified,
                        response.networkTimeMs);
            }

            return super.parseNetworkResponse(response);
           }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);
    }
}
