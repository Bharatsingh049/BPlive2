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

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
    private SwipeRefreshLayout Main_Layout;
    public String API_URL = "";
    private Context context;
    private Bitmap bmp;
    private Timer t;

    private ActionBarDrawerToggle toggle;
    public String mError_reason;
    private String tablename,columnttitle,columndesription,columnimage,columnurl,colummdate;
    private News_dbhelper mdbHelper;
    private FloatingActionButton fab;
    private String CAT ;// private AdView madview;
    //private RecyclerView recyclerView;
   // private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       //this.mProgressDialog=ProgressDialog.show(this, "Fancy App",
               //"Loading...Please wait...", true, false);;

        //mProgressDialog.show();
       // supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Main_Layout=(SwipeRefreshLayout) findViewById(R.id.Main_layout);
        //Toast.makeText(MainActivity.this,"Please refresh if news was not dispalying",Toast.LENGTH_LONG).show();
        mProgressDialog=new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage(" Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

       // mInterstitialAd= new InterstitialAd(MainActivity.this);

        Toolbar toolbar;
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // fab = findViewById(R.id.fab);
        //fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.refreshgreen));
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
        context=getApplicationContext();
        CAT="National";
        Log.d("Cat before = ",CAT);
        API_URL = getIntent().getStringExtra("URL");
        CAT=getIntent().getStringExtra("Cat");
       // Log.d("Cat after= ",CAT);
        initColumns();
        Log.d("Cat = ",columndesription);
        //Log.d("Cat before = ",columnid);
        Log.d("Cat before = ",columnttitle);
        Log.d("Cat before = ",columnurl);
        Log.d("Table name = ",tablename);
        Log.d("date = ",colummdate);
        mdbHelper=new News_dbhelper(this);
        if(API_URL==null){API_URL=" https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";}
            splash_screen splash=new splash_screen();
            splash.Inserting_records(API_URL,CAT,mdbHelper,context);
        final Handler handler23 = new Handler();
        handler23.postDelayed(new Runnable() {
            @Override public void run() {
                //Do something after 100ms
                //Log.d("onCreateView: ",  Income_List.size()+"  "+BarChart_Entries.size()+"  ");
                thread.run();
                mProgressDialog.dismiss();

            }
        }, 1000);

           // gatherInformation(mdbHelper);

        toolbar.setTitle(CAT);
        setSupportActionBar(toolbar);


        navigationView.setNavigationItemSelectedListener(this);



        Main_Layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                splash_screen splash=new splash_screen();
                splash.Inserting_records(API_URL,CAT,mdbHelper,context);
                thread.run();

            }
        });
    }


