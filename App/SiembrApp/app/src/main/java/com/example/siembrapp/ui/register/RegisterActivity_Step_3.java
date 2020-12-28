package com.example.siembrapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.siembrapp.R;

public class RegisterActivity_Step_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__step_3);

        final ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        final Button asadaChoiceBtn = findViewById(R.id.asadaChoiceBtn);
        final Button estadoChoiceBtn = findViewById(R.id.estadoChoiceBtn);
        final Button gobiernoChoiceBtn = findViewById(R.id.gobiernoChoiceBtn);
        final Button ongChoiceBtn = findViewById(R.id.ongChoiceBtn);
        final Button otroChoiceBtn = findViewById(R.id.otroChoiceBtn);

        asadaChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Asada");
            }
        });

        estadoChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Estado");
            }
        });

        gobiernoChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Gobierno local");
            }
        });

        ongChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("ONG");
            }
        });

        otroChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Otro");
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void finishinRegister(String tipoorganizacion){
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        String apellidos = extras.getString("apellidos");
        String email = extras.getString("email");
        String password = extras.getString("password");
        String razon = extras.getString("razon");
/*
        Log.d("RegisterTest", nombre+" "+apellidos+" "+email+" "+password+" "+razon+" "+tipoorganizacion);
*/
        
    }
}