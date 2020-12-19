package com.example.siembrapp.data.model;

import android.util.Log;

import org.json.JSONObject;

/**
 * Clase "intermediario" para guardar JSONObjects de requests para su uso
 */
public class RequestResponse {

    private static JSONObject responseObject;
    private static boolean locked = false;
    private static String TAG = "RequestResponse";
    public static void setResponseObject(JSONObject object){

        if(locked){
            Log.d(TAG,"RequestResponse is locked");
        }else{
            responseObject = object;
            locked = true;
            Log.d(TAG,"Setting:\n"+responseObject.toString());
        }
    }

    /**
     * Retorna una copia del JSONObject guardado y borra el objeto almacenado
     * @return Copia del JSONObject
     */
    public static JSONObject consumeObject(){
        JSONObject copy = responseObject;
        locked = false;
        clearResponseObject();
        return copy;
    }

    public static void clearResponseObject(){
        responseObject = null;
    }
}
