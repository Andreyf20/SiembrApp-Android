package com.example.siembrapp.data.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;


/*
Clase controlador
 */
public class God {

    //Log TAG
    static final String TAG = "God";

    // Usuario loggeado
    private static User loggedUser;

    public static User getLoggedUser(){
        return loggedUser;
    }

    public static void setLoggedUser(User newUser){
        loggedUser = newUser;
    }

    public static void logout(Context ctx){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    ////////////////////////////////////////////////////

    //Viveros
    private static ArrayList<Vivero> viveros;

    public static ArrayList<Vivero> getViveros(){
        return viveros;
    }

    public static void setViveros(ArrayList<Vivero> viverosList){
        viveros = viverosList;
    }

    public static void updateViveros(){
        //TODO call getViveros request
    }
    ////////////////////////////////////////////////////

    /**
     * 1.
     * Hace la consulta, recibe el response y llama al metodo para escribir en SharedPreferences
     */
    public static void setupUser(String correo, final Context ctx,final VolleyCallBack callBack){
        //Encapsulamos el correo en un jsonobject
        JSONObject params = new JSONObject();
        try {
            params.put("correo",correo);

            //Cargamos datos del usuario loggeado
            RequestHandler.APIRequester.request(params,ctx, RequestHandler.GETUSERINFO, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject object) {
                    //Pasar el resultado

                    writeUserInfoToSharedPreferences(object,ctx);

                    loadUser(ctx);

                    callBack.onSuccess(null);
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
     * 2.
     * Escribir la informacion del usuario en SharedPreferences
     */
    @SuppressLint("ApplySharedPref")
    public static void writeUserInfoToSharedPreferences(JSONObject object, Context ctx){

        String jsonAsString = object.toString();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("userinfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("info",jsonAsString);
        Log.d("XD","Preferences saved: "+ editor.commit());

    }

    /**
     * 3.
     * Leer de los datos de SharedPreferences para instanciar el objeto User
     */
    private static void loadUser(Context ctx) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences("userinfo", MODE_PRIVATE);
        String infoAsJson = sharedPreferences.getString("info","{}");// Retornar el string del json, si no existe, retorna "{}" por default

        try {
            //Tenemos el json de la info del usuario como string y la pasamos a JSONObject
            JSONObject array = new JSONObject(infoAsJson);

            //Se crea el builder del usuario y extraemos cada dato
            User.UserBuilder userBuilder = new User.UserBuilder();
            userBuilder
                    .setNombre(array.getString("nombre"))
                    .setCorreo(array.getString("correo"))
                    .setRazon(array.getString("razon"))
                    .setUUID(array.getString("uid"))
                    .setTipoOrganizacion(array.getString("nombretipoorganizacion"));

            //Lo asignamos al atributo loggedUser de la clase God
            God.setLoggedUser(userBuilder.build());

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Primer paso para hacer fetch de las plantas del usuario usando su UUID
     */
    public static void getPlantasDeUsuario(final Context ctx, final VolleyCallBack callBack){
        User user = God.getLoggedUser();
        String uuid = user.getUuid();

        JSONObject params = new JSONObject();
        try {
            params.put("uuid",uuid);

            RequestHandler.APIRequester.request(params, ctx, RequestHandler.GETUSERID, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject objectwithid) {

                    //Utilizando el UUID recibimos el id del usuario y lo pasamos directo al metodo para cargar las plantas
                    //del usuario
                    fetchUserPlantas(objectwithid,ctx, new VolleyCallBack() {
                        @Override
                        public void onSuccess(JSONObject object) {

                            callBack.onSuccess(null);

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

    /**
     *  Consultar plantas del usuario usando su id
     */
    private static void fetchUserPlantas(JSONObject userid, Context ctx, final VolleyCallBack callBack) {

        RequestHandler.APIRequester.request(userid, ctx, RequestHandler.GETUSERPLANTAS, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject plantasDelUsuario) {

                //Recibimos las plantas y pasamos el JSONArray a ArrayList<Plantas>)
                try {
                   JSONArray arrayPlantas = plantasDelUsuario.getJSONArray("plantas");

                   ArrayList<Planta> plantas = new ArrayList<>();

                   for(int i =0; i < arrayPlantas.length();i++){

                       JSONObject element = arrayPlantas.getJSONObject(i);

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
                                       new ArrayList<>(Arrays.asList(paisajesRecomendados))
                               )
                               .setUsosConocidos(
                                       new ArrayList<>(Arrays.asList(usos))
                               );

                       plantas.add(plantaBuilder.build());
                   }

                   loadUserPlantas(plantas);
                   callBack.onSuccess(null);

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
    }

    /**
     * Asigna las plantas al usuario
     */
    private static void loadUserPlantas(ArrayList<Planta> plantas){
        God.getLoggedUser().setPlantas(plantas);
    }

    /**
     * Pedirle al API la lista de viveros
     */
    public static void getListaViveros(final Context ctx,final VolleyCallBack callBack){

        RequestHandler.APIRequester.request(null, ctx, RequestHandler.LISTVIVEROS, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {
                if(viveros == null){
                    loadViveros(object);
                }
                callBack.onSuccess(null);
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

    /**
     * Tomar el JSON array, parsearlo y asignarlo al ArrayList<String> viveros
     */
    private static void loadViveros(JSONObject viverosJSON){

        try {
            JSONArray viverosJSONArray = viverosJSON.getJSONArray("viveros");

            ArrayList<Vivero> viveros = new ArrayList<>();

            for (int i = 0; i < viverosJSONArray.length(); i++) {

                JSONObject viveroObject = viverosJSONArray.getJSONObject(i);

                Vivero.ViveroBuilder builder = new Vivero.ViveroBuilder();
                builder.setNombre(viveroObject.getString("nombre"))
                        .setDireccion(viveroObject.getString("direccion"));

                viveros.add(builder.build());
            }
            God.setViveros(viveros);

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Conseguir los numeros de telefono del vivero usando su nombre
     */
    public static void getTelefonos(final Context ctx, final String nombre, final VolleyCallBack callBack){

        //Encapsular el nombre en JSONObject
        JSONObject nombreVivero = new JSONObject();
        try {
            nombreVivero.put("nombreVivero",nombre);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        RequestHandler.APIRequester.request(nombreVivero, ctx, RequestHandler.GETTELEFONOSVIVERO, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.d(TAG,response.toString());
                callBack.onSuccess(response);
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

    /**
     * Conseguir los horarios del vivero usando su nombre
     */
    public static void getHorarios(final Context ctx, final String nombre, final VolleyCallBack callBack){
        JSONObject nombreVivero = new JSONObject();
        try {
            nombreVivero.put("nombreVivero",nombre);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        RequestHandler.APIRequester.request(nombreVivero, ctx, RequestHandler.GETHORARIOSVIVERO, new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.d(TAG,response.toString());
                callBack.onSuccess(response);
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
}
