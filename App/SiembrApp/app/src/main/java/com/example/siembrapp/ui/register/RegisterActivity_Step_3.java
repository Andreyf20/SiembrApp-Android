package com.example.siembrapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.MainActivity;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity_Step_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__step_3);

        final ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        final Button asadaChoiceBtn = findViewById(R.id.asadaChoiceBtn);
        final Button estadoChoiceBtn = findViewById(R.id.estadoChoiceBtn);
        final Button gobiernoChoiceBtn = findViewById(R.id.gobiernoChoiceBtn);
        final Button ongChoiceBtn = findViewById(R.id.ongChoiceBtn);
        final Button otroChoiceBtn = findViewById(R.id.otroChoiceBtn);

        asadaChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Asada");
            }
        });

        estadoChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Estado");
            }
        });

        gobiernoChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Gobierno local");
            }
        });

        ongChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("ONG");
            }
        });

        otroChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishinRegister("Otro");
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void finishinRegister(String tipoorganizacion){
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        String apellidos = extras.getString("apellidos");
        String email = extras.getString("email");
        String password = extras.getString("password");
        String razon = extras.getString("razon");
/*
        Log.d("RegisterTest", nombre+" "+apellidos+" "+email+" "+password+" "+razon+" "+tipoorganizacion);
*/
        try {
            JSONObject params = new JSONObject();
            params.put("nombre", nombre+" "+apellidos);
            params.put("correo", email);
            params.put("contrasenna", password);
            params.put("tipoOrganizacion", tipoorganizacion);
            params.put("razon", razon);

            Dialog dialog = new Dialog(RegisterActivity_Step_3.this);
            final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
            pDialog.setMessage("Creando cuenta...");
            pDialog.show();

            RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.REGISTERUSER, new VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject object) {

                    try {
                        //Consumimos el objeto json del RequestResponse
                        String response = object.getString("ok");
                        if(response.equals("1")){
                            Toast.makeText(getApplicationContext(), "Cuenta creada con éxito!!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Error: No se pudo crear la cuenta, revisar si ya se está registrado!!", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.hide();
                        finish();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}