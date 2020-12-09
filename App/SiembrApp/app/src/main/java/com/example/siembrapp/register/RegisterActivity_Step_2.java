package com.example.siembrapp.register;

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
                goToStep3();
            }
        });

        campannaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3();
            }
        });

        otroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStep3();
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void goToStep3(){
        Intent registerStep3Intent = new Intent(getApplicationContext(), RegisterActivity_Step_3.class);
        startActivity(registerStep3Intent);
    }
}