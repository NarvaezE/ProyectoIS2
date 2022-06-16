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
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LoginForm extends AppCompatActivity {
    EditText nicknameL;
    EditText contrasenaL;
    Button enviarSesion;
    private AsyncHttpClient cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        nicknameL = (EditText) findViewById(R.id.nicknameL);
        contrasenaL = (EditText) findViewById(R.id.contrasenaL);
        enviarSesion = (Button) findViewById(R.id.enviarSesion);
        cliente = new AsyncHttpClient();

        botonLogin();
    }
    private void botonLogin(){
        enviarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nicknameL.getText().toString().isEmpty() || contrasenaL.getText().toString().isEmpty()){
                    Toast.makeText(LoginForm.this, "Hay Campos En Blanco!!", Toast.LENGTH_SHORT).show();
                    nicknameL.setText("");
                    contrasenaL.setText("");
                }else{
                    String usu = nicknameL.getText().toString().replace(" ","%20");
                    String pas = contrasenaL.getText().toString().replace(" ","%20");
                    String url = "https://ggabysgs.lucusvirtual.es/validar_login.php?username="+usu+"&contrasena="+pas;
                    cliente.post(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                            if(statusCode == 200) {
                                String respuesta = new String(responseBody);
                                if (respuesta.equalsIgnoreCase("null")) {
                                    Toast.makeText(LoginForm.this, "Error De Usuario y/o Contraseña!!", Toast.LENGTH_SHORT).show();
                                    nicknameL.setText("");
                                    contrasenaL.setText("");
                                } else {
                                    try {
                                        JSONObject jsonObj = new JSONObject(respuesta);
                                        Usuarios u = new Usuarios();
                                        u.setId(jsonObj.getString("idUSUARIOS"));
                                        u.setNombre(jsonObj.getString("Nombre"));
                                        u.setApellido(jsonObj.getString("Apellido"));
                                        u.setNick(jsonObj.getString("username"));
                                        u.setCorreo(jsonObj.getString("correo"));
                                        u.setContrasena(jsonObj.getString("contrasena"));
                                        u.setId_tip(jsonObj.getString("rol"));
                                        u.setId_tutor(jsonObj.getString("idTutor"));
                                        Intent i = null;
                                        Toast.makeText(LoginForm.this, u.getId_tip(), Toast.LENGTH_SHORT).show();

                                        switch(u.getId_tip()){
                                            case "1":
                                                u.setNom_tip("Paciente");
                                                Toast.makeText(LoginForm.this, u.getNom_tip(), Toast.LENGTH_SHORT).show();
                                                i = new Intent(LoginForm.this,Medicamentos.class);
                                                break;
                                            case "2":
                                                u.setNom_tip("Cuidador");
                                                Toast.makeText(LoginForm.this, u.getNom_tip(), Toast.LENGTH_SHORT).show();

                                                i = new Intent(LoginForm.this,MedicamentosTutor.class);
                                                break;


                                        }
                                        i.putExtra("u",u);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(LoginForm.this, "Error Desconocido. Intentelo De Nuevo!!", Toast.LENGTH_SHORT).show();
                            nicknameL.setText("");
                            contrasenaL.setText("");
                        }
                    });
                }
            }
        });
    }
//    private void botonLogin(){
//        enviarSesion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(nicknameL.getText().toString().isEmpty() || contrasenaL.getText().toString().isEmpty()){
//                    Toast.makeText(LoginForm.this, "Hay Campos En Blanco!!", Toast.LENGTH_SHORT).show();
//                    nicknameL.setText("");
//                    contrasenaL.setText("");
//                }else{
//                    String usu = nicknameL.getText().toString().replace(" ","%20");
//                    String pas = contrasenaL.getText().toString().replace(" ","%20");
//                    String url = "https://ggabysgs.lucusvirtual.es/validar_login.php?username="+usu+"&contrasena="+pas;
//
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        if(statusCode == 200) {
//                            String respuesta = new String(responseBody);
//                            if (respuesta.equalsIgnoreCase("null")) {
//                                Toast.makeText(LoginForm.this, "Error De Usuario y/o Contraseña!!", Toast.LENGTH_SHORT).show();
//                                nicknameL.setText("");
//                                contrasenaL.setText("");
//                            } else {
//                                try {
//                                    JSONObject jsonObj = new JSONObject(respuesta);
//                                    Usuarios u = new Usuarios();
//                                    u.setId(jsonObj.getString("idUSUARIOS"));
//                                    u.setNombre(jsonObj.getString("username"));
//                                    u.setContrasena(jsonObj.getString("contrasena"));
//                                    u.setId_tip(jsonObj.getString("rol"));
//                                    Intent i = null;
//                                    switch(u.getId_tip()){
//
//                                    }
//                                    i.putExtra("u",u);
//                                    startActivity(i);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        Toast.makeText(LoginForm.this, "Error Desconocido. Intentelo De Nuevo!!", Toast.LENGTH_SHORT).show();
//                        nicknameL.setText("");
//                        contrasenaL.setText("");
//                    }
//                });
//            }
//        }
//    });
//    }

}