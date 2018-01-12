package com.example.android.sm_termsconditions;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    String domain = "http://savemonkrestenv.us-east-1.elasticbeanstalk.com";
    String tcUrl = domain + "/rest/companyPolicies/terms";
    String id;
    RequestQueue queue;
    ListView listview;
    TCAdapter tcAdapter;
    ArrayList<String> list;
    SwipeRefreshLayout tcLayout;
    Gson gson;
    private List<Data> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        queue = Volley.newRequestQueue(MainActivity.this);
        tcLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        swipe();

        tcLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe();
            }
        });
    }

    public void swipe() {
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tcUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseFinal) {
//                        final ArrayList<TCList> numbers = new ArrayList<TCList>();
                        Log.i("tag", responseFinal);
                        List<Data> posts = new ArrayList<Data>();
                        posts = Arrays.asList(gson.fromJson(responseFinal, Data[].class));
                        Log.i("PostActivity", posts.size() + " posts loaded.");
                        for (Data post : posts) {
                            Log.i("PostActivity", post.point);
                            list.add(post.point);
                        }
                        tcAdapter = new TCAdapter(MainActivity.this, list);
                        listview = (ListView) findViewById(R.id.tc_list);
                        listview.setAdapter(tcAdapter);
                        //handlePostsList(posts);
//                        JSONArray wholeData = null;
//                        try {
//                            wholeData = new JSONArray(responseFinal);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        for (int i = 0; i < wholeData.length(); i++) {
//                            JSONObject dataPoint = null;
//                            try {
//                                dataPoint = (JSONObject) wholeData.get(i);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                id = (String) dataPoint.get("point");
//                                String k = Integer.toString(i + 1) + ".  ";
//                                id = k + id;
//                                list.add(id);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            //Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
//                        }
//                        listview = (ListView) findViewById(R.id.tc_list);
//                        tcAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, list);
//                        listview.setAdapter(tcAdapter);
                        //Toast.makeText(MainActivity.this, responseFinal, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                        response.setText("That didn't work!");
                Log.e("tag", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        tcLayout.setRefreshing(false);
    }
}
