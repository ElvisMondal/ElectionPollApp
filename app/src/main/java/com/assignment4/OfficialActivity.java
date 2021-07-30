package com.assignment4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class OfficialActivity extends AppCompatActivity {

    private ImageView imageView, imageView2, imageView3, imageView4;
    ImageView ptyView;
    TextView address, titleAdd, titleNumber, pNum,titleUrl,uNum,location,eNum,titleE,ps,nm,pty,t,te,tex,text,texts;

    private static final String Democrat = "https://democrats.org/";
    private static final String Republic = "https://www.gop.com/";

    private static final String TAG = "MainActivityTag";
    String pht;
    String adds,adds2,adds3;
    String prty, phoneN,City,State,Zip,Urlss,FB,TW,YT,Cty,Stt,Zps,email,posis,nams;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        ptyView = findViewById(R.id.partys);
        address = findViewById(R.id.textView2);
        titleAdd = findViewById(R.id.textView);
        titleNumber = findViewById(R.id.Number);
        pNum = findViewById(R.id.textView3);
        titleUrl = findViewById(R.id.textView4);
        uNum = findViewById(R.id.textView5);
        location=findViewById(R.id.loc);
        titleE=findViewById(R.id.em);
        eNum=findViewById(R.id.textView13);


        ps=findViewById(R.id.textView12);
        nm=findViewById(R.id.textView14);
        pty=findViewById(R.id.textView15);

        Intent inte = getIntent();
        pht = inte.getStringExtra("Photo");
        adds = inte.getStringExtra("Address");
        adds2 = inte.getStringExtra("Address2");
        adds3 = inte.getStringExtra("Address3");
        prty = inte.getStringExtra("Party");
        phoneN = inte.getStringExtra("Phone");
        City = inte.getStringExtra("City");
        State = inte.getStringExtra("State");
        Zip = inte.getStringExtra("Zip");
        Urlss = inte.getStringExtra("URL");
        email = inte.getStringExtra("EMAIL");
        FB = inte.getStringExtra("FB");
        TW = inte.getStringExtra("TW");
        YT = inte.getStringExtra("YT");
        Cty = inte.getStringExtra("CT");
        Stt = inte.getStringExtra("ST");
        Zps = inte.getStringExtra("ZP");
        posis = inte.getStringExtra("Position");
        nams = inte.getStringExtra("Name");


        titleAdd.setTextColor(Color.WHITE);
        address.setTextColor(Color.WHITE);
        titleNumber.setTextColor(Color.WHITE);
        pNum.setTextColor(Color.WHITE);
        titleUrl.setTextColor(Color.WHITE);
        uNum.setTextColor(Color.WHITE);
        titleE.setTextColor(Color.WHITE);
        eNum.setTextColor(Color.WHITE);
        ps.setTextColor(Color.WHITE);
        nm.setTextColor(Color.WHITE);
        pty.setTextColor(Color.WHITE);


        ps.setText(posis);
        nm.setText(nams);
        pty.setText(getString(R.string.Position, "(",prty , ")"));

        if(adds.isEmpty()) {
            titleAdd.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
        }
        else{

            String a=adds2;
            String b=adds3;
            if((!a.isEmpty()) && (b.isEmpty())) {              //((!a.isEmpty()) && (!b.isEmpty()))
                address.setText(adds+"\n"+a+"\n"+City+"\n"+State+"\n"+Zip);
            }

            else if((!a.isEmpty()) && (!b.isEmpty())){         //((!a.isEmpty()) && (b.isEmpty()))
                address.setText(adds+"\n"+a+"\n"+b+"\n"+City+"\n"+State+"\n"+Zip);
            }
            else{
                address.setText(adds+a+"\n"+City+"\n"+State+"\n"+Zip);
            }

        }


        if(phoneN.isEmpty()){
            titleNumber.setVisibility(View.GONE);
            pNum.setVisibility(View.GONE);
        }

        else {
            pNum.setText(phoneN);
        }

        if(Urlss.isEmpty()){

            titleUrl.setVisibility(View.GONE);
            uNum.setVisibility(View.GONE);
        }
        else {
            uNum.setText(Urlss);
        }

        location.setText(Cty+","+" "+Stt+" "+Zps);


        if(email.isEmpty()){
            titleE.setVisibility(View.GONE);
            eNum.setVisibility(View.GONE);
        }

        else {
            eNum.setText(email);
        }

        pNum.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(pNum, Linkify.ALL);

        address.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(address, Linkify.ALL);

        uNum.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(uNum, Linkify.ALL);

        eNum.setLinkTextColor(Color.WHITE);
        Linkify.addLinks(eNum, Linkify.ALL);




        if (prty.equals("Democratic Party")) {

            ptyView.setImageResource(R.drawable.dem_logo);
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);


        } else if (prty.equals("Republican Party")) {
            ptyView.setImageResource(R.drawable.rep_logo);
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        } else {

            ptyView.setVisibility(View.GONE);
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);


        }


        if(FB.isEmpty()){

            imageView2.setVisibility(View.GONE);
        }

        if(TW.isEmpty()){
            imageView3.setVisibility(View.GONE);
        }

        if(YT.isEmpty()){
            imageView4.setVisibility(View.GONE);
        }

        if (!checkNetworkConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Photo Cannot be Loaded");
            AlertDialog dialog = builder.create();
            dialog.show();

            imageView.setImageResource(R.drawable.brokenimage);
            return;
        }

        if (pht.isEmpty()) {
            imageView.setImageResource(R.drawable.missing);
        } else {
            final long start = System.currentTimeMillis();

            Picasso.get().load(pht)
                    .error(R.drawable.missing)
                    .placeholder(R.drawable.placeholder)
                    //.into(imageView);
                    .into(imageView,
                            new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: Size: " +
                                            ((BitmapDrawable) imageView.getDrawable()).getBitmap().getByteCount());
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



    public void OpenAct(View v){

        if(pht.isEmpty()){

            new OfficialActivity();
        }
        else {
            Intent tn = new Intent(this, PhotoActivity.class);
            tn.putExtra("PURL", pht);
            tn.putExtra("Positions", posis);
            tn.putExtra("Names", nams);
            tn.putExtra("Party", prty);
            tn.putExtra("City", Cty);
            tn.putExtra("State", Stt);
            tn.putExtra("Zip", Zps);
            startActivity(tn);
        }
    }



    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + FB;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" + FB;
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }


    public void twitterClicked(View v) {
        Intent intent = null;
        String name = TW;
        try {
            // get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }


    public void youTubeClicked(View v) {
        String name = YT;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));

        }


    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public void politicalSite(View v){

        if(prty.equals("Democratic Party")){

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Democrat));
            startActivity(i);
        }

        else{

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Republic));
            startActivity(i);

        }

    }
}