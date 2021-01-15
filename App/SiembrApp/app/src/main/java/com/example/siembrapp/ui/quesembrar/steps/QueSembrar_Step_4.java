package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Planta;
import com.example.siembrapp.ui.detallesPlanta.DetallePlantaActivity;
import com.example.siembrapp.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class QueSembrar_Step_4 extends AppCompatActivity {
    private final String TAG = "quesembrar4";

    private LayoutInflater inflater;
    private LinearLayout gallery;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar__step_4);

        gallery = findViewById(R.id.gallery);
        inflater = LayoutInflater.from(this);

        TextView title = findViewById(R.id.que_sembrar_light_title_textView2);

        ImageView atrasBTN = findViewById(R.id.atrasBtn7);
        atrasBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        String region = extras.getString("region");
        int step = extras.getInt("step");
        String light = extras.getString("luz");

        title.setText(region);

        try {
            JSONObject params = new JSONObject();
            JSONArray arr = new JSONArray();
            arr.put(light);
            arr.put(region);
            params.put("filtros", arr);

            Dialog dialog = new Dialog(QueSembrar_Step_4.this);
            final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
            pDialog.setMessage("Buscando los mejores resultados...");
            pDialog.show();

            RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.GETPLANTSFILTROS, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject object) {

                    try {
                        //Consumimos el objeto json del RequestResponse

                        String response = object.getString("ok");
                        if(response.equals("1")){
                            JSONArray arrayPlantas = object.getJSONArray("results");

                            ArrayList<Planta> plantas = new ArrayList<>();

                            for(int i =0; i < arrayPlantas.length();i++){

                                JSONObject element = arrayPlantas.getJSONObject(i);

                                Planta.PlantaBuilder plantaBuilder = new Planta.PlantaBuilder();

                                //Parse usos conocidos
                                String usosConocidosStr = element.getString("usosconocidos");
                                String[] usos = usosConocidosStr.split(", ");

                                //Parse paisajes recomendados
                                String paisajesRecomendadosStr = element.getString("paisajerecomendado");
                                String[] paisajesRecomendados = paisajesRecomendadosStr.split(", ");

                                plantaBuilder.setRequerimientosDeLuz(element.getString("requerimientosdeluz"))
                                        .setFamilia(element.getString("familia"))
                                        .setFenologia(element.getString("fenologia"))
                                        .setAgentePolinizador(element.getString("polinizador"))
                                        .setMetodoDispersion(element.getString("metododispersion"))
                                        .setNombreComun(element.getString("nombrecomun"))
                                        .setNombreCientifico(element.getString("nombrecientifico"))
                                        .setOrigen(element.getString("origen"))
                                        .setMinRangoAltitudinal(Double.parseDouble(element.getString("minrangoaltitudinal")))
                                        .setMaxRangoAltitudinal(Double.parseDouble(element.getString("maxrangoaltitudinal")))
                                        .setMetros(Double.parseDouble(element.getString("metros")))
                                        .setHabito(element.getString("requerimientosdeluz"))
                                        .setFruto(element.getString("frutos"))
                                        .setTexturaFruto(element.getString("texturafruto"))
                                        .setFlor(element.getString("flor"))
                                        .setPaisajeRecomendado(
                                                new ArrayList<>(Arrays.asList(paisajesRecomendados))
                                        )
                                        .setUsosConocidos(
                                                new ArrayList<>(Arrays.asList(usos))
                                        ).setImagen(element.getString("imagen"));

                                plantas.add(plantaBuilder.build());
                            }

                            setPlantas(plantas);
                        }else {
                            Toast.makeText(getApplicationContext(), "Error: No se pudieron obtener las plantas!!", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.hide();
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), R.string.loginError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void noConnection() {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), R.string.connectionError, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void timedOut() {
                    pDialog.hide();
                    Toast.makeText(getApplicationContext(), R.string.timedouterror, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPlantas(final ArrayList<Planta> plantas){
        // Add all plants gotten from the query
        for (int i = 0; i < plantas.size(); i++){
            final View view = inflater.inflate(R.layout.planta_user_item, gallery, false);

            TextView textView = view.findViewById(R.id.planta_user_item_textView);
            textView.setText(plantas.get(i).getNombreComun());

            TextView index = view.findViewById(R.id.planta_user_item_index);
            index.setText(String.valueOf(i));

            ImageView imageView = view.findViewById(R.id.planta_user_item_imageView);
            Bitmap bmp = plantas.get(i).getImage();

            // Handle errors with base64 image string
            if(bmp != null)
                imageView.setImageBitmap(bmp);
            else
                imageView.setImageResource(R.drawable.stage4);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView index = v.findViewById(R.id.planta_user_item_index);
                    int i = Integer.parseInt(index.getText().toString());

                    Intent detallePlantaIntent = new Intent(v.getContext(), DetallePlantaActivity.class);
                    detallePlantaIntent.putExtra("planta", plantas.get(i));
                    v.getContext().startActivity(detallePlantaIntent);
                }
            });

            gallery.addView(view);
        }
    }
}