package com.example.siembrapp.ui.viveros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siembrapp.R;
import com.example.siembrapp.data.model.Vivero;

public class DetallesViveroActivity extends AppCompatActivity {

    private TextView nombreViveroTV;

    private EditText direccionET;

    private EditText telefonosET;

    private EditText horariosET;

    private ImageButton back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_vivero);

        back_btn = findViewById(R.id.detallesViveroBackBtn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nombreViveroTV = findViewById(R.id.nombreViveroTV);

        direccionET = findViewById(R.id.direccionET);

        telefonosET = findViewById(R.id.telefonosET);

        horariosET = findViewById(R.id.horariosET);

        //Recibimos el intent
        Intent intent = getIntent();

        Vivero vivero = (Vivero) intent.getSerializableExtra("vivero");

        //Extraemos los datos y los asignamos a los componentes graficos
        nombreViveroTV.setText(vivero.getNombre());
        direccionET.setText(vivero.getDireccion());
        telefonosET.setText(vivero.getTelefonos());
        horariosET.setText(vivero.getHorarios());
    }

}