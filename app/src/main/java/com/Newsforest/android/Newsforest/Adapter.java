package com.Newsforest.android.Newsforest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.Newsforest.android.Newsforest.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Bharat on 3/27/2017.
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<NewsModel> mModelList;
    int day,month,year;
    String[] DateArray;
    private Bitmap bmp;


    //TextView TV = (TextView) findViewById(R.id.resp);
    //TV.setText("Task Successful");
    //Main4Activity m4a = new Main4Activity();

    public Adapter(Context mContext, List<NewsModel> mModelList) {
        this.mContext = mContext;
        this.mModelList = mModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.costume_row, parent, false);
        Item item = new Item(row);
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH)+1;
        year=cal.get(Calendar.YEAR);
        return item;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
       /* try{ URL ur=new URL(mModelList.get(position).getimage());
            bmp=BitmapFactory.decodeStream(ur.openConnection().getInputStream());
        }catch (IOException e){e.printStackTrace();}
       */ //byte[] byteimage1 = getBytes(bmp);
        DateArray=mModelList.get(position).getDateArray();
        Log.d("size of mModellist ",DateArray.length+"");
        ((Item) holder).textView.setText(mModelList.get(position).getTitle());
        ((Item) holder).TV.setText(mModelList.get(position).getDescription());
        //((Item) holder).imageView.setImageBitmap(mModelList.get(position).getbitmapimage());
        Glide.with(mContext).load(mModelList.get(position).getimage()).placeholder(R.drawable.loadjungle).crossFade().into(((Item) holder).imageView);

        if (TextUtils.isEmpty(mModelList.get(position).getDateString())  ||  TextUtils.equals(mModelList.get(position).getDateString(),"null")){
            ((Item) holder).flame.setVisibility(View.INVISIBLE);
        }else if(year!=Integer.parseInt(DateArray[0])   &&  month!=Integer.parseInt(DateArray[1])   &&    day!=Integer.parseInt(DateArray[2])){
            ((Item) holder).flame.setVisibility(View.INVISIBLE);
        }

        // ((Item)holder).imageView.setImageBitmap(getBitmapfromUrl(mModelList.get(position).getUrltoimage()));
        ((Item) holder).itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext ,"Clicker at postion"+position, Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(mContext , Main4Activity.class);
                // m4a.setwebview(mModelList.get(position).getUrl()); //<----This is wrong way of doing you should pass the data to the next activity then show it from there
                //i.putExtra("url",mModelList.get(position).getUrl());
                /*YoYo.with(Techniques.SlideInRight)
                        .duration(1000)
                        .repeat(0)
                        .playOn(((Item)holder).imageView);*/
                Intent cameraIntent = new Intent(mContext, Main4Activity.class);
                String APIurl = mModelList.get(position).getUrl();
                cameraIntent.putExtra("URL", APIurl)
                        .putExtra("desc", mModelList.get(position).getDescription())
                        .putExtra("title", mModelList.get(position).getTitle());
                //cameraIntent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                mContext.startActivity(cameraIntent);

                //startActivity(i);
            }
        }

        );
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView flame;
        Button button;
        TextView TV;
        ImageView imageView;

        public Item(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item);
            //button= (Button) itemView.findViewById(R.id.button);
            TV = (TextView) itemView.findViewById(R.id.resp);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            flame=(ImageView)itemView.findViewById(R.id.flame);
            // TV.setText("Task Successful");

        }
    }

    /*public static Bitmap getBitmapFromURL(String src) {

        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }*/

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.train);
            return bitmap;

        }
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
