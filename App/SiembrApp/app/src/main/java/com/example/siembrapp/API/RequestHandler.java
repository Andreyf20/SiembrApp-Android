package com.example.siembrapp.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestHandler {

    public static class Requester{

        static GsonBuilder builder = new GsonBuilder();
        static final Gson gson = builder.setPrettyPrinting().create();

        public static void request(String url, RequestQueue requestQueue){

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        //JSONArray array = response.toJSONArray(response.names());
                        String json = gson.toJson(response);
                        Log.d("XD", json);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("XD",error.toString());
                    }
                });
            requestQueue.add(jsonObjectRequest);
        }
    }

    public static class RequestQueueInstance{

        private static RequestQueue requestQueue;

        public static RequestQueue getRequestQueue(Context ctx){

            if (requestQueue == null){

                // Instantiate the cache
                Cache cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024); // 1MB cap

                // Set up the network to use HttpURLConnection as the HTTP client.
                Network network = new BasicNetwork(new HurlStack());

                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();
            }
            return requestQueue;
        }
    }
}
