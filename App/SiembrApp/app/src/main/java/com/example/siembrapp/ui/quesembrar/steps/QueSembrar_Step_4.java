package com.example.siembrapp.ui.quesembrar.steps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.siembrapp.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                            JSONArray array = object.getJSONArray("results");
                            setPlantas(array);
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

    private void setPlantas(JSONArray plantas){
        Log.d(TAG, "setPlantas: " + plantas.length());

        // Add 4 images
        for (int i = 0; i < 10; i++){
            View view = inflater.inflate(R.layout.planta_user_item, gallery, false);

            TextView textView = view.findViewById(R.id.planta_user_item_textView);
            textView.setText(String.valueOf(i));

            ImageView imageView = view.findViewById(R.id.planta_user_item_imageView);
            imageView.setImageResource(R.drawable.stage4);

            gallery.addView(view);
        }

    }
}