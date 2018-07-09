package com.Newsforest.android.Newsforest;

import android.graphics.Bitmap;

/**
 * Created by Bharat on 4/1/2017.
 */
public class NewsModel {
    public String author;
    public String Description;
    public String title;
    public String url;
    public String image2url;
    public Bitmap Imagebitmap;

    public NewsModel() {
    }

    public NewsModel(String author, String Description , String title, String url, String image2url) {
        super();
        this.author = author;
        this.title = title;
        this.url = url;
        this.Description = Description;
        this.image2url=image2url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getimage() {
        return image2url;
    }

    public void setimage(String image2url) {this.image2url = image2url;}

    public Bitmap getbitmapimage(){return Imagebitmap;}

    public void setbitmapimage(Bitmap Imagebitmap){this.Imagebitmap=Imagebitmap;}
}
