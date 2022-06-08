package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginForm extends AppCompatActivity {
    EditText nicknameL,contrasenaL;
    Button enviarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        relacionarVistas();
        nicknameL.setText("");
        contrasenaL.setText("");



        enviarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(LoginForm.this, nicknameL.getText()+"-"+contrasenaL.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginForm.this, Recordatorios.class));
                Toast.makeText(LoginForm.this,"Inicio de sesion Exitoso!!", Toast.LENGTH_LONG).show();
            }

        });
    }
    public void relacionarVistas(){
        contrasenaL=(EditText)findViewById(R.id.contrasenaL);
        enviarSesion=(Button)findViewById(R.id.enviarSesion);
        nicknameL=(EditText)findViewById(R.id.nombreR);

    }
}