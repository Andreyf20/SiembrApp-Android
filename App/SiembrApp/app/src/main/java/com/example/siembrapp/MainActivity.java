package com.example.siembrapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

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

        setupUser();
    }

    private void setupUser() {
        //Extraer el correo que se paso desde el loginactivity y pedirle a la clase God
        //consultar la informacion del usuario para guardarla en SharedPreferences
        God.setupUser(
                getIntent().getStringExtra("correo"),getApplicationContext()
        );

        SharedPreferences preferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        String json = preferences.getString("info","{}");
        Gson gson = new Gson();
        User user = gson.fromJson(json,User.class);
        Toast.makeText(getApplicationContext(), "Hola " +user.getNombre(), Toast.LENGTH_SHORT).show();

    }

}