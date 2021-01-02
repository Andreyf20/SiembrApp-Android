package com.example.siembrapp.ui.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.User;

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