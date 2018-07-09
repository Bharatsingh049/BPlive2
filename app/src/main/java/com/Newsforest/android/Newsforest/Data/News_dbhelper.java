package com.Newsforest.android.Newsforest.Data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.National_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.International_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Business_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Technology_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Music_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Sports_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Cricket_News_entry;
import com.Newsforest.android.Newsforest.Data.NewsJungle_contract.Gaming_News_entry;



/**
 * Created by Bharat on 02/14/18.
 */

public class News_dbhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="NewsJungle.db";
    private static final int DATABASE_VERSION=1;
    public News_dbhelper(Context context) {super(context,DATABASE_NAME,null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

String SQL_CREATE_NATIONAL_NEWS_TABLE="CREATE TABLE "+ National_News_entry.Table_Name+"("
        //+National_News_entry.National_News_id+" INTEGER NOT NULL , "
        +National_News_entry.Column_News_Image+" TEXT ,   "
        +National_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
        +National_News_entry.Column_News_Description+" TEXT NOT NULL, "
        +National_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_INTERNATIONAL_NEWS_TABLE="CREATE TABLE "+ International_News_entry.Table_Name+"("
                //+International_News_entry.International_News_id+" INTEGER NOT NULL , "
                +International_News_entry.Column_News_Image+" TEXT ,   "
                +International_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +International_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +International_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_BUSINESS_NEWS_TABLE="CREATE TABLE "+ Business_News_entry.Table_Name+"("
                //+Business_News_entry.Business_News_id+" INTEGER NOT NULL , "
                +Business_News_entry.Column_News_Image+"  TEXT ,  "
                +Business_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Business_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Business_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_TECHNOLOGY_NEWS_TABLE="CREATE TABLE "+ Technology_News_entry.Table_Name+"("
                //+Technology_News_entry.Technology_News_id+" INTEGER NOT NULL , "
                +Technology_News_entry.Column_News_Image+" TEXT ,   "
                +Technology_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Technology_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Technology_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_SPORTS_NEWS_TABLE="CREATE TABLE "+ Sports_News_entry.Table_Name+"("
                //+Sports_News_entry.Sports_News_id+" INTEGER NOT NULL , "
                +Sports_News_entry.Column_News_Image+" TEXT ,   "
                +Sports_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Sports_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Sports_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_GAMING_NEWS_TABLE="CREATE TABLE "+ Gaming_News_entry.Table_Name+"("
                //+Gaming_News_entry.Gaming_News_id+" INTEGER NOT NULL , "
                +Gaming_News_entry.Column_News_Image+" TEXT ,   "
                +Gaming_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Gaming_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Gaming_News_entry.Column_News_Url+" TEXT NOT NULL); ";


        String SQL_CREATE_MUSIC_NEWS_TABLE="CREATE TABLE "+ Music_News_entry.Table_Name+"("
                //+Music_News_entry.Music_News_id+" INTEGER NOT NULL , "
                +Music_News_entry.Column_News_Image+" TEXT ,   "
                +Music_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Music_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Music_News_entry.Column_News_Url+" TEXT NOT NULL); ";



        String SQL_CREATE_CRICKET_NEWS_TABLE="CREATE TABLE "+ Cricket_News_entry.Table_Name+"("
                //+Cricket_News_entry.Cricket_News_id+" INTEGER NOT NULL , "
                +Cricket_News_entry.Column_News_Image+" TEXT ,   "
                +Cricket_News_entry.Column_News_Title+" TEXT PRIMARY KEY, "
                +Cricket_News_entry.Column_News_Description+" TEXT NOT NULL, "
                +Cricket_News_entry.Column_News_Url+" TEXT NOT NULL); ";

        db.execSQL(SQL_CREATE_NATIONAL_NEWS_TABLE);
        db.execSQL(SQL_CREATE_INTERNATIONAL_NEWS_TABLE);
        db.execSQL(SQL_CREATE_BUSINESS_NEWS_TABLE);
        db.execSQL(SQL_CREATE_TECHNOLOGY_NEWS_TABLE);
        db.execSQL(SQL_CREATE_SPORTS_NEWS_TABLE);
        db.execSQL(SQL_CREATE_GAMING_NEWS_TABLE);
        db.execSQL(SQL_CREATE_MUSIC_NEWS_TABLE);
        db.execSQL(SQL_CREATE_CRICKET_NEWS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
