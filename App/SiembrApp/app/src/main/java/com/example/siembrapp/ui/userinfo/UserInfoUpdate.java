package com.example.siembrapp.ui.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.siembrapp.R;

public class UserInfoUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner userSP;
    String tipoOrganizacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);

        userSP = findViewById(R.id.user_info_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_organizacion, android.R.layout.simple_spinner_item);
        userSP.setAdapter(adapter);
        userSP.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.tipoOrganizacion = (String) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);
    }

    private void setSelectionSpinner(String type) {
        switch (type) {
            case "ASADA":
                userSP.setSelection(1);
                break;
            case "ESTADO":
                userSP.setSelection(2);
                break;
            case "GOBIERNO LOCAL":
                userSP.setSelection(3);
                break;
            case "ONG":
                userSP.setSelection(4);
                break;
            case "OTRO":
                userSP.setSelection(5);
                break;
            default:
                userSP.setSelection(0);
                break;
        }
    }
}