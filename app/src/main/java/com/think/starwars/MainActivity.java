package com.think.starwars;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.think.starwars.adapter.StarShipAdapter;
import com.think.starwars.model.FilmNameData;
import com.think.starwars.model.StarShipData;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ProgressDialog dialog;
    private StarShipData shipData;
    private FilmNameData filmData;
    private RecyclerView shipList;
    StarShipAdapter shipAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shipList = (RecyclerView) findViewById(R.id.shipDetail_list);

        new GetCartAsync().execute("http://swapi.co/api/starships/?format=json&page=1", "http://swapi.co/api/starships/?format=json&page=2",
                "http://swapi.co/api/starships/?format=json&page=3", "http://swapi.co/api/starships/?format=json&page=4",
                "http://swapi.co/api/films/?format=json");


    }

    private class GetCartAsync extends AsyncTask<String, Void, Boolean> {
        List<StarShipData> ship_data = new ArrayList<>();
        List<FilmNameData> film_data = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(true);
        }


        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //get film titles//
                HttpGet httppost4 = new HttpGet(urls[4]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response4 = httpclient.execute(httppost4);

                // StatusLine stat = response.getStatusLine();
                int status4 = response4.getStatusLine().getStatusCode();
                if (status4 == 200) {
                    HttpEntity entity = response4.getEntity();
                    String data1 = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data1);
                    JSONArray jarray = jsono.getJSONArray("results");
                    //  JSONArray jarray = jsono.getJSONArray(data1);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject json_data = jarray.getJSONObject(i);
                        filmData = new FilmNameData();
                        filmData.film_name = json_data.getString("title");
                        filmData.episode = json_data.getString("episode_id");
                        filmData.series = i + 1;

                        film_data.add(filmData);
                    }
                    // return true;
                    
                }
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data1 = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data1);
                    JSONArray jarray = jsono.getJSONArray("results");
                    //  JSONArray jarray = jsono.getJSONArray(data1);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject json_data = jarray.getJSONObject(i);
                        shipData = new StarShipData();
                        shipData.ship_name = json_data.getString("name");


                        String temp_cost = json_data.getString("cost_in_credits");
                        if (temp_cost.equals("unknown")) {
                            shipData.ship_cost = "0";
                        } else {
                            shipData.ship_cost = temp_cost;
                        }

                        //    shipData.movie_name = json_data.getString("manufacturer");
                        JSONArray jarray_films = json_data.getJSONArray("films");
                        String str_film_name = "";
                        for (int k = 0; k < jarray_films.length(); k++) {

                            //  JSONObject film_nameOBJ = jarray_films.getJSONObject(k);
                            String str = jarray_films.getString(k);
                            str = String.valueOf(str.charAt(str.length() - 2));
                            str_film_name += film_data.get(Integer.parseInt(str) - 1).film_name + "\n";

                        }
                        shipData.movie_name = str_film_name;
                        ship_data.add(shipData);
                    }
                    // return true;
                }

                //------------------>>
                HttpGet httppost1 = new HttpGet(urls[1]);
                HttpResponse response1 = httpclient.execute(httppost1);
                // StatusLine stat = response.getStatusLine();
                int status1 = response1.getStatusLine().getStatusCode();

                if (status1 == 200) {
                    HttpEntity entity = response1.getEntity();
                    String data1 = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data1);
                    JSONArray jarray = jsono.getJSONArray("results");
                    //  JSONArray jarray = jsono.getJSONArray(data1);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject json_data = jarray.getJSONObject(i);
                        shipData = new StarShipData();
                        shipData.ship_name = json_data.getString("name");
                        JSONArray jarray_films = json_data.getJSONArray("films");
                        String str_film_name = "";
                        for (int k = 0; k < jarray_films.length(); k++) {

                            //  JSONObject film_nameOBJ = jarray_films.getJSONObject(k);
                            String str = jarray_films.getString(k);
                            str = String.valueOf(str.charAt(str.length() - 2));
                            str_film_name += film_data.get(Integer.parseInt(str) - 1).film_name + "\n";
                        }
                        shipData.movie_name = str_film_name;
                        String temp_cost = json_data.getString("cost_in_credits");
                        if (temp_cost.equals("unknown")) {
                            shipData.ship_cost = "0";
                        } else {
                            shipData.ship_cost = temp_cost;
                        }
                        ship_data.add(shipData);
                    }
                }
                HttpGet httppost2 = new HttpGet(urls[2]);
                HttpResponse response2 = httpclient.execute(httppost2);
                // StatusLine stat = response.getStatusLine();
                int status2 = response2.getStatusLine().getStatusCode();

                if (status2 == 200) {
                    HttpEntity entity = response2.getEntity();
                    String data1 = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data1);
                    JSONArray jarray = jsono.getJSONArray("results");
                    //  JSONArray jarray = jsono.getJSONArray(data1);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject json_data = jarray.getJSONObject(i);
                        shipData = new StarShipData();
                        shipData.ship_name = json_data.getString("name");
                        JSONArray jarray_films = json_data.getJSONArray("films");
                        String str_film_name = "";
                        for (int k = 0; k < jarray_films.length(); k++) {

                            //  JSONObject film_nameOBJ = jarray_films.getJSONObject(k);
                            String str = jarray_films.getString(k);
                            str = String.valueOf(str.charAt(str.length() - 2));
                            str_film_name += film_data.get(Integer.parseInt(str) - 1).film_name + "\n";
                        }
                        shipData.movie_name = str_film_name;
                        String temp_cost = json_data.getString("cost_in_credits");
                        if (temp_cost.equals("unknown")) {
                            shipData.ship_cost = "0";
                        } else {
                            shipData.ship_cost = temp_cost;
                        }
                        ship_data.add(shipData);
                    }
                }

                HttpGet httppost3 = new HttpGet(urls[3]);
                HttpResponse response3 = httpclient.execute(httppost3);
                // StatusLine stat = response.getStatusLine();
                int status3 = response3.getStatusLine().getStatusCode();

                if (status3 == 200) {
                    HttpEntity entity = response3.getEntity();
                    String data1 = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data1);
                    JSONArray jarray = jsono.getJSONArray("results");
                    //  JSONArray jarray = jsono.getJSONArray(data1);
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject json_data = jarray.getJSONObject(i);
                        shipData = new StarShipData();
                        shipData.ship_name = json_data.getString("name");
                        JSONArray jarray_films = json_data.getJSONArray("films");
                        String str_film_name = "";
                        for (int k = 0; k < jarray_films.length(); k++) {

                            //  JSONObject film_nameOBJ = jarray_films.getJSONObject(k);
                            String str = jarray_films.getString(k);
                            str = String.valueOf(str.charAt(str.length() - 2));
                            str_film_name += film_data.get(Integer.parseInt(str) - 1).film_name + "\n";
                        }
                        shipData.movie_name = str_film_name;
                        String temp_cost = json_data.getString("cost_in_credits");
                        if (temp_cost.equals("unknown")) {
                            shipData.ship_cost = "0";
                        } else {
                            shipData.ship_cost = temp_cost;
                        }

                        ship_data.add(shipData);
                    }

                    return true;
                }

            } catch (ParseException | IOException | JSONException e1) {
                e1.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dialog.cancel();
            //     mSwipeRefreshLayout.setRefreshing(false);

            if (result) {
                List<StarShipData> ship_data_sort = new ArrayList<>();
                Collections.sort(ship_data, new Comparator<StarShipData>() {
                    @Override
                    public int compare(StarShipData cost2, StarShipData cost1) {

                        return (int) (Long.parseLong(cost1.ship_cost) - (Long.parseLong((cost2.ship_cost))));
                    }
                });

                ship_data_sort = new ArrayList<>(ship_data.subList(0, 15));

                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                shipAdapter = new StarShipAdapter(MainActivity.this, ship_data_sort);
                shipList.setAdapter(shipAdapter);
                shipList.setLayoutManager(layoutManager);

            } else {
                Toast.makeText(MainActivity.this, "cant retrive", Toast.LENGTH_SHORT).show();

            }


        }
    }


}