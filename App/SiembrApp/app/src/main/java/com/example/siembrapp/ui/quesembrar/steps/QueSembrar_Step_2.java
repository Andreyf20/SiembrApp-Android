package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siembrapp.R;

public class QueSembrar_Step_2 extends AppCompatActivity {
    protected int progressValue = 1;
    protected String region = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar__step_2);

        ImageButton backBTN = findViewById(R.id.atrasBtn3);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imgView = findViewById(R.id.que_sembrar_imageView);
        SeekBar seekBar = findViewById(R.id.que_sembrar_seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // REF: https://abhiandroid.com/ui/seekbar
            int progressChangedValue = 1;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                changeProgressValue(progressChangedValue);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(QueSembrar_Step_2.this, "Seek bar progress is :" + progressChangedValue,
                //        Toast.LENGTH_SHORT).show();
            }
        });

        TextView title = findViewById(R.id.que_sembrar_title_textView);
        Bundle extras = getIntent().getExtras();
        this.region = extras.getString("region");
        title.setText(region);

        Button nextStep = findViewById(R.id.que_sembrar_next_step2);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
    }

    private void changeProgressValue(int value){
        this.progressValue = value;
    }

    private void nextStep(){
        Intent queSembrarStep3 = new Intent(getApplicationContext(), QueSembrar_Step_3.class);
        queSembrarStep3.putExtra("region", this.region);
        queSembrarStep3.putExtra("step", this.progressValue);
        startActivity(queSembrarStep3);
        finish();
    }
}