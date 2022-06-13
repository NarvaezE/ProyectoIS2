package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

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
//                Toast.makeText(LoginForm.this, nicknameL.getText()+"-"+contrasenaL.getText(), Toast.LENGTH_SHORT).show();
                validarUsuario("https://ggabysgs.lucusvirtual.es/validar_login.php");
            }

        });
    }
    public void relacionarVistas(){
        contrasenaL=(EditText)findViewById(R.id.contrasenaL);
        enviarSesion=(Button)findViewById(R.id.enviarSesion);
        nicknameL=(EditText)findViewById(R.id.nombreR);

    }
    private void validarUsuario (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginForm.this, response.toString(), Toast.LENGTH_SHORT).show();
//                if(!response.equalsIgnoreCase("Error")){
                    Intent IniciarSesion = new Intent(getApplicationContext(),Medicamentos.class);
                    startActivity(IniciarSesion);
//                    Toast.makeText(LoginForm.this, response.toString(), Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(LoginForm.this, "Username o Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginForm.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("username", nicknameL.getText().toString());
                parametros.put("contrasena", contrasenaL.getText().toString());
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}