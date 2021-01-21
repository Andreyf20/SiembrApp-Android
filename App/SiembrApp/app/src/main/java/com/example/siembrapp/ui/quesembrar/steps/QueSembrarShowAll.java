package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Adapters.PlantasCardAdapter;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.Planta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QueSembrarShowAll extends AppCompatActivity {
    EditText input;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_sembrar_show_all);

        input = findViewById(R.id.search_plant_input);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    loadPlants(false);
                    handled = true;
                }
                return handled;
            }
        });

        rv = findViewById(R.id.show_all_recyclerView);

        Bundle extras = getIntent().getExtras();
        final String region = extras.getString("region");

        ImageView atrasBTN = findViewById(R.id.atrasBtn8);
        atrasBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView filtrar = findViewById(R.id.filtrar_textView);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlants(true);
            }
        });

        TextView title = findViewById(R.id.que_sembrar_light_title_textView3);
        title.setText(region);
    }

    private void loadPlants(boolean closeKeyboard){
        String in = input.getText().toString();

        if(in.equals("")){
            //Toast
            Toast.makeText(getApplicationContext(), "Error, por favor revisar los datos introducidos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(closeKeyboard)
            input.onEditorAction(EditorInfo.IME_ACTION_DONE);

        try {
            JSONObject params = new JSONObject();
            JSONArray arr = new JSONArray();
            arr.put(in);
            params.put("filtros", arr);

            Dialog dialog = new Dialog(QueSembrarShowAll.this);
            final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
            pDialog.setMessage("Buscando...");
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
                                        .setHabito(element.getString("habito"))
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

    private void setPlantas(List<Planta> plantas){
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        PlantasCardAdapter adapter = new PlantasCardAdapter(plantas, false);

        rv.setAdapter(adapter);

        Objects.requireNonNull(rv.getAdapter()).notifyDataSetChanged();
    }
}