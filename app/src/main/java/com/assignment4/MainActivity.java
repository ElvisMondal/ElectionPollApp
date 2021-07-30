package com.assignment4;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final List<OfficialClass> rankList = new ArrayList<>();

    private RecyclerView recyclerView;

    private static int MY_LOCATION_REQUEST_CODE_ID = 111;
    private LocationManager locationManager;
    private Criteria criteria;

    int pos;
    OfficialClass ed, c;
    double lat, lon;
    GovAdapter nAdap;

    TextView title;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);

        nAdap = new GovAdapter(rankList, this);
        recyclerView.setAdapter(nAdap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        title = findViewById(R.id.textView6);

        if (!checkNetworkConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Data cannot be accessed/loaded without an internet connection");
            AlertDialog dialog = builder.create();
            dialog.show();
            title.setText("No Data For Location");

            return;
        }


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        // use gps for location
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        // use network for location
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        //criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE_ID);

        } else {

              getAddress();
        }


    }

    private void getAddress() {
        String bestProvider = locationManager.getBestProvider(criteria, true);

        Location currentLocation = null;
        if (bestProvider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            currentLocation = locationManager.getLastKnownLocation(bestProvider);
        }
        if (currentLocation != null) {
            lat = currentLocation.getLatitude();
            lon = currentLocation.getLongitude();
        } else {
            Toast.makeText(this, "No Location", Toast.LENGTH_LONG).show();

        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses;

            addresses = geocoder.getFromLocation(lat, lon, 1);

            displayAddresses(addresses);
            //Log.d(TAG, "onCreate: " + addresses.get(0));
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull
            String[] permissions, @NonNull
                    int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                getAddress();
               return;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Permission");
        builder.setMessage("Data cannot be accessed/loaded without Permission");
        AlertDialog dialog = builder.create();
        dialog.show();
        title.setText("No Permission");



    }


    private void displayAddresses(List<Address> addresses) {
        StringBuilder sb = new StringBuilder();
        if (addresses.size() == 0) {
            Toast.makeText(this, "Nothing Found", Toast.LENGTH_LONG).show();
            return;
        }

        for (Address ad : addresses) {

            String a = String.format("%s ",
                    (ad.getPostalCode() == null ? "" : ad.getPostalCode()));

            if (!a.trim().isEmpty())
                sb.append(a.trim());

            // sb.append("\n");
        }
        CivicInformation civicInformationRunnable = new CivicInformation(this, sb.toString());
        new Thread(civicInformationRunnable).start();
       // Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();//Main Value is in "sb"
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.locations_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.add:

                civicicall();

                return true;

            case R.id.info:

                Intent intr = new Intent(this, AboutActivity.class);
                startActivity(intr);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("SetTextI18n")
    public void dialg(){

        if (!checkNetworkConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Data cannot be accessed/loaded without an internet connection");
            AlertDialog dialog = builder.create();
            dialog.show();
            title.setText("No Data For Location");
            return;
        }
    }


    public void civicicall(){


        if (!checkNetworkConnection()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Network Connection");
            builder.setMessage("Data cannot be accessed/loaded without an internet connection");
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setView(et);

        //builder.setIcon(R.drawable.icon1);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialg();
                rankList.clear();
                //Toast.makeText(MainActivity.this, et.getText().toString(), Toast.LENGTH_LONG).show();
                CivicInformation civicInformationsRunnable = new CivicInformation(MainActivity.this, et.getText().toString());
                new Thread(civicInformationsRunnable).start();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(MainActivity.this, "You changed your mind!", Toast.LENGTH_SHORT).show();

            }
        });


        builder.setMessage("");
        builder.setTitle("Enter a City,State or a Zip Code:");

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    @SuppressLint("SetTextI18n")
    public void addInfo(ArrayList<OfficialClass> oList) {

        rankList.addAll(oList);
        nAdap.notifyDataSetChanged();

        c = rankList.get(0);
        title.setText(c.getCity() + "," + " " + c.getState() + " " + c.getZip());
    }

    @Override
    public void onClick(View view) {
        pos = recyclerView.getChildLayoutPosition(view);
        ed = rankList.get(pos);

        Intent intent = new Intent(this, OfficialActivity.class);
        intent.putExtra("Photo", ed.getPhotoUrl());
        intent.putExtra("Address", ed.getLineone());
        intent.putExtra("Address2", ed.getLinetwo());
        intent.putExtra("Address3", ed.getLinethree());
        intent.putExtra("City", ed.getCitys());
        intent.putExtra("State", ed.getStates());
        intent.putExtra("Zip", ed.getZips());

        intent.putExtra("Party", ed.getParty());
        intent.putExtra("Phone", ed.getPh());
        intent.putExtra("URL", ed.getUrls());
        intent.putExtra("EMAIL", ed.getMail());
        intent.putExtra("Position",ed.getPosition());
        intent.putExtra("Name",ed.getNames());
        intent.putExtra("FB", ed.getFb());
        intent.putExtra("TW", ed.getTw());
        intent.putExtra("YT", ed.getYt());
        intent.putExtra("CT", rankList.get(0).getCity());
        intent.putExtra("ST", rankList.get(0).getState());
        intent.putExtra("ZP", rankList.get(0).getZip());


        startActivity(intent);


    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
