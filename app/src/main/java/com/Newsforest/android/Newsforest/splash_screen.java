package com.Newsforest.android.Newsforest;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Newsforest.android.Newsforest.R;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.Newsforest.android.Newsforest.Data.News_dbhelper;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.National_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.International_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Business_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Sports_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Technology_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Gaming_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Music_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Cricket_News_entry;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class splash_screen extends AppCompatActivity    {
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private String  API_URL="https://newsapi.org/v2/top-headlines?source=the-times-of-india&language=en&country=in&sortBy=top&apiKey=40ab81231e4c43fd925cdc3a0d08f7f6";
    private String CAT;
    private Context context;
    private String url;
    private ImageView ForestImage;
    private TextView TextVersion;

    Context con;
    //private Cursor cursor,cur;
   // private SQLiteDatabase dbr,dbw ;

    private String tablename,columnttitle,columndesription,columnimage,columnurl,columndate;
    private Bitmap bmp,bmp1;
    private int Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_forest);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ForestImage=(ImageView)findViewById(R.id.forest_id);
        TextVersion=(TextView)findViewById(R.id.app_version);

        tablename= National_News_entry.Table_Name;
        columnttitle=National_News_entry.Column_News_Title;
        columndesription=National_News_entry.Column_News_Description;
        columnimage=National_News_entry.Column_News_Image;
        columndate=National_News_entry.Column_News_Date;
        columnurl=National_News_entry.Column_News_Url;
        context=splash_screen.this;

        News_dbhelper dbhelper=new News_dbhelper(this);
       // SQLiteDatabase dbr = dbhelper.getReadableDatabase();
       // SQLiteDatabase dbw = dbhelper.getWritableDatabase();
     if(TextUtils.isEmpty(CAT)){
         url=API_URL;
         OnCreate(dbhelper);
     }else {
         Inserting_records(API_URL, CAT, dbhelper, context);
     }

//        Log.d("Cat before = ",CAT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Create an Intent that will start the Menu-Activity.

                }
            }, SPLASH_DISPLAY_LENGTH);

        YoYo.with(Techniques.ZoomInUp)
                .duration(2000)
                .repeat(0)
                .playOn(ForestImage);
        YoYo.with(Techniques.FadeInUp)
                .duration(2000)
                .repeat(0)
                .playOn(TextVersion);
    }





    public void OnCreate(final News_dbhelper help) {
       //final Context context=splash_screen.this;
       final   List<NewsModel> mModelList1 =new ArrayList<>();

        //Log.d("NO cursor is not null", cursor.getString(cursor.getColumnIndex(columnttitle)));
        //News_dbhelper helperdb=new News_dbhelper(getApplicationContext());


        //queryBuilder.setTables(ProfessionalTable.TABLE_NAME);


        try {

            StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response: ", response.toString());
                    //mProgressDialog.dismiss();
                    //SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                    //queryBuilder.setTables(tablename);
                    try {
                        JSONObject jObject = new JSONObject(response);
                        String status = jObject.getString("status");
                        //String sortby = jObject.getString("sortBy");
                        Log.e("Response: ", response.toString());
                        if (status.equals("ok")) {
                            JSONArray jsonArray = jObject.getJSONArray("articles");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                Log.e("InnerData: ", jsonobject.toString());
                                NewsModel mDataList = new NewsModel();
                                String date=jsonobject.getString("publishedAt");
                                Log.d("onResponse: ",date);
                                if(!TextUtils.isEmpty(date)  &&  !TextUtils.equals(date,"null")){

                                String[] arr=date.split("-");
                                Log.d("onResponse: ",arr[0]);
                                Log.d("onResponse: ",arr[1]);
                                arr[2]=extractNumber(arr[2]);
                                Log.d("onResponse: ",arr[2]);
                                    mDataList.setDateArray(arr);}


                                mDataList.setDescription(jsonobject.getString("description"));
                                mDataList.setTitle(jsonobject.getString("title"));
                                mDataList.setUrl(jsonobject.getString("url"));

                                mDataList.setimage(jsonobject.getString("urlToImage"));
                                mModelList1.add(mDataList);
                              if(i==jsonArray.length()-1){
                                  Log.d("size of mModellist ",""+mModelList1.size());
                                  Continue_Oncreate(help,mModelList1);
                              }
                                Log.d("size of mModellist ",""+mModelList1.size());
                            }

                        }


                    } catch (NullPointerException ex) {
                        Log.e("Error ", ex.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //mProgressDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(context, "Failed to fetch data." +
                                " Please check your network connection", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof AuthFailureError) {
                        //TODO
                    } else if (error instanceof ServerError) {
                        //TODO
                        Log.e("Server error", error.toString());
                    } else if (error instanceof NetworkError) {
                        //TODO
                        Log.e("Network error", error.toString());
                    } else if (error instanceof ParseError) {
                        //TODO
                        Log.e("Parse Error ", error.toString());
                    }
                }
            }) {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (response.headers == null) {
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
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(jsonArrayRequest);

                Log.d("size of mModellist ",""+mModelList1.size());


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (IllegalFormatException ex) {
            ex.printStackTrace();
        }finally {
            Log.d("size of mModellist ",""+mModelList1.size());
        }




        Log.d("size of mModellist ",""+mModelList1.size());



    }

public void Continue_Oncreate(final News_dbhelper help, final List<NewsModel> mModelList1){


        Log.d("size of mModellist ",""+mModelList1.size());

   final SQLiteDatabase dbr = help.getReadableDatabase();
   final SQLiteDatabase dbw = help.getWritableDatabase();


    final String[] projection = {
            columnimage,
            columnttitle,
            columndesription,
            columndate,
            columnurl
    };
    //  Cursor cursor = dbr.query(tablename, projection, null, null, null, null, null);
    //  Cursor cur = dbr.query(tablename, projection, null, null, null, null, null);

    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
    queryBuilder.setTables(tablename);




   //Cursor cursor= queryBuilder.query(dbr,projection,null,null,null,null,null);

   // Cursor cur= queryBuilder.query(dbr,projection,null,null,null,null,null);


    Log.d("size of mModellist ",""+mModelList1.size());
    //count = cursor.getCount();
    try{
        for (int i = 0; i < mModelList1.size(); i++) {


            final Cursor cursor = dbr.query(tablename, projection, null, null, null, null, null);
            int count=cursor.getCount();
            Log.d("size of mModellist ",""+count);
            cursor.moveToFirst();
            if (cursor.getCount()==0){
                Insert_in_a_row(i,mModelList1,help);
                cursor.moveToLast();
                Log.d("uniq cursor.getCount()=",""+cursor.getCount());
            }



            while (!cursor.isAfterLast()) {
               // SQLiteDatabase dbr = help.getReadableDatabase();
                //Cursor cur = dbr.query(tablename, projection, null, null, null, null, null);
                Cursor cur1= dbr.query(tablename,projection,null,null,null,null,null);
                Log.d("fist cursor.getCount()=",""+cur1.getCount());
                if(cur1.getCount()>0) {


                if (cur1.getCount() <=20) {

                        if (cur1.getCount() > 0 && cur1.getCount() < 20) {
                            //cursor.moveToPosition(j);
                            if (cursor.getString(cursor.getColumnIndex(columnttitle)).equals(mModelList1.get(i).getTitle())) {
                                cursor.moveToLast();
                            }
                            else {
                                if (cursor.isLast()) {
                                    Log.d("NO cursor is at",cursor.getString(cursor.getColumnIndex(columnttitle))) ;

                                    Log.d("NO cursor is at",""+ cursor.getPosition());
                                   // Cursor cur3 = dbr.query(tablename, projection, null, null, null, null, null);
                                    //cur3.moveToLast();
                                   // Id = cur3.getInt(cur3.getColumnIndex(columnid)) + 1;
                                    Insert_in_a_row(i,mModelList1,help);
                                    cursor.moveToLast();
                                    //cur3.close();

                                }

                            }

                        }

                } else {
                    if (cur1.getCount() <= 21){


                        Cursor C1= dbr.query(tablename,projection, null, null, null, null, null);
                        C1.moveToFirst();
                        if(C1.getCount()==21){
                            String rowId = C1.getString(cursor.getColumnIndex(columnttitle));
                            dbw.delete(tablename, columnttitle + "=?", new String[]{rowId});
                            Log.d("record has been deleted", rowId);

                            //long counttt=cursor.getCount();
                           /* Cursor CCC= db.query(tablename, projection, null, null, null, null, null);

                            for (int k = 2; k <= CCC.getCount(); k++) {
                                ContentValues values = new ContentValues();
                                values.put(columnid, --k);
                                dbw.update(tablename, values, columnid + "=" + k, null);
                            }

                            CCC.close();
                          */

                        }
                        C1.close();

                       /*     if (cur1.getCount() == 25) {

                        if (cursor.getString(cursor.getColumnIndex(columnttitle)) != null) {

                            if (cursor.getString(cursor.getColumnIndex(columnttitle)).equals(mModelList1.get(i).getTitle())) {
                                cursor.moveToLast();
                            } else {
                                Cursor cur2 = dbr.query(tablename, projection, null, null, null, null, null);
                                cur2.moveToLast();
                                Id = cur2.getInt(cur2.getColumnIndex(columnid)) + 1;
                                Insert_in_a_row(i, Id, mModelList1, help);
                                cursor.moveToLast();
                                cur2.close();
                            }

                        }
                    }
*/
                }
                }


                }
                cursor.moveToNext();
                cur1.close();

                }if(i==mModelList1.size()-1){
                cursor.close();
                dbr.close();}

        }

        if(context==splash_screen.this) {

            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.putExtra("Cat", CAT);
            context.startActivity(mainIntent);
            splash_screen.this.finish();
        }

    }catch (Exception e){e.printStackTrace();}

}

    public void Inserting_records(final String url1, String cat,News_dbhelper help,Context context){
        //Log.d( "API_URL ",url1);
         url=url1;
        CAT=cat;
       // Log.d( "CAT ",CAT);
        if(context==null){
            this.context=splash_screen.this;
        }else {this.context=context;}
        //if(mdbHelp==null){mdbHelper= new News_dbhelper(getApplicationContext());}

        if("National".equals(cat)){
            tablename= National_News_entry.Table_Name;
            columnttitle=National_News_entry.Column_News_Title;
            columndesription=National_News_entry.Column_News_Description;
            columnimage=National_News_entry.Column_News_Image;
            columndate=National_News_entry.Column_News_Date;
            columnurl=National_News_entry.Column_News_Url;
            //columnid=National_News_entry.National_News_id;
        }
        if("International".equals(cat)){
            tablename= International_News_entry.Table_Name;
            columnttitle=International_News_entry.Column_News_Title;
            columndesription=International_News_entry.Column_News_Description;
            columnimage=International_News_entry.Column_News_Image;
            columndate=International_News_entry.Column_News_Date;
            columnurl=International_News_entry.Column_News_Url;
            //columnid=International_News_entry.International_News_id;
        }
        if("Sports".equals(cat)){
            tablename= Sports_News_entry.Table_Name;
            columnttitle=Sports_News_entry.Column_News_Title;
            columndesription=Sports_News_entry.Column_News_Description;
            columnimage=Sports_News_entry.Column_News_Image;
            columndate=Sports_News_entry.Column_News_Date;
            columnurl=Sports_News_entry.Column_News_Url;
            //columnid=Sports_News_entry.Sports_News_id;
        }
        if("Business".equals(cat)){
            tablename= Business_News_entry.Table_Name;
            columnttitle=Business_News_entry.Column_News_Title;
            columndesription=Business_News_entry.Column_News_Description;
            columnimage=Business_News_entry.Column_News_Image;
            columndate=Business_News_entry.Column_News_Date;
            columnurl=Business_News_entry.Column_News_Url;
            //columnid=Business_News_entry.Business_News_id;
        }
        if("Technology".equals(cat)){
            tablename= Technology_News_entry.Table_Name;
            columnttitle=Technology_News_entry.Column_News_Title;
            columndesription=Technology_News_entry.Column_News_Description;
            columnimage=Technology_News_entry.Column_News_Image;
            columndate=Technology_News_entry.Column_News_Date;
            columnurl=Technology_News_entry.Column_News_Url;
            //columnid=Technology_News_entry.Technology_News_id;
        }
        if("Cricket".equals(cat)){
            tablename= Cricket_News_entry.Table_Name;
            columnttitle=Cricket_News_entry.Column_News_Title;
            columndesription=Cricket_News_entry.Column_News_Description;
            columnimage=Cricket_News_entry.Column_News_Image;
            columndate=Cricket_News_entry.Column_News_Date;
            columnurl=Cricket_News_entry.Column_News_Url;
            //columnid=Cricket_News_entry.Cricket_News_id;
        }
        if("Music".equals(cat)){
            tablename= Music_News_entry.Table_Name;
            columnttitle=Music_News_entry.Column_News_Title;
            columndesription=Music_News_entry.Column_News_Description;
            columnimage=Music_News_entry.Column_News_Image;
            columndate=Music_News_entry.Column_News_Date;
            columnurl=Music_News_entry.Column_News_Url;
            //columnid=Music_News_entry.Music_News_id;
        }
        if("Gaming".equals(cat)){
            tablename= Gaming_News_entry.Table_Name;
            columnttitle=Gaming_News_entry.Column_News_Title;
            columndesription=Gaming_News_entry.Column_News_Description;
            columnimage=Gaming_News_entry.Column_News_Image;
            columndate=Gaming_News_entry.Column_News_Date;
            columnurl=Gaming_News_entry.Column_News_Url;
            //columnid=Gaming_News_entry.Gaming_News_id;
        }

      OnCreate(help);
    }


    public void Insert_in_a_row(final int i,final List<NewsModel> mModelList1,final News_dbhelper help){
        final SQLiteDatabase dbw = help.getWritableDatabase();
        Log.d("size of mModellist ",""+mModelList1.size());
        /*try{ URL ur=new URL(mModelList1.get(i).getimage());
            bmp=BitmapFactory.decodeStream(ur.openConnection().getInputStream());
        }catch (IOException e){e.printStackTrace();}
        byte[] byteimage1 = getBytes(bmp);
       */
        ContentValues values = new ContentValues();
        //values.put(columnid, Id);
        values.put(columnimage, mModelList1.get(i).getimage());
        values.put(columnttitle, mModelList1.get(i).getTitle());
        values.put(columndesription,mModelList1.get(i).getDescription());
        values.put(columndate,mModelList1.get(i).getDateString());
        values.put(columnurl, mModelList1.get(i).getUrl());
        long currentRow_ID = dbw.insert(tablename, null, values);
        if (Id != currentRow_ID) {
            //Toast.makeText(con, "Error with saving news", Toast.LENGTH_LONG).show();
            Log.d("news was not saved",""+currentRow_ID );
        } else {
            // Toast.makeText(con, "You are having " + currentRow_ID + " News", Toast.LENGTH_LONG).show();
            Log.d("news saved on title", ""+currentRow_ID);
        }

        //runnable.run();

    }

     public static String extractNumber(final String str) {
        if(str == null || str.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }
        return sb.toString();
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}

