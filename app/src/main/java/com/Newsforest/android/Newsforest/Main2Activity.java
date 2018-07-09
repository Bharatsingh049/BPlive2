package com.Newsforest.android.Newsforest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Newsforest.android.Newsforest.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {

   /* private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public String[] DataSet={"one","two","three","four","five"};
    */
   ProgressDialog mProgressDialog;
    private List<Matches> mModelList1 = new ArrayList<>();
    RecyclerView recyclerView1;
    public String match_API;
    public String mError_reason;
   // private InterstitialAd mInterstitialAd;
    //public var d = new Date('2015-03-04T00:00:00.000Z');

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   mInterstitialAd=new InterstitialAd(Main2Activity.this);
       /* mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mRecyclerView.setAdapter(new MyAdapter(DataSet));

        */
        recyclerView1= (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        match_API = getIntent().getStringExtra("URL");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gatherInformation(match_API);
            }
        });
        gatherInformation(match_API);
  /*      AdView madview = findViewById(R.id.match_list_Ads);
        AdRequest adRequest= new AdRequest.Builder().build();
        //madview.loadAd(adRequest);
       Timer t= new Timer();
        mInterstitialAd.setAdUnitId("ca-app-pub-2093514817013726/4815893747");
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                if (mInterstitialAd != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AdRequest adr = new AdRequest.Builder().build();
                            mInterstitialAd.loadAd(adr);

                            mInterstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {

                                }

                                @Override
                                public void onAdLoaded() {
                                    //mAdIsLoading = false;
                                    super.onAdLoaded();
                                    Log.d("onAdLoaded ", "" + 1);
                                    if (mInterstitialAd != null) {
                                        try {
                                            mInterstitialAd.show();
                                        } catch (NullPointerException e) {
                                            throw e;
                                        }
                                    }
                                }

                                @Override
                                public void onAdFailedToLoad(int errorCode) {
                                    // mAdIsLoading = false;
                                    super.onAdFailedToLoad(errorCode);
                                    Log.d("onAdFailedtoLoad ", "" + errorCode);
                                }
                            });
                        }
                    });

            }
            }
        };
        t.schedule(task,20000);
*/
  /*      madview.setAdListener(new AdListener(){
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

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
       // mInterstitialAd=null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //mInterstitialAd=null;
    }


    private void gatherInformation(String API_URL) {
        /*if (API_URL == null){
            API_URL = API_URL1;
        }*/
        try {
            mProgressDialog = new ProgressDialog(Main2Activity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response: ",response.toString());
                    mProgressDialog.dismiss();
                    Calendar cc = Calendar.getInstance();
                    int year=cc.get(Calendar.YEAR);
                    int month=cc.get(Calendar.MONTH);
                    int mDay = cc.get(Calendar.DAY_OF_MONTH);
                    //var localTime = moment().format('YYYY-MM-DD'); // store localTime
                    //var proposedDate = localTime + "T00:00:00.000Z";
                    try{
                        JSONObject jObject=new JSONObject(response);
                        //String status=jObject.getString("status");
                        //String sortby=jObject.getString("sortBy");
                            JSONArray jsonArray= jObject.getJSONArray("matches");
                            Log.e("prevoius data",jsonArray.toString());
                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonobject=jsonArray.getJSONObject(i);

                                Log.e("InnerData: ",jsonobject.toString());
                                Log.e("Date: ",jsonobject.getString("date"));
                                Matches datalist1 = new Matches();
                                datalist1.setUnique_id(jsonobject.getInt("unique_id"));
                                datalist1.setTeam2(jsonobject.getString("team-2"));
                                datalist1.setTeam1(jsonobject.getString("team-1"));
                                datalist1.setMatchStarted(jsonobject.getString("matchStarted"));
                                datalist1.setDate(jsonobject.getString("date"));
                                mModelList1.add(datalist1);
                            }

                            recyclerView1.setAdapter(new MyAdapter(Main2Activity.this,mModelList1));



                    }catch (NullPointerException ex){
                        Log.e("Error ",ex.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(Main2Activity.this, "Failed to fetch data." +
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
            }) {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
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

            //*********To Retry sending*********************************************
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(8000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonArrayRequest);

        }catch (NullPointerException ex){

        }catch (IllegalFormatException ex){}
    }

}

