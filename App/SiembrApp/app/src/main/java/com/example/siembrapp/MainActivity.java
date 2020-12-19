package com.example.siembrapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.LoggedInUser;
import com.example.siembrapp.data.model.RequestResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        //Back button to toolbar
        Toolbar mainActivityToolbar = findViewById(R.id.mainactivitytoolbar);
        setSupportActionBar(mainActivityToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mainActivityToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent loginIntent = getIntent();
        String correo = loginIntent.getStringExtra("correo");
        Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();




        //getPlantasDeUsuario(LoggedInUser.LoggedUser.getLoggedUser().getUuid());

    }

    private void getAndSetInfoUsuario(String correo){
        //Encapsulamos el correo en un jsonobject
        JSONObject params = new JSONObject();
        try {
            params.put("correo",correo);

            //Cargamos datos del usuario loggeado
            RequestHandler.APIRequester.request(params,getApplicationContext(), RequestHandler.GETUSERINFO, new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    try {
                        JSONObject object = RequestResponse.consumeObject().getJSONObject("info");
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

    private void getPlantasDelUsuario(){
        boolean xd = God.getPlantasDeUsuario();
    }


}