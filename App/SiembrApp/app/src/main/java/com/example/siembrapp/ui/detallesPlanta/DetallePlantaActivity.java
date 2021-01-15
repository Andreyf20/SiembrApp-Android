package com.example.siembrapp.ui.detallesPlanta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Planta;
import com.example.siembrapp.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class DetallePlantaActivity extends AppCompatActivity {

    boolean like_state;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_planta);
        Intent intent = getIntent();
        final Planta plantaFocused = (Planta) intent.getSerializableExtra("planta");
        like_state = intent.getBooleanExtra("like_state", false);
        ImageButton backBtn = findViewById(R.id.detallesPlantaBackBtn);

        final ImageView like = findViewById(R.id.like);
        if(like_state)
            like.setImageDrawable(getDrawable(R.drawable.unlike));
        else
            like.setImageDrawable(getDrawable(R.drawable.like));

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ref: https://stackoverflow.com/a/50716727
                Dialog dialog = new Dialog(DetallePlantaActivity.this);
                final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
                pDialog.setMessage("Actualizando información...");
                pDialog.show();

                JSONObject params = new JSONObject();
                try {
                    params.put("userId", God.getLoggedUserId());
                    params.put("nombrePlanta", plantaFocused.getNombreCientifico());

                    RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.PLANTANUEVA, new VolleyCallBack() {
                        @Override
                        public void onSuccess(JSONObject object) {

                            try {
                                //Consumimos el objeto json del RequestResponse
                                String response = object.getString("ok");
                                if(response.equals("1")){
                                    Toast.makeText(getApplicationContext(), "Se completó la acción con éxito!", Toast.LENGTH_SHORT).show();
                                    if(like_state){
                                        like.setImageDrawable(getDrawable(R.drawable.like));
                                        like_state = false;
                                    }else{
                                        like.setImageDrawable(getDrawable(R.drawable.unlike));
                                        like_state = true;
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Error no se pudo completar la operación!!", Toast.LENGTH_SHORT).show();
                                }
                                pDialog.dismiss();
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure() {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "ERROR DEL SERVIDOR!!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void noConnection() {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void timedOut() {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.timedouterror, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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