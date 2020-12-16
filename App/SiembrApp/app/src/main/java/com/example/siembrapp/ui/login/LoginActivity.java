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

public class LoginActivity extends AppCompatActivity {

    private RequestQueue rq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rq = RequestHandler.RequestQueueInstance.getRequestQueue(getApplicationContext());

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
                RequestHandler.Requester.login(usernameEditText.getText().toString(), passwordEditText.getText().toString(), rq, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "¡Bienvenido!", Toast.LENGTH_SHORT).show();


                        Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(homeIntent);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getApplicationContext(), "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });
    }
}