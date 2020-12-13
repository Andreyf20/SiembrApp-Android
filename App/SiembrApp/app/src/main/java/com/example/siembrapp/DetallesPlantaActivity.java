package com.example.siembrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.siembrapp.ui.detalleplanta.DetallesPlantaFragment;

public class DetallesPlantaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_planta_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DetallesPlantaFragment.newInstance())
                    .commitNow();
        }
    }
}