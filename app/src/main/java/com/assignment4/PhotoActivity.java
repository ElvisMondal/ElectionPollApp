package com.assignment4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    String PUrls,Ps,nms,Pty,Cy,St,Zp;
    ImageView photo,pys;
    TextView Pos,Nam,lk,pyt;
    private static final String TAG = "MainActivityTag";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photo=findViewById(R.id.imageView);
        Pos=findViewById(R.id.textView16);
        Nam=findViewById(R.id.textView17);
        //pyt=findViewById(R.id.textView18);
        pys=findViewById(R.id.logo);
        lk=findViewById(R.id.locs);

        Intent ints=getIntent();
        PUrls=ints.getStringExtra("PURL");
        Ps=ints.getStringExtra("Positions");
        nms=ints.getStringExtra("Names");
       Pty=ints.getStringExtra("Party");
        Cy=ints.getStringExtra("City");
        St=ints.getStringExtra("State");
        Zp=ints.getStringExtra("Zip");


       Pos.setTextColor(Color.WHITE);
       Nam.setTextColor(Color.WHITE);
      // pyt.setTextColor(Color.WHITE);
        Pos.setText(Ps);
        Nam.setText(nms);
        //pyt.setText(getString(R.string.Position, "(",Pty , ")"));

        lk.setText(Cy+","+" "+St+" "+Zp);

        if (Pty.equals("Democratic Party")) {

            pys.setImageResource(R.drawable.dem_logo);
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);


        } else if (Pty.equals("Republican Party")) {
            pys.setImageResource(R.drawable.rep_logo);
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        } else {

            pys.setVisibility(View.GONE);
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);


        }


        if (!checkNetworkConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Photo Cannot be Loaded");
            AlertDialog dialog = builder.create();
            dialog.show();

            photo.setImageResource(R.drawable.brokenimage);
            return;
        }

        if (PUrls.isEmpty()) {
            photo.setImageResource(R.drawable.missing);
        } else {
            final long start = System.currentTimeMillis();

            Picasso.get().load(PUrls)
                    .error(R.drawable.missing)
                    .placeholder(R.drawable.placeholder)
                    //.into(imageView);
                    .into(photo,
                            new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: Size: " +
                                            ((BitmapDrawable) photo.getDrawable()).getBitmap().getByteCount());
                                    long dur = System.currentTimeMillis() - start;
                                    Log.d(TAG, "onSuccess: Time: " + dur);
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.d(TAG, "onError: " + e.getMessage());
                                }
                            });

        }
    }
    private boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}