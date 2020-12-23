package com.example.siembrapp.ui.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RequestHandler.RequestQueueSingleton.getInstance(this.getApplicationContext());

        // Limpiar el objeto porque estamos en login screen
        God.logout(getApplicationContext());

        usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView registerEditText = findViewById(R.id.registerHyperlink);

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

                                            God.getListaViveros(getApplicationContext(), new VolleyCallBack() {
                                                @Override
                                                public void onSuccess(JSONObject object) {

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