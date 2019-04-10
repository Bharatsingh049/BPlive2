package com.Newsforest.android.Newsforest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Newsforest.android.Newsforest.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Bharat on 4/7/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Item> {
    Context mContext;
    List<Matches> mModelList;
    //LinearLayout linear= new LinearLayout(mContext);;
            //searchPin.getLayoutParams();

    //TextView TV = (TextView) findViewById(R.id.resp);
    //TV.setText("Task Successful");
   // Main4Activity m4a = new Main4Activity();

    public MyAdapter(Context mContext, List<Matches> mModelList1) {
        this.mContext = mContext;
        this.mModelList = mModelList1;
    }

    @Override
    public Item onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.match_scores, parent, false);
        Item item = new Item(row);
        return item;

    }

    @Override
    public void onBindViewHolder(final Item holder, final int position) {

        holder.team1.setText(mModelList.get(position).getTeam1());
        holder.team2.setText(mModelList.get(position).getTeam2());
        holder.date.setText(mModelList.get(position).getDate());
        holder.Time.setText(mModelList.get(position).getTime());
        if ( mModelList.get(position).getMatchStarted() == "true") {
            holder.LIVE.setVisibility(View.VISIBLE);
            holder.LIVE.setText("LIVE");
           // ((Item) holder).LIVE.setBackgroundColor(Color.parseColor("#f44336"));
        }
        else{
            holder.LIVE.setVisibility(View.GONE);
            //((Item) holder).LIVE.setText("   ");
            //((Item) holder).LIVE.setBackgroundColor(Color.parseColor("#eceff1"));
        }


            if (TextUtils.equals(mModelList.get(position).getTeam1(),mModelList.get(position).getWinner())){
                holder.Winner1.setVisibility(View.VISIBLE);
                holder.LIVE.setVisibility(View.GONE);
            }else if (TextUtils.equals(mModelList.get(position).getTeam2(),mModelList.get(position).getWinner())){
                holder.Winner2.setVisibility(View.VISIBLE);
                holder.LIVE.setVisibility(View.GONE);
            }else {
                holder.Winner1.setVisibility(View.GONE);
                holder.Winner2.setVisibility(View.GONE);
            }




        /*Glide.with(mContext)
                .load(mModelList.get(position).getUrltoimage())
                .placeholder(R.drawable.train)
                .crossFade()
                .into(((Item) holder).imageView);
         */
        // ((Item)holder).imageView.setImageBitmap(getBitmapfromUrl(mModelList.get(position).getUrltoimage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            int UIP=(mModelList.get(position).getUnique_id());
            String UI = Integer.toString(UIP);
            String t1=mModelList.get(position).getTeam1();
            String t2=mModelList.get(position).getTeam2();


            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext ,"Clicker at postion"+position, Toast.LENGTH_SHORT).show();
                if (mModelList.get(position).getMatchStarted()=="true") {
                    String temp=mModelList.get(position).getWinner()+" "+mModelList.get(position).getTeam1()
                            +" "+mModelList.get(position).getTeam2();
                    Log.d("onClick: ",temp);
                    Intent i = new Intent(mContext, Main3Activity.class);
                    i.putExtra("Matches",mModelList.get(position));
                    mContext.startActivity(i);
                }
                else{
                   Toast.makeText(mContext ,"Match has not yet Started "+mModelList.get(position).getWinner(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        );
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView team1;
        ImageView Winner1,Winner2;
        Button button;
        TextView team2;
        TextView date;
        TextView LIVE;
        TextView Time;

        public Item(View itemView) {

            super(itemView);
            team1 = (TextView) itemView.findViewById(R.id.team1);
            //button= (Button) itemView.findViewById(R.id.button);
            team2 = (TextView) itemView.findViewById(R.id.team2);
            date = (TextView) itemView.findViewById(R.id.date);
            LIVE = (TextView) itemView.findViewById(R.id.LIVE);
            Time=itemView.findViewById(R.id.time);
            Winner1=itemView.findViewById(R.id.winner1);
            Winner2=itemView.findViewById(R.id.winner2);
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

    /*public Bitmap getBitmapfromUrl(String imageUrl) {
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
    }*/
}
