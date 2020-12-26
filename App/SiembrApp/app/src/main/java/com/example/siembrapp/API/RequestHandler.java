package com.example.siembrapp.API;

import android.content.Context;
import android.widget.Toast;

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
    public static final int GETUSERPLANTAS = 4;
    public static final int LISTVIVEROS = 5;
    public static final int GETTELEFONOSVIVERO = 6;
    public static final int GETHORARIOSVIVERO = 7;


    //JSONObject, MODE.LOGIN
    public static class APIRequester{

        private static final String APIURL = "http://ipconfig:5000/api/";

        public static void request(JSONObject params,Context ctx,int mode, VolleyCallBack callback){

            switch(mode){

                case LOGIN:

                    login(params,ctx,callback);
                    break;

                case GETUSERINFO:

                    getUserInfo(params,ctx,callback);
                    break;

                case GETUSERID:

                    getUserID(params,ctx,callback);
                    break;
                case GETUSERPLANTAS:

                    getPlantas(params,ctx,callback);
                    break;

                case LISTVIVEROS:

                    getViverosList(ctx,callback);
                    break;

                case GETTELEFONOSVIVERO:

                    getTelefonosDeVivero(params,ctx,callback);
                    break;

                case GETHORARIOSVIVERO:

                    getHorariosDeVivero(params,ctx,callback);
                    break;

                default:

                    Toast.makeText(ctx, "No existe esa funcion", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private static void getTelefonosDeVivero(JSONObject bodyparams, Context ctx,final VolleyCallBack callback) {
            //Request URL
            String url= APIURL + "getTelefonosVivero";

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

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,bodyparams,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }

        private static void getHorariosDeVivero(JSONObject bodyparams, Context ctx,final VolleyCallBack callback) {
            //Request URL
            String url= APIURL + "getHorariosVivero";

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

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,bodyparams,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }

        private static void getViverosList(Context ctx,final VolleyCallBack callback) {
            //Request URL
            String url = APIURL + "listViveros";

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

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }

        private static void getPlantas(JSONObject bodyParams, Context ctx,final VolleyCallBack callback) {
            //Request url
            String url = APIURL + "getPlantasDeUsuario";

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

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,bodyParams,responseListener,errorListener);
            RequestQueueSingleton.getInstance(ctx).getRequestQueue().add(request);
        }
    }
}