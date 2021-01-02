package com.example.siembrapp.ui.userinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.siembrapp.R;
import com.example.siembrapp.data.model.God;
import com.example.siembrapp.data.model.User;
import com.example.siembrapp.ui.register.RegisterActivity_Step_3;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ImageButton backBTN = findViewById(R.id.atrasBtn5);

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView name = findViewById(R.id.user_info_username_textView);
        TextView email = findViewById(R.id.user_info_email_textView);
        TextView tipoOrganizacion = findViewById(R.id.user_info_aditionalinfo_textView);

        User user = God.getLoggedUser();

        name.setText(user.getNombre());
        email.setText(user.getCorreo());
        tipoOrganizacion.setText(user.getTipoOrganizacion());

        LinearLayout user_info_layout = findViewById(R.id.user_info_linearLayout);

        user_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateUser = new Intent(getApplicationContext(), UserInfoUpdate.class);
                startActivity(updateUser);
                finish();
            }
        });
    }
}