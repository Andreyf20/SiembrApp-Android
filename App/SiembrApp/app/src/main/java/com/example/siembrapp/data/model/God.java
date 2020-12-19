package com.example.siembrapp.data.model;

import android.content.Context;
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
     * Funcion que "settea" le usuario que inicio sesion
     * @param object
     */
    public static boolean setLoggedUser(JSONObject object){

        String uid, nombre,correo,nombretipoOrganizacion,razon;
        try {

            uid = object.getString("uid");
            nombre = object.getString("nombre");
            correo = object.getString("correo");
            nombretipoOrganizacion = object.getString("nombretipoorganizacion");
            razon = object.getString("razon");

            User.UserBuilder userBuilder = new User.UserBuilder().
                    setUUID(uid).
                    setNombre(nombre).
                    setCorreo(correo).
                    setTipoOrganizacion(nombretipoOrganizacion).
                    setRazon(razon);

            LoggedInUser.LoggedUser.setLoggedUser(userBuilder.build());
            return true;
        } catch (JSONException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean getPlantasDeUsuario(){
        User loggedUser = LoggedInUser.LoggedUser.getLoggedUser();
        return true;
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
