package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.siembrapp.R;

public class QueSembrar_Step_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar__step_1);

        ImageButton backBTN = findViewById(R.id.atrasBtn2);

        Button central = findViewById(R.id.region_central);
        Button chorotega = findViewById(R.id.region_chorotega);
        Button pacificoCentral = findViewById(R.id.region_pacifico_central);
        Button brunca = findViewById(R.id.region_brunca);
        Button huetarNorte = findViewById(R.id.region_huetar_norte);
        Button huetarAtlantica = findViewById(R.id.region_huetar_atlantica);
        String region = "";

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Central");
            }
        });

        chorotega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Chorotega");
            }
        });

        pacificoCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Pacífico Central");
            }
        });

        brunca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Brunca");
            }
        });

        huetarNorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Huetar Norte");
            }
        });

        huetarAtlantica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step("Huetar Atlántica");
            }
        });
    }

    private void next_step(String region){
        Intent queSembrarStep2 = new Intent(getApplicationContext(), QueSembrar_Step_2.class);
        queSembrarStep2.putExtra("region", region);
        startActivity(queSembrarStep2);
        finish();
    }
}