package com.example.siembrapp.ui.login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.Permissions.Checker.PermChecker;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private final int INTERNET_GPS_NETWORKSTATE_CODE = 1001;
    EditText usernameEditText;
    Button loginButton;
    TextView registerEditText;
    TextView acceptPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RequestHandler.RequestQueueSingleton.getInstance(this.getApplicationContext());

        // Limpiar el objeto porque estamos en login screen
        God.logout(getApplicationContext());

        usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerEditText = findViewById(R.id.registerHyperlink);
        acceptPermissions = findViewById(R.id.accept_permissions);

        usernameEditText.setText("admin@siembrapp.com");
        passwordEditText.setText("admin");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length()==0 || passwordEditText.getText().length() ==0){
                    Toast.makeText(getApplicationContext(), R.string.camposvacios, Toast.LENGTH_SHORT).show();
                }else{

                    //Ref: https://stackoverflow.com/a/50716727
                    Dialog dialog = new Dialog(LoginActivity.this);
                    final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
                    pDialog.setMessage("Ingresando...");
                    pDialog.show();

                    try {

                        JSONObject params = new JSONObject();
                        params.put("correo",usernameEditText.getText().toString());
                        params.put("contrasenna",passwordEditText.getText().toString());

                        RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.LOGIN, new VolleyCallBack() {
                            @Override
                            public void onSuccess(JSONObject object) {

                                try {
                                    //Consumimos el objeto json del RequestResponse
                                    String response = object.getString("ok");
                                    if(response.equals("1")){

                                        setupUser(usernameEditText.getText().toString(),new VolleyCallBack() {

                                            @Override
                                            public void onSuccess(JSONObject object) {

                                                pDialog.hide();

                                                //Ya el usuario esta setteado, podemos pasar de actividad
                                                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                Toast.makeText(getApplicationContext(), "Hola " +God.getLoggedUser().getNombre(), Toast.LENGTH_SHORT).show();
                                                startActivity(mainActivityIntent);
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

                                    }else {
                                        Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException exception) {
                                    exception.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure() {
                                pDialog.hide();
                                Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void noConnection() {
                                pDialog.hide();
                                Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void timedOut() {
                                pDialog.hide();
                                Toast.makeText(getApplicationContext(), R.string.timedouterror, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        acceptPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

        checkPermissions();
        String status = getConnectivityStatusString();
        if(status.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Se necesita internet para utilizar la aplicación", Toast.LENGTH_LONG).show();
            loginButton.setEnabled(false);
            registerEditText.setEnabled(false);
        }
    }

    private String getConnectivityStatusString(){
        /*REF: https://www.tutorialspoint.com/how-to-check-internet-connection-in-android*/
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
            }
            return status;
        } else {
            status = "No internet is available";
            return status;
        }
    }

    private void checkPermissions(){
        PermChecker checker = PermChecker.getSingletonInstance();
        boolean isInternetPerm = true;
        boolean isGpsPerm = true;
        /*boolean isNetworkstatePerm = true;*/
        try {
            isInternetPerm = checker.isPermissionActive("internet", this);
            isGpsPerm = checker.isPermissionActive("gps", this);
            /*isNetworkstatePerm = checker.isPermissionActive("networkstate", this);*/
        }catch (Exception e) {
            e.printStackTrace();
        }

        if(!isInternetPerm || !isGpsPerm){
            String[] permissions = {Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions);
        }
    }

    private void requestPermissions(final String[] permissions) {
        new AlertDialog.Builder(this)
                .setTitle("Se necesitan permisos!")
                .setMessage("Esta aplicación requiere de múltiples permisos para funcionar, por favor aceptarlos todos para evitar inconvenientes")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(LoginActivity.this,
                                permissions, INTERNET_GPS_NETWORKSTATE_CODE);
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Se deben aceptar los permisos para continuar", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(false);
                        registerEditText.setEnabled(false);
                        acceptPermissions.setVisibility(View.VISIBLE);
                    }
                })
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == INTERNET_GPS_NETWORKSTATE_CODE)  {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos habilitados", Toast.LENGTH_SHORT).show();
                loginButton.setEnabled(true);
                registerEditText.setEnabled(true);
                acceptPermissions.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, "La aplicación se desabilitará debido a falta de permisos", Toast.LENGTH_SHORT).show();
                loginButton.setEnabled(false);
                registerEditText.setEnabled(false);
                acceptPermissions.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Conseguir la informacion del usuario que ingreso y sus plantas
     */
    private void setupUser(String correo,final VolleyCallBack callback) {

        //Extraer el correo que se paso desde el loginactivity y pedirle a la clase God
        //consultar la informacion del usuario para guardarla en SharedPreferences
        God.setupUser(
                correo,getApplicationContext(), new VolleyCallBack() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        //User ya existe, seguimos con sus plantas
                        Log.d("XD",God.getLoggedUser().getUuid());
                        getPlantasDelUsuario(new VolleyCallBack() {
                            @Override
                            public void onSuccess(JSONObject object) {

                                //Llamar funcion que retorna plantas
                                callback.onSuccess(null);

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
                }
        );
    }

    private void getPlantasDelUsuario(final VolleyCallBack callback){

        God.getPlantasDeUsuario(getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(JSONObject object) {
                Log.d("XD",God.getLoggedUser().getNombre() + " tiene "+God.getLoggedUser().getPlantas().size());
                callback.onSuccess(null);
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