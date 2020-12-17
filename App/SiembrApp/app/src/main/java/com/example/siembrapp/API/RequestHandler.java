package com.example.siembrapp.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.data.model.LoggedInUser;
import com.example.siembrapp.data.model.Planta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RequestHandler {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
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
                                Log.d("onResponse", Boolean.toString(response.getString("ok").equals("1")));
                                if (!(response.getString("ok").equals("1"))) {
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

                            if (error.getClass().equals(NoConnectionError.class)) {
                                callBack.noConnection();
                                return;
                            }
                            if (error.getClass().equals(TimeoutError.class)) {
                                callBack.timedOut();
                                return;
                            }
                            callBack.onFailure();
                        }
                    }) {
                @Override
                public byte[] getBody() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("correo", correo);
                        jsonObject.put("contrasenna", contrasenna);
                        return jsonObject.toString().getBytes();
                    } catch (JSONException e) {
                        Log.e("login.getBody()", "JSONException");
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

        public static void updateLoggedInUserInfo(final String correo, final RequestQueue requestQueue, final VolleyCallBack callBack) {
            String url = APIURL + "api/getUserInfo";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //On response actions
                            try {
                                String correo = response.getString("correo");
                                String uuid = response.getString("uuid");
                                String nombre = response.getString("nombre");

                                if (!correo.equals("null") && !uuid.equals("null") && !nombre.equals("null")) {
                                    //Set del logged in user
                                    LoggedInUser.LoggedInUserBuilder builder = new LoggedInUser.LoggedInUserBuilder();
                                    builder.setCorreo(correo).setUuid(uuid).setNombre(nombre);

                                    LoggedInUser.LoggedUser.setLoggedUser(builder.build());

                                    getPlantasUsuario(1, requestQueue, new VolleyCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            callBack.onSuccess();
                                        }

                                        @Override
                                        public void onFailure() {

                                        }

                                        @Override
                                        public void noConnection() {

                                        }

                                        @Override
                                        public void timedOut() {

                                        }
                                    });

                                }
                                callBack.onFailure();

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
                    }) {
                @Override
                public byte[] getBody() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //parameter puts
                        jsonObject.put("correo", correo);
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

        public static void getPlantasUsuario(final int id, RequestQueue requestQueue, final VolleyCallBack callBack) {
            String url = APIURL + "api/getPlantasDeUsuario";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //On response actions

                            ArrayList<Planta> plantas = new ArrayList<>();
                            try {

                                JSONArray array =  response.getJSONArray("plantas");
                                for (int i=0; i < array.length(); i++){

                                    JSONObject element = array.getJSONObject(i);

                                    Planta.PlantaBuilder plantaBuilder = new Planta.PlantaBuilder();

                                    //Parse usos conocidos
                                    String usosConocidosStr = element.getString("usosconocidos");
                                    String[] usos = usosConocidosStr.split(", ");

                                    //Parse paisajes recomendados
                                    String paisajesRecomendadosStr = element.getString("paisajerecomendado");
                                    String[] paisajesRecomendados = paisajesRecomendadosStr.split(", ");

                                    plantaBuilder.setRequerimientosDeLuz(element.getString("requerimientosdeluz"))
                                            .setFamilia(element.getString("familia"))
                                            .setFenologia(element.getString("fenologia"))
                                            .setAgentePolinizador(element.getString("polinizador"))
                                            .setMetodoDispersion(element.getString("metododispersion"))
                                            .setNombreComun(element.getString("nombrecomun"))
                                            .setNombreCientifico(element.getString("nombrecientifico"))
                                            .setOrigen(element.getString("origen"))
                                            .setMinRangoAltitudinal(Double.parseDouble(element.getString("minrangoaltitudinal")))
                                            .setMaxRangoAltitudinal(Double.parseDouble(element.getString("maxrangoaltitudinal")))
                                            .setMetros(Double.parseDouble(element.getString("metros")))
                                            .setHabito(element.getString("requerimientosdeluz"))
                                            .setFruto(element.getString("frutos"))
                                            .setTexturaFruto(element.getString("texturafruto"))
                                            .setFlor(element.getString("flor"))
                                            .setPaisajeRecomendado(
                                                    new ArrayList<String>(Arrays.asList(paisajesRecomendados))
                                            )
                                            .setUsosConocidos(
                                                    new ArrayList<>(Arrays.asList(usos))
                                            );

                                    plantas.add(plantaBuilder.build());
                                }
                                LoggedInUser.LoggedUser.getLoggedUser().setPlantas(plantas);
                                Log.d("XD",array.toString());
                                callBack.onSuccess();
                                return;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            callBack.onFailure();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            callBack.onFailure();
                        }
                    }) {
                @Override
                public byte[] getBody() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        //parameter puts
                        jsonObject.put("id", id);
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
