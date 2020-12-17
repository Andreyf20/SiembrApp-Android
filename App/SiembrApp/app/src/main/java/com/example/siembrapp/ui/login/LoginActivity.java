package com.example.siembrapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.LoggedInUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final RequestQueue requestQueue = RequestHandler.RequestQueueInstance.getRequestQueue(getApplicationContext());

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
                RequestHandler.Requester.login(usernameEditText.getText().toString(), passwordEditText.getText().toString(),requestQueue, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {

                        // Set logged in user
                        RequestHandler.Requester.updateLoggedInUserInfo(usernameEditText.getText().toString(),requestQueue,new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(), "Bienvenido "+LoggedInUser.LoggedUser.getLoggedUser().getNombre(), Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(homeIntent);
                            }

                            @Override
                            public void onFailure() {
                                //Ignore
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
                        Toast.makeText(getApplicationContext(),R.string.timedouterror, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });
    }
}