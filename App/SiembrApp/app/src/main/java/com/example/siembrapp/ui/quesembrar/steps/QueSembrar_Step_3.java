package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.siembrapp.R;

public class QueSembrar_Step_3 extends AppCompatActivity {
    protected int progressValue = 0;
    protected String region = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar_tep_3);

        Bundle extras = getIntent().getExtras();
        this.progressValue = extras.getInt("step");
        this.region = extras.getString("region");
    }
}