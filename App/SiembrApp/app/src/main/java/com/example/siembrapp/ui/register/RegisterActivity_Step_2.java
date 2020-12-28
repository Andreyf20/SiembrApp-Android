package com.example.siembrapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.siembrapp.R;


public class RegisterActivity_Step_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__step_2);

        final ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        final Button trabajoBtn = findViewById(R.id.trabajoChoiceBtn);
        final Button campannaBtn = findViewById(R.id.campannaChoiceBtn);
        final Button otroBtn = findViewById(R.id.otroChoiceBtn);

        trabajoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3("Trabajo");
            }
        });

        campannaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3("Campaña de reforestación");
            }
        });

        otroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3("Otro");
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void goToStep3(String razon){
        Intent registerStep3Intent = new Intent(getApplicationContext(), RegisterActivity_Step_3.class);

        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        String apellidos = extras.getString("apellidos");
        String email = extras.getString("email");
        String password = extras.getString("password");
        registerStep3Intent.putExtra("nombre", nombre);
        registerStep3Intent.putExtra("apellidos", apellidos);
        registerStep3Intent.putExtra("email", email);
        registerStep3Intent.putExtra("password", password);
        registerStep3Intent.putExtra("razon", razon);

        startActivity(registerStep3Intent);
        finish();
    }
}