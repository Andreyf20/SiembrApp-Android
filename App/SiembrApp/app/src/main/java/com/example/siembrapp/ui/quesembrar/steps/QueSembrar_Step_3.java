package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.siembrapp.R;

public class QueSembrar_Step_3 extends AppCompatActivity {
    protected int progressValue = 0;
    protected String region = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar__step_3);

        Bundle extras = getIntent().getExtras();
        this.progressValue = extras.getInt("step");
        this.region = extras.getString("region");
        TextView title = findViewById(R.id.que_sembrar_light_title_textView);
        title.setText(this.region);

        LinearLayout lightLevel1 = findViewById(R.id.light_level_1);
        LinearLayout lightLevel2 = findViewById(R.id.light_level_2);
        LinearLayout lightLevel3 = findViewById(R.id.light_level_3);
        LinearLayout lightLevel4 = findViewById(R.id.light_level_4);

        lightLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step(getString(R.string.light_level_1));
            }
        });

        lightLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step(getString(R.string.light_level_2_1));
            }
        });

        lightLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step(getString(R.string.light_level_3_1));
            }
        });

        lightLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_step(getString(R.string.light_level_4_1));
            }
        });
    }

    private void next_step(String light_level){
        Intent queSembrarStep4 = new Intent(getApplicationContext(), QueSembrar_Step_4.class);
        queSembrarStep4.putExtra("region", this.region);
        queSembrarStep4.putExtra("step", this.progressValue);
        queSembrarStep4.putExtra("luz", light_level);
        startActivity(queSembrarStep4);
        finish();
    }
}