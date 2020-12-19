package com.example.siembrapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.LoggedInUser;
import com.example.siembrapp.data.model.RequestResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RequestHandler.RequestQueueSingleton.getInstance(this.getApplicationContext());

        // Limpiar el objeto porque estamos en login screen
        LoggedInUser.LoggedUser.setLoggedUser(null);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final TextView registerEditText = findViewById(R.id.registerHyperlink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (usernameEditText.getText().length()==0 || passwordEditText.getText().length() ==0){
                Toast.makeText(getApplicationContext(), R.string.camposvacios, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Verificando...",Toast.LENGTH_SHORT);
                try {
                    JSONObject params = new JSONObject();
                    params.put("correo",usernameEditText.getText().toString());
                    params.put("contrasenna",passwordEditText.getText().toString());

                   RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.LOGIN, new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            try {
                                //Consumimos el objeto json del RequestResponse
                                String response = RequestResponse.consumeObject().getString("ok");
                                Log.d("xd",response);
                                if(response.equals("1")){

                                    //Instanciamos el intent a la actividad de home y le pasamos la variable del correo
                                    Intent mainActivityIntent = new Intent(getApplicationContext(),MainActivity.class);
                                    mainActivityIntent.putExtra("correo",usernameEditText.getText().toString());
                                    startActivity(mainActivityIntent);

                                }else {
                                    Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void noConnection() {
                            Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void timedOut() {
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
}