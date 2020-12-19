package com.example.siembrapp.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.siembrapp.Interfaces.VolleyCallBack;


import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {

    /**
     * Singleton class to get the instance of RequestQueue
     * Ref: https://developer.android.com/training/volley/requestqueue
     */

    public static class RequestQueueSingleton{

        private static RequestQueueSingleton instance;
        private RequestQueue requestQueue;
        private static Context ctx;

        private RequestQueueSingleton(Context context){
            ctx = context;
            requestQueue = getRequestQueue();
        }

        private RequestQueue getRequestQueue() {
            if (requestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            }
            return requestQueue;
        }

        public static synchronized RequestQueueSingleton getInstance(Context context) {
            if (instance == null) {
                instance = new RequestQueueSingleton(context);
            }
            return instance;
        }
    }

    // Modos
    public static final int LOGIN = 1;
    public static final int GETUSERINFO = 2;
    public static final int GETUSERID = 3;
    public static final int GETPLANTASDEUSUARIO = 4;

    //JSONObject, MODE.LOGIN
    public static class APIRequester{

        private static final String APIURL = "http://192.168.50.37:5000/api/";

        public static void request(JSONObject object,Context ctx,int mode, VolleyCallBack callback){

            switch(mode){

                case LOGIN:

                    login(object,ctx,callback);
                    break;

                case GETUSERINFO:

                    getUserInfo(object,ctx,callback);
                    break;

                /*case GETPLANTAS:

                    getPlantas(object,ctx,callback);
                    break;*/

                default:

            }
        }

        private static void getUserInfo(JSONObject bodyParams, Context ctx,final VolleyCallBack callback) {

            //Request url
            String url = APIURL + "getUserInfo";

            //Instanciar Listener para el JsonObjectRequest
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        JSONObject info = response.getJSONObject("info");
                        callback.onSuccess(info);

                    } catch (JSONException exception) {
                        exception.printStackTrace();
                        callback.onFailure();
                    }
                }
            };

            //Instanciar error listener
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getClass().equals(NoConnectionError.class)) {
                        callback.noConnection();
                        return;
                    }
                    if (error.getClass().equals(TimeoutError.class)) {
                        callback.timedOut();
                        return;
                    }
                    callback.onFailure();
                }
            };
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,bodyParams,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }

        private static void login(JSONObject bodyParams, Context ctx, final VolleyCallBack callback){
            //Request url
            String url = APIURL + "login";

            //Instanciar Listener para el JsonObjectRequest
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    callback.onSuccess(response);
                }
            };
            //Instanciar error listener
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getClass().equals(NoConnectionError.class)) {
                        callback.noConnection();
                        return;
                    }
                    if (error.getClass().equals(TimeoutError.class)) {
                        callback.timedOut();
                        return;
                    }
                    callback.onFailure();
                }
            };
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,bodyParams,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }

        private static void getUserID(JSONObject bodyParams,Context ctx,final VolleyCallBack callback){
            //Request url
            String url = APIURL +"getid";

            //Instanciar Listener para el JsonObjectRequest
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    callback.onSuccess(response);
                }
            };
            //Instanciar error listener
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getClass().equals(NoConnectionError.class)) {
                        callback.noConnection();
                        return;
                    }
                    if (error.getClass().equals(TimeoutError.class)) {
                        callback.timedOut();
                        return;
                    }
                    callback.onFailure();
                }
            };
        }
    }
}