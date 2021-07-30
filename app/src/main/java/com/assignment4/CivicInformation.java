package com.assignment4;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class CivicInformation implements Runnable {

    private static final String DATA_URL = " https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyA0CU3_ZfJc1zw3vG1Rqv7126H9zq2mdTk&address=";
    private MainActivity mainActivity;
    private String zip;
    //private static final String UR="zip-code";

    String City, State, Zip, position, names, lineone, linetwo,linethree, Citys, States, Zips, party, ph, Urls, Mail, PhotoUrl, fb, tw, yt;
    int value, cv = 0;


    public CivicInformation(MainActivity mainActivity, String zip) {
        this.mainActivity = mainActivity;
        this.zip = zip;
    }

    @Override
    public void run() {

        Uri dataUri = Uri.parse(DATA_URL + zip);
        String urlToUse = dataUri.toString();

        Log.d(TAG, "run: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK: " + conn.getResponseCode());
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

            Log.d(TAG, "run: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
            return;
        }

        handleResults(sb.toString());
        Log.d(TAG, "run: ");


    }


    private void handleResults(String s) {

        final ArrayList<OfficialClass> rankList = parserJSON(s);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.addInfo(rankList);
            }
        });
    }

    private ArrayList<OfficialClass> parserJSON(String s) {

        ArrayList<OfficialClass> rankList = new ArrayList<>();

        try {

            JSONObject jsonObject = new JSONObject(s);
            JSONObject normalizedObject = jsonObject.getJSONObject("normalizedInput");


            City = normalizedObject.getString("city");
            State = normalizedObject.getString("state");
            Zip = normalizedObject.getString("zip");

            //1st Array
            JSONArray Offices = jsonObject.getJSONArray("offices");

            for (int i = 0; i < Offices.length(); i++) {

                position = "";


                JSONObject cObj = Offices.getJSONObject(i);
                position = cObj.getString("name");

                JSONArray Officials = jsonObject.getJSONArray("officials");

                JSONArray Indices = cObj.getJSONArray("officialIndices");
                for (int p = 0; p < Indices.length(); p++) {


                    names = "";
                    lineone = "";
                    linetwo = "";
                    linethree="";
                    Citys = "";
                    States = "";
                    Zips = "";
                    party = "";
                    ph = "";
                    Urls = "";
                    Mail = "";
                    PhotoUrl = "";
                    fb = "";
                    tw = "";
                    yt = "";
                    value = 0;


                    value = Indices.getInt(p);
                    JSONObject cObjs = Officials.getJSONObject(value);


                    names = cObjs.getString("name");

                    if (cObjs.has("address")) {
                        JSONArray Address = cObjs.getJSONArray("address");
                        for (int e = 0; e < Address.length(); e++) {
                            JSONObject Objs = Address.getJSONObject(e);

                            lineone = Objs.getString("line1");
                            if (!Objs.isNull("line2")) {
                                linetwo = Objs.getString("line2");
                            }

                            if (!Objs.isNull("line3")) {
                                linethree = Objs.getString("line3");
                            }
                            Citys = Objs.getString("city");
                            States = Objs.getString("state");
                            Zips = Objs.getString("zip");

                        }
                    }

                    party = cObjs.getString("party");

                    if (cObjs.has("phones")) {
                        JSONArray Phones = cObjs.getJSONArray("phones");
                        for (int g = 0; g < Phones.length(); g++) {
                            ph = Phones.getString(0);

                        }
                    }


                    if (cObjs.has("urls")) {
                        JSONArray URLS = cObjs.getJSONArray("urls");
                        for (int h = 0; h < URLS.length(); h++) {
                            Urls = URLS.getString(0);

                        }
                    }

                    if (cObjs.has("emails")) {
                        JSONArray mail = cObjs.getJSONArray("emails");
                        for (int y = 0; y < mail.length(); y++) {
                            Mail = mail.getString(0);
                        }
                    }


                    if (cObjs.has("photoUrl")) {
                        PhotoUrl = cObjs.getString("photoUrl");
                    }


                    if (cObjs.has("channels")) {
                        JSONArray Channels = cObjs.getJSONArray("channels");
                        for (int m = 0; m < Channels.length(); m++) {
                            JSONObject Objects = Channels.getJSONObject(m);
                            if (Objects != null) {
                                if (Objects.getString("type").equals("Facebook"))
                                    fb = Objects.getString("id");
                                if (Objects.getString("type").equals("Twitter"))
                                    tw = Objects.getString("id");
                                if (Objects.getString("type").equals("YouTube"))
                                    yt = Objects.getString("id");
                            }
                        }
                    }

                    rankList.add(new OfficialClass(City, State, Zip, position, names, lineone, linetwo,linethree, Citys, States, Zips, party, ph, Urls, Mail, PhotoUrl, fb, tw, yt, value));


                }


            }


            return rankList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}

