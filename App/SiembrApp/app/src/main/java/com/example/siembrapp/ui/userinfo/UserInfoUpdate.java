package com.example.siembrapp.ui.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siembrapp.API.RequestHandler;
import com.example.siembrapp.Interfaces.VolleyCallBack;
import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.User;
import com.example.siembrapp.ui.register.RegisterActivity_Step_3;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner userSP;
    String tipoOrganizacion = "";
    EditText name;
    EditText email;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);

        ImageButton backBTN = findViewById(R.id.atrasBtn6);

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        user = God.getLoggedUser();

        name = findViewById(R.id.user_info_nombre_input);
        email = findViewById(R.id.user_info_email_input);

        name.setText(user.getNombre());
        email.setText(user.getCorreo());

        userSP = findViewById(R.id.user_info_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_organizacion, android.R.layout.simple_spinner_item);
        userSP.setAdapter(adapter);
        userSP.setOnItemSelectedListener(this);

        setSelectionSpinner(user.getTipoOrganizacion());

        Button update = findViewById(R.id.user_info_update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = name.getText().toString();
                final String correo = email.getText().toString();

                try {
                    JSONObject params = new JSONObject();
                    params.put("nombre", nombre);
                    params.put("correo", correo);
                    params.put("tipoOrganizacion", tipoOrganizacion);
                    params.put("uuid", user.getUuid());

                    Dialog dialog = new Dialog(UserInfoUpdate.this);
                    final ProgressDialog pDialog = new ProgressDialog(dialog.getContext());
                    pDialog.setMessage("Actualizando datos...");
                    pDialog.show();

                    RequestHandler.APIRequester.request(params, getApplicationContext(), RequestHandler.UPDATEUSER, new VolleyCallBack() {
                        @Override
                        public void onSuccess(JSONObject object) {

                            try {
                                //Consumimos el objeto json del RequestResponse
                                String response = object.getString("ok");
                                if(response.equals("1")){
                                    user.setCorreo(correo);
                                    user.setTipoOrganizacion(tipoOrganizacion);
                                    user.setNombre(nombre);
                                    God.setLoggedUser(user);
                                    Toast.makeText(getApplicationContext(), "Cuenta actualizada con éxito!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Error: No se pudo actualizar la cuenta!!", Toast.LENGTH_SHORT).show();
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
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.tipoOrganizacion = (String) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        setSelectionSpinner(user.getTipoOrganizacion());
    }

    private void setSelectionSpinner(String type) {
        switch (type) {
            case "Estado":
                userSP.setSelection(1);
                break;
            case "Gobierno local":
                userSP.setSelection(2);
                break;
            case "ONG":
                userSP.setSelection(3);
                break;
            case "Otro":
                userSP.setSelection(4);
                break;
            default:
                // ASADA
                userSP.setSelection(0);
                break;
        }
    }
}