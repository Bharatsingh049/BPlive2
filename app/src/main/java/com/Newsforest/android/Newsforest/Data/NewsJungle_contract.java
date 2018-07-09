package com.Newsforest.android.Newsforest.Data;

import android.provider.BaseColumns;

/**
 * Created by Bharat on 02/14/18.
 */

public class NewsJungle_contract {
    private NewsJungle_contract(){}

    public static final class National_News_entry implements BaseColumns{

    public static final String Table_Name="National_News";


    //public final static String National_News_id="National_ID";
    public final static String Column_News_Title="Title";
    public final static String Column_News_Description="Description";
    public final static String Column_News_Image="News_Image";
    public final static String Column_News_Url="News_Url";
    }

    public static final class International_News_entry implements BaseColumns{

        public static final String Table_Name="International_News";


       // public final static String International_News_id="International_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Business_News_entry implements BaseColumns{

        public static final String Table_Name="Business_News";


        //public final static String Business_News_id="Business_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Sports_News_entry implements BaseColumns{

        public static final String Table_Name="Sports_News";


       // public final static String Sports_News_id="Sports_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Technology_News_entry implements BaseColumns{

        public static final String Table_Name="Technology_News";


       // public final static String Technology_News_id="Technology_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Cricket_News_entry implements BaseColumns{

        public static final String Table_Name="Cricket_News";


        //public final static String Cricket_News_id="Cricket_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Music_News_entry implements BaseColumns{

        public static final String Table_Name="Music_News";


       // public final static String Music_News_id="Music_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }

    public static final class Gaming_News_entry implements BaseColumns{

        public static final String Table_Name="Gaming_News";


        //public final static String Gaming_News_id="Gaming_ID";
        public final static String Column_News_Title="Title";
        public final static String Column_News_Description="Description";
        public final static String Column_News_Image="News_Image";
        public final static String Column_News_Url="News_Url";
    }
}
