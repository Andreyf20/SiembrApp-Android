package com.example.siembrapp.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;

import org.json.JSONException;
import org.json.JSONObject;


/*
Clase controlador
 */
public class God {

    public static final int OK = 0;
    public static final int FAILURE = 1;
    public static final int NOCONN = 2;
    public static final int TIMEOUT = 3;

    /**
     * Buscar informacion del usuario, get de su ID y buscar sus plantas
     * @param correo
     * @param ctx
     */
    public static void setupUser(String correo, final Context ctx){
        //Encapsulamos el correo en un jsonobject
        JSONObject params = new JSONObject();
        try {
            params.put("correo",correo);

            //Cargamos datos del usuario loggeado
            RequestHandler.APIRequester.request(params,ctx, RequestHandler.GETUSERINFO, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject object) {
                    //Pasar el resultado
                    setLoggedUser(object,ctx);
                }

                @Override
                public void onFailure() {
                    Toast.makeText(ctx, R.string.loginError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void noConnection() {
                    Toast.makeText(ctx, R.string.connectionError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void timedOut() {
                    Toast.makeText(ctx, R.string.timedouterror, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Funcion que "settea" le usuario que inicio sesion
     * @param object
     */
    public static void setLoggedUser(JSONObject object, Context ctx){
        String jsonAsString = object.toString();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("info",jsonAsString);
        editor.apply();

    }

    /**
     * Retorna el objeto User con la informacion provista del JSONObject
     * @param object
     * @return
     */
    public static User getUserInfo(JSONObject object){
        String uid, nombre,correo,nombretipoOrganizacion,razon;
        try {

            uid = object.getString("uid");
            nombre = object.getString("nombre");
            correo = object.getString("correo");
            nombretipoOrganizacion = object.getString("nombretipoorganizacion");
            razon = object.getString("razon");

            User.UserBuilder userBuilder = new User.UserBuilder();
            userBuilder.setUUID(uid).setNombre(nombre).setCorreo(correo).setTipoOrganizacion(nombretipoOrganizacion).setRazon(razon);

            return userBuilder.build();

        } catch (JSONException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void getPlantasDeUsuario(final Context ctx){

        String uuid = Session.getLoggedUser().getUuid();

        JSONObject params = new JSONObject();
        try {
            params.put("uuid",uuid);

            RequestHandler.APIRequester.request(params, ctx, RequestHandler.GETUSERID, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject object) {

                    try {
                        Log.d("XD",object.getString("id"));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
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

        } catch (JSONException exception) {
            exception.printStackTrace();
        }

    }

    public static JSONObject buildResponse(int statuscode,String msg){

        try {
            return new JSONObject()
                    .put("statuscode",statuscode)
                    .put("msg",msg);
        } catch (JSONException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
