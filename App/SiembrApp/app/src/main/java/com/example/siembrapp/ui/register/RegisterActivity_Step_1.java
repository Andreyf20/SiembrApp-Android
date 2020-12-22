package com.example.siembrapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.siembrapp.R;


public class RegisterActivity_Step_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__step_1);

        final ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        final Button siguienteBtn = findViewById(R.id.continueStep2Btn);

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Not implemented",Toast.LENGTH_SHORT).show();
            }
        });

        siguienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerStep2Intent = new Intent(getApplicationContext(), RegisterActivity_Step_2.class);
                startActivity(registerStep2Intent);
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}