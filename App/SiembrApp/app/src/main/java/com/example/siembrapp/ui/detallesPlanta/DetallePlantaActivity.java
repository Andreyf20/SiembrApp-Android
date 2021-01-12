package com.example.siembrapp.ui.detallesPlanta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.Planta;

public class DetallePlantaActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_planta);
        Intent intent = getIntent();
        Planta plantaFocused = (Planta) intent.getSerializableExtra("planta");
        boolean like_state = intent.getBooleanExtra("like_state", false);
        ImageButton backBtn = findViewById(R.id.detallesPlantaBackBtn);

        ImageView like = findViewById(R.id.like);
        if(like_state)
            like.setImageDrawable(getDrawable(R.drawable.unlike));
        else
            like.setImageDrawable(getDrawable(R.drawable.like));

        // Referencias a componentes graficos
        TextView nombreComunTV, nombreCientificoTV,familiaTV,origenTV,habitoTV,rangoTV,requerimientosLuzTV,fenologiaTV,polinizadorTV,dispersionTV,frutoTV,texturaFrutaTV,florTV,usosTV,paisajesTV;

        nombreComunTV = findViewById(R.id.nombreComunTV);
        nombreCientificoTV = findViewById(R.id.nombreCientificoTV);
        familiaTV = findViewById(R.id.familiaTV);
        origenTV = findViewById(R.id.origenTV);
        habitoTV = findViewById(R.id.habitoTV);
        rangoTV =findViewById(R.id.rangoAltitudinalTV);
        requerimientosLuzTV =findViewById(R.id.requerimientosLuzTV);
        fenologiaTV = findViewById(R.id.fenologiaTV);
        polinizadorTV =findViewById(R.id.agentePolinizadorTV);
        dispersionTV = findViewById(R.id.metododispersionTV);
        frutoTV = findViewById(R.id.frutoTV);
        texturaFrutaTV =findViewById(R.id.texturafrutoTV);
        florTV = findViewById(R.id.florTV);
        usosTV = findViewById(R.id.usosConocidosTV);
        paisajesTV = findViewById(R.id.paisajesRecomendadosTV);

        nombreComunTV.setText(plantaFocused.getNombreComun());
        nombreCientificoTV.setText(plantaFocused.getNombreCientifico());
        familiaTV.setText(plantaFocused.getFamilia());
        origenTV.setText(plantaFocused.getOrigen());
        habitoTV.setText(plantaFocused.getHabito());
        requerimientosLuzTV.setText(plantaFocused.getRequerimientosDeLuz());
        fenologiaTV .setText(plantaFocused.getFenologia());
        polinizadorTV.setText(plantaFocused.getAgentePolinizador());
        dispersionTV.setText(plantaFocused.getMetodoDispersion());
        frutoTV.setText(plantaFocused.getFruto());
        texturaFrutaTV.setText(plantaFocused.getTexturaFruto());
        florTV.setText(plantaFocused.getFlor());

        String rangoAltitudinal = plantaFocused.getMinRangoAltitudinal() +" - "+ plantaFocused.getMaxRangoAltitudinal() + " msnm";
        rangoTV.setText(rangoAltitudinal);

        usosTV.setText(plantaFocused.getUsosConocidosString());

        paisajesTV.setText(plantaFocused.getPaisajesRecomendadosString());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}