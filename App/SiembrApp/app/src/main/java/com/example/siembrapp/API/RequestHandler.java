package com.example.siembrapp.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class RequestHandler {

    /***
     * Static class for doing requests
     */
    public static class Requester {

        static final String APIURL = "http://192.168.50.37:5000/";

        public static void login(final String correo, final String contrasenna, RequestQueue requestQueue, final VolleyCallBack callBack) {
            String url = APIURL + "api/login";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.d("onResponse",Boolean.toString(response.getString("ok").equals("1")));
                                if (!(response.getString("ok").equals("1"))){
                                    callBack.onFailure();
                                    return;
                                }
                                callBack.onSuccess();
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callBack.onFailure();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            callBack.onFailure();
                        }
                    }){
                @Override
                public byte[] getBody() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("correo",correo);
                        jsonObject.put("contrasenna",contrasenna);
                        return jsonObject.toString().getBytes();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            requestQueue.add(jsonObjectRequest);
        }

        private static void updateLoggedInUserInfo(String correo){

            

        }

    }

    /**
     * Singleton class to get the instance of RequestQueue
     */
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