public void initColumns(){

    if("National".equals(CAT) || TextUtils.isEmpty(CAT)){
        tablename= National_News_entry.Table_Name;
        columnttitle=National_News_entry.Column_News_Title;
        columndesription=National_News_entry.Column_News_Description;
        columnimage=National_News_entry.Column_News_Image;
        colummdate=National_News_entry.Column_News_Date;
        columnurl=National_News_entry.Column_News_Url;
        thread.run();
        //columnid=National_News_entry.National_News_id;
    }
    if("International".equals(CAT)){
        tablename= International_News_entry.Table_Name;
        columnttitle=International_News_entry.Column_News_Title;
        columndesription=International_News_entry.Column_News_Description;
        columnimage=International_News_entry.Column_News_Image;
        colummdate=International_News_entry.Column_News_Date;
        columnurl=International_News_entry.Column_News_Url;
        thread.run();
        //columnid=International_News_entry.International_News_id;
    }
    if("Sports".equals(CAT)){
        tablename= Sports_News_entry.Table_Name;
        columnttitle=Sports_News_entry.Column_News_Title;
        columndesription=Sports_News_entry.Column_News_Description;
        columnimage=Sports_News_entry.Column_News_Image;
        colummdate=Sports_News_entry.Column_News_Date;
        columnurl=Sports_News_entry.Column_News_Url;
        thread.run();
        //columnid=Sports_News_entry.Sports_News_id;
    }
    if("Business".equals(CAT)){
        tablename= Business_News_entry.Table_Name;
        columnttitle=Business_News_entry.Column_News_Title;
        columndesription=Business_News_entry.Column_News_Description;
        columnimage=Business_News_entry.Column_News_Image;
        colummdate=Business_News_entry.Column_News_Date;
        columnurl=Business_News_entry.Column_News_Url;
        thread.run();
        //columnid=Business_News_entry.Business_News_id;
    }
    if("Technology".equals(CAT)){
        tablename= Technology_News_entry.Table_Name;
        columnttitle=Technology_News_entry.Column_News_Title;
        columndesription=Technology_News_entry.Column_News_Description;
        columnimage=Technology_News_entry.Column_News_Image;
        colummdate=Technology_News_entry.Column_News_Date;
        columnurl=Technology_News_entry.Column_News_Url;
        thread.run();
        //columnid=Technology_News_entry.Technology_News_id;
    }
    if("Cricket".equals(CAT)){
        tablename= Cricket_News_entry.Table_Name;
        columnttitle=Cricket_News_entry.Column_News_Title;
        columndesription=Cricket_News_entry.Column_News_Description;
        columnimage=Cricket_News_entry.Column_News_Image;
        colummdate=Cricket_News_entry.Column_News_Date;
        columnurl=Cricket_News_entry.Column_News_Url;
        thread.run();
        //columnid=Cricket_News_entry.Cricket_News_id;
    }
    if("Music".equals(CAT)){
        tablename= Music_News_entry.Table_Name;
        columnttitle=Music_News_entry.Column_News_Title;
        columndesription=Music_News_entry.Column_News_Description;
        columnimage=Music_News_entry.Column_News_Image;
        colummdate=Music_News_entry.Column_News_Date;
        columnurl=Music_News_entry.Column_News_Url;
        thread.run();
        //columnid=Music_News_entry.Music_News_id;
    }
    if("Gaming".equals(CAT)){
        tablename= Gaming_News_entry.Table_Name;
        columnttitle=Gaming_News_entry.Column_News_Title;
        columndesription=Gaming_News_entry.Column_News_Description;
        columnimage=Gaming_News_entry.Column_News_Image;
        colummdate=Gaming_News_entry.Column_News_Date;
        columnurl=Gaming_News_entry.Column_News_Url;
        thread.run();
        //columnid=Gaming_News_entry.Gaming_News_id;
    }

}

    Thread thread= new Thread() {
        @Override
        public void run() {
            mProgressDialog.show();
            final   List<NewsModel> mModelList =new ArrayList<>();


            try {//News_dbhelper mdbHelper= new News_dbhelper(this);

                SQLiteDatabase db = mdbHelper.getReadableDatabase();
                //SQLiteDatabase dbw=mdbHelper.getWritableDatabase();

                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(tablename);
                String[] projection = {
                        columnimage,
                        columnttitle,
                        columndesription,
                        colummdate,
                        columnurl
                };

                Cursor C= db.query(tablename,projection, null, null, null, null, null);
                int count=C.getCount();
                Log.d("Cursor ",count+"");
                //Cursor C = db.rawQuery(" SELECT * FROM " + tablename ,null);
                // Log.d( "gatherInformation: ",""+C.getCount());

                C.moveToFirst();
                while (!C.isAfterLast()){
                    NewsModel model = new NewsModel();
                    //Cursor cursor1 = db.rawQuery(" SELECT * FROM " + tablename + " WHERE " + columnid + "=?", new String[]{String.valueOf(j)});
                    model.setTitle(C.getString(C.getColumnIndex(columnttitle)));
                    model.setDescription(C.getString(C.getColumnIndex(columndesription)));
                    String url=C.getString(C.getColumnIndex(columnurl));
                    Log.d( "run: ",url);
                    model.setUrl(C.getString(C.getColumnIndex(columnurl)));
                    model.setDateString(C.getString(C.getColumnIndex(colummdate)));
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
                    LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this);
                    manager.setReverseLayout(true);
                    manager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(manager);
                    Log.d("size of mModellist ", "" + mModelList.size());
                    mProgressDialog.dismiss();
                    recyclerView.setAdapter(new Adapter(MainActivity.this, mModelList));
                    Main_Layout.setRefreshing(false);
                    //Toast.makeText( MainActivity.this,"News was refreshed\n please refresh if was not displaying", Toast.LENGTH_SHORT).show();

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
            /*String URL="https://newsapi.org/v2/top-headlines?source=the-times-of-india&language=en&country=in&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="National";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent i= new Intent(this,MainActivity.class);
            i.putExtra("URL", "https://newsapi.org/v2/top-headlines?source=the-times-of-india&language=en&country=in&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            i.putExtra("Cat", "National");
            startActivity(i);

        } else if (id == R.id.nav_international) {
            //show international news
            /*String URL="https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="International";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent j=new Intent(this,MainActivity.class );
            j.putExtra("URL", "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            j.putExtra("Cat", "International");
            startActivity(j);
        } else if (id == R.id.sports) {
            /*String URL="https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Sports";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent k=new Intent(this,MainActivity.class );
            k.putExtra("URL", "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            k.putExtra("Cat", "Sports");
            startActivity(k);

        } else if (id == R.id.nav_business) {
          // show all news items
            /*String URL="https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Business";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent l=new Intent(this,MainActivity.class );
            l.putExtra("URL", "https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            l.putExtra("Cat", "Business");
            startActivity(l);
        } else if (id == R.id.nav_tech) {
            /*String URL="https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Technology";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent m=new Intent(this,MainActivity.class );
            m.putExtra("URL", "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            m.putExtra("Cat", "Technology");
            startActivity(m);
        }
        else if (id == R.id.nav_Cricket) {
            /*String URL="https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Cricket";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent n=new Intent(this,MainActivity.class );
            n.putExtra("URL", "https://newsapi.org/v1/articles?source=espn-cric-info&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            n.putExtra("Cat", "Cricket");
            startActivity(n);

        }
        else if (id == R.id.nav_Music) {
            /*String URL="https://newsapi.org/v1/articles?source=mtv-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Music";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
            Intent o=new Intent(this,MainActivity.class );
            o.putExtra("URL", "https://newsapi.org/v1/articles?source=mtv-news&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6");
            o.putExtra("Cat", "Music");
            startActivity(o);
        }
        else if (id == R.id.nav_Gaming) {
            /*String URL="https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
            String CAT="Gaming";
            this.CAT=CAT;
            initColumns();
            splash_screen splash=new splash_screen();
            splash.Inserting_records(URL,CAT,mdbHelper,context);
            thread.run();*/
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

    /*public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }*/


}
