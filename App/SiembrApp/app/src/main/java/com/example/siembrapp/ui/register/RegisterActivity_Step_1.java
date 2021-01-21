package com.example.siembrapp.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.siembrapp.R;


public class RegisterActivity_Step_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__step_1);

        final ImageButton atrasBtn = findViewById(R.id.atrasBtn);
        final Button siguienteBtn = findViewById(R.id.continueStep2Btn);

        final EditText nombreEditText = findViewById(R.id.nombreEditText);
        final EditText apellidosEditText = findViewById(R.id.apellidosEditText);
        final EditText passwordEditText = findViewById(R.id.passwordET);
        final EditText passwordVerificationEditText = findViewById(R.id.passwordVerificationET);
        final EditText emailEditText = findViewById(R.id.emailET);

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Not implemented",Toast.LENGTH_SHORT).show();
            }
        });

        siguienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreEditText.getText().toString();
                String apellidos = apellidosEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordVerification = passwordVerificationEditText.getText().toString();

                // Validations of all strings
                // Not empty strings
                if(nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()
                    || password.isEmpty() || passwordVerification.isEmpty()){
                    showErrorMsg("Error: No deben haber campos vacíos!!");
                    return;
                }

                // Valid email
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showErrorMsg("Error: El email introducido no parece ser válido!!");
                    return;
                }

                // Valid password and passwordValidation
                if(password.length() < 5 || !password.equals(passwordVerification)){
                    showErrorMsg("Error: la contraseña es muy corta o no coinciden!!");
                    return;
                }

                Intent registerStep2Intent = new Intent(getApplicationContext(), RegisterActivity_Step_2.class);
                registerStep2Intent.putExtra("nombre", nombre);
                registerStep2Intent.putExtra("apellidos", apellidos);
                registerStep2Intent.putExtra("email", email);
                registerStep2Intent.putExtra("password", password);

                startActivity(registerStep2Intent);
                finish();
            }
        });

        atrasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showErrorMsg(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}