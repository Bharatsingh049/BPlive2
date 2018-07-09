package com.Newsforest.android.Newsforest;


import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.National_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.International_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Sports_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Business_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Technology_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Gaming_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Cricket_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Music_News_entry;
import com.Newsforest.android.Newsforest.Data.News_dbhelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //RecyclerView recyclerView;
    //ProgressBar progressBar;
    private ProgressDialog mProgressDialog ;


    public String API_URL = "";
    private Context context;
    private Bitmap bmp;
    private Timer t;

    private ActionBarDrawerToggle toggle;
    public String mError_reason;
    private String tablename,columnttitle,columndesription,columnimage,columnurl;
    private News_dbhelper mdbHelper;
    public String CAT ;// private AdView madview;
    //private RecyclerView recyclerView;
   // private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       //this.mProgressDialog=ProgressDialog.show(this, "Fancy App",
               //"Loading...Please wait...", true, false);;

        //mProgressDialog.show();
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"Please refresh if news was not dispalying",Toast.LENGTH_LONG).show();
        mProgressDialog=new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage(" Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

       // mInterstitialAd= new InterstitialAd(MainActivity.this);

        Toolbar toolbar;
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.refreshgreen));
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         //recyclerView.setOnApplyWindowInsetsListener();
      /*  AdView madview = findViewById(R.id.forest_ad);
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
                Log.d( "onAdFailedToLoad: ",mError_reason);
                Toast.makeText(getApplicationContext(),String.format("onAdFailedToLoad(%s)",mError_reason),Toast.LENGTH_LONG).show();}
        });
        */


        NavigationView navigationView =  findViewById(R.id.nav_view);
        context=MainActivity.this;
        CAT="National";
        Log.d("Cat before = ",CAT);
        API_URL = getIntent().getStringExtra("URL");
        CAT=getIntent().getStringExtra("Cat");
        Log.d("Cat after= ",CAT);
        mdbHelper=new News_dbhelper(this);
        if(API_URL==null){API_URL=" https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";}
        if(!("National".equals(CAT))){
            splash_screen splash=new splash_screen();
            splash.Inserting_records(API_URL,CAT,mdbHelper,context);
           // gatherInformation(mdbHelper);
            }
        toolbar.setTitle(CAT);
        setSupportActionBar(toolbar);

        if("National".equals(CAT)){
            tablename= National_News_entry.Table_Name;
            columnttitle=National_News_entry.Column_News_Title;
            columndesription=National_News_entry.Column_News_Description;
            columnimage=National_News_entry.Column_News_Image;
            columnurl=National_News_entry.Column_News_Url;
            thread.run();
            //columnid=National_News_entry.National_News_id;
        }
        if("International".equals(CAT)){
            tablename= International_News_entry.Table_Name;
            columnttitle=International_News_entry.Column_News_Title;
            columndesription=International_News_entry.Column_News_Description;
            columnimage=International_News_entry.Column_News_Image;
            columnurl=International_News_entry.Column_News_Url;
            thread.run();
            //columnid=International_News_entry.International_News_id;
        }
        if("Sports".equals(CAT)){
            tablename= Sports_News_entry.Table_Name;
            columnttitle=Sports_News_entry.Column_News_Title;
            columndesription=Sports_News_entry.Column_News_Description;
            columnimage=Sports_News_entry.Column_News_Image;
            columnurl=Sports_News_entry.Column_News_Url;
            thread.run();
            //columnid=Sports_News_entry.Sports_News_id;
        }
        if("Business".equals(CAT)){
            tablename= Business_News_entry.Table_Name;
            columnttitle=Business_News_entry.Column_News_Title;
            columndesription=Business_News_entry.Column_News_Description;
            columnimage=Business_News_entry.Column_News_Image;
            columnurl=Business_News_entry.Column_News_Url;
            thread.run();
            //columnid=Business_News_entry.Business_News_id;
        }
        if("Technology".equals(CAT)){
            tablename= Technology_News_entry.Table_Name;
            columnttitle=Technology_News_entry.Column_News_Title;
            columndesription=Technology_News_entry.Column_News_Description;
            columnimage=Technology_News_entry.Column_News_Image;
            columnurl=Technology_News_entry.Column_News_Url;
            thread.run();
            //columnid=Technology_News_entry.Technology_News_id;
        }
        if("Cricket".equals(CAT)){
            tablename= Cricket_News_entry.Table_Name;
            columnttitle=Cricket_News_entry.Column_News_Title;
            columndesription=Cricket_News_entry.Column_News_Description;
            columnimage=Cricket_News_entry.Column_News_Image;
            columnurl=Cricket_News_entry.Column_News_Url;
            thread.run();
            //columnid=Cricket_News_entry.Cricket_News_id;
        }
        if("Music".equals(CAT)){
            tablename= Music_News_entry.Table_Name;
            columnttitle=Music_News_entry.Column_News_Title;
            columndesription=Music_News_entry.Column_News_Description;
            columnimage=Music_News_entry.Column_News_Image;
            columnurl=Music_News_entry.Column_News_Url;
            thread.run();
            //columnid=Music_News_entry.Music_News_id;
        }
        if("Gaming".equals(CAT)){
            tablename= Gaming_News_entry.Table_Name;
            columnttitle=Gaming_News_entry.Column_News_Title;
            columndesription=Gaming_News_entry.Column_News_Description;
            columnimage=Gaming_News_entry.Column_News_Image;
            columnurl=Gaming_News_entry.Column_News_Url;
            thread.run();
            //columnid=Gaming_News_entry.Gaming_News_id;
        }


        //task.execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splash_screen splash=new splash_screen();
                splash.Inserting_records(API_URL,CAT,mdbHelper,context);

                thread.run();

                //gatherInformation(mdbHelper);
                //task.execute();



            }
        });




        navigationView.setNavigationItemSelectedListener(this);




        Log.d("Cat = ",columndesription);
        //Log.d("Cat before = ",columnid);
        Log.d("Cat before = ",columnttitle);
        Log.d("Cat before = ",columnurl);
        Log.d("Table name = ",tablename);

/*
       AdView madview = findViewById(R.id.forest_ad);
        AdRequest adRequest= new AdRequest.Builder().build();
        madview.loadAd(adRequest);



       Timer t= new Timer();
        mInterstitialAd.setAdUnitId("ca-app-pub-8791947476117487/8226369149");
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


    }




    Thread thread= new Thread() {
        @Override
        public void run() {
            mProgressDialog.show();
            final   List<NewsModel> mModelList =new ArrayList<>();


            try {//News_dbhelper mdbHelper= new News_dbhelper(this);

                SQLiteDatabase db = mdbHelper.getReadableDatabase();
                SQLiteDatabase dbw=mdbHelper.getWritableDatabase();

                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(tablename);
                String[] projection = {
                        columnimage,
                        columnttitle,
                        columndesription,
                        columnurl
                };

                Cursor C= db.query(tablename,projection, null, null, null, null, null);
                Log.d("Table name = ",tablename);
                //Cursor C = db.rawQuery(" SELECT * FROM " + tablename ,null);
                // Log.d( "gatherInformation: ",""+C.getCount());

                C.moveToFirst();
                while (!C.isAfterLast()){
                    NewsModel model = new NewsModel();
                    //Cursor cursor1 = db.rawQuery(" SELECT * FROM " + tablename + " WHERE " + columnid + "=?", new String[]{String.valueOf(j)});
                    model.setTitle(C.getString(C.getColumnIndex(columnttitle)));
                    model.setDescription(C.getString(C.getColumnIndex(columndesription)));
                    model.setUrl(C.getString(C.getColumnIndex(columnurl)));
                    // byte[] bit=C.getString(C.getColumnIndex(columnimage));
                    //Bitmap bitmapimage= BitmapFactory.decodeByteArray(bit,0,bit.length);
                    model.setimage(C.getString(C.getColumnIndex(columnimage)));
                    mModelList.add(model);
                    C.moveToNext();
                }
                C.close();
            }catch (SQLiteException e){e.printStackTrace();
                Toast.makeText(MainActivity.this,"error reason"+e.toString(),Toast.LENGTH_LONG).show();}
            catch (Exception e){e.printStackTrace();}
            finally {
                try { RecyclerView recyclerView;
                    recyclerView= findViewById(R.id.recyclerview);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    Log.d("size of mModellist ", "" + mModelList.size());
                    mProgressDialog.dismiss();
                    recyclerView.setAdapter(new Adapter(MainActivity.this, mModelList));
                    Toast.makeText( MainActivity.this,"News was refreshed\n please refresh if was not displaying", Toast.LENGTH_SHORT).show();

                }catch (NullPointerException e){e.printStackTrace();}
            }


        }
    };







    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
       // mInterstitialAd=null;
    }

    @Override
    public void onPause(){
        super.onPause();
       // mInterstitialAd=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {return true;}

        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
            // Handle the camera action
        if (id == R.id.nav_national) {
            //show national news
            //getSupportActionBar().setTitle("National");
            Intent i= new Intent(this,MainActivity.class);
            i.putExtra("URL", " https://newsapi.org/v2/top-headlines?source=the-times-of-india&language=en&country=in&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            i.putExtra("Cat", "National");
            startActivity(i);

        } else if (id == R.id.nav_international) {
            //show international news
            Intent j=new Intent(this,MainActivity.class );
            j.putExtra("URL", "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            j.putExtra("Cat", "International");
            startActivity(j);
        } else if (id == R.id.sports) {
            Intent k=new Intent(this,MainActivity.class );
            k.putExtra("URL", "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            k.putExtra("Cat", "Sports");
            startActivity(k);

        } else if (id == R.id.nav_business) {
          // show all news items
            Intent l=new Intent(this,MainActivity.class );
            l.putExtra("URL", "https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            l.putExtra("Cat", "Business");
            startActivity(l);
        } else if (id == R.id.nav_tech) {
            Intent m=new Intent(this,MainActivity.class );
            m.putExtra("URL", "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            m.putExtra("Cat", "Technology");
            startActivity(m);
        }
        else if (id == R.id.nav_Cricket) {
            Intent n=new Intent(this,MainActivity.class );
            n.putExtra("URL", "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            n.putExtra("Cat", "Cricket");
            startActivity(n);
        }
        else if (id == R.id.nav_Music) {
            Intent o=new Intent(this,MainActivity.class );
            o.putExtra("URL", "https://newsapi.org/v1/articles?source=mtv-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            o.putExtra("Cat", "Music");
            startActivity(o);
        }
        else if (id == R.id.nav_Gaming) {
            Intent p=new Intent(this,MainActivity.class );
            p.putExtra("URL", "https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            p.putExtra("Cat", "Gaming");
            startActivity(p);
        }
        else if (id == R.id.nav_dev) {
            Toast.makeText(MainActivity.this, "Developed by baga (Bharat)" +
                    "\n Version 2.2.0/(2018) \n News Jungle", Toast.LENGTH_LONG).show();

            //Intent p=new Intent(this,MainActivity.class );
            //p.putExtra("URL", "https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            //p.putExtra("Cat", "Gaming");
            //startActivity(p);
        }
        else if (id == R.id.nav_Matches) {
            Intent p=new Intent(this,Main2Activity.class );
            p.putExtra("URL", "http://cricapi.com/api/matches/?apikey=37UZslM2YmOIsHPI5pmnD8Nz5ki1");
            //p.putExtra("Cat", "Gaming");
            startActivity(p);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }





   // private void gatherInformation(final News_dbhelper mdbHelper) {}


   /* public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }*/

    //public static String strSeparator = "__,__";

   /* public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    } */
}
