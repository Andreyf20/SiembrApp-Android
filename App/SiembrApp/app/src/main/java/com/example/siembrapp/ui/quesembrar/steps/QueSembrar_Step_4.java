package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.siembrapp.R;

public class QueSembrar_Step_4 extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar__step_4);

        TextView test = findViewById(R.id.dummy_txt_xd);

        Bundle extras = getIntent().getExtras();
        String region = extras.getString("region");
        int step = extras.getInt("step");
        String light = extras.getString("luz");

        test.setText("Region: "+ region + ", Step: "+step+", Luz: "+light);
    }
}