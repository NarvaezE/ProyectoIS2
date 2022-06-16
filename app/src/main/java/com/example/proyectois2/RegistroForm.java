package com.example.proyectois2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RegistroForm extends AppCompatActivity {
     private EditText nombre, apellido, nickname, email, password;
     private Button btnEnviar;
     private Spinner spinnerRol;
    private boolean esVisible=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_form);
        this.setTitle(R.string.tRegistro);

        nombre = (EditText) findViewById(R.id.nicknameL);
        apellido = (EditText) findViewById(R.id.apellido);
        nickname = (EditText) findViewById(R.id.nickname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnEnviar = (Button) findViewById(R.id.btnEnviarR);
        spinnerRol=(Spinner)findViewById(R.id.spinnerRol);

        nombre.setText("");
        apellido.setText("");
        nickname.setText("");
        email.setText("");
        password.setText("");


        //Arreglo que guarda los elementos de nuestro spinner
        String[] roles = {"Tutor", "Paciente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        spinnerRol.setAdapter(adapter);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarRegistro();

//                Toast.makeText(RegistroForm.this, "Registro Exitoso!! Inicia sesión con tu username", Toast.LENGTH_LONG).show();
            }

        });

    }



    public void mostrarPassword(View view){
        if(!esVisible) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            esVisible = true;

        }
        else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            esVisible = false;

        }
    }
    //metodo que inserta los datos

    public void agregarRegistro(){
        String aux="";
        final String name=nombre.getText().toString().trim();
        final String ap=apellido.getText().toString().trim();
        final String username=nickname.getText().toString().trim();
        final String correo=email.getText().toString().trim();
        final String contrasena=password.getText().toString().trim();
        final ProgressDialog progresDialog=new ProgressDialog(this);
        progresDialog.setMessage("Cargando. Por favor Espere");
        String seleccion=spinnerRol.getSelectedItem().toString().trim();
        //se hace la validacion del id (int) que devolvera el catalogo
        if(seleccion=="Tutor"){
            aux="1";
        }else if(seleccion=="Paciente"){
            aux="2";
        }
        final String rol=aux;
        if (name.isEmpty()){
            Toast.makeText(this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
            return;
        }else if (ap.isEmpty()){
            Toast.makeText(this, "Ingrese su apellido", Toast.LENGTH_SHORT).show();
            return;
        }else if (username.isEmpty()){
            Toast.makeText(this, "Ingrese su nombre de usuario", Toast.LENGTH_SHORT).show();
            return;
        }else if (correo.isEmpty() && !correo.contains("@") && !correo.contains(".") ){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
            return;
        }else if (contrasena.isEmpty() && contrasena.length()<8){
            Toast.makeText(this, "Ingrese una contrasena valida (minimo 8 caracteres)", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progresDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "https://ggabysgs.lucusvirtual.es/insertar_usuario.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.equalsIgnoreCase("Registro exitoso")){
                        Toast.makeText(RegistroForm.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                        progresDialog.dismiss();
                        startActivity(new Intent(RegistroForm.this,LoginForm.class));

                    }
                    else{
                        Toast.makeText(RegistroForm.this, response, Toast.LENGTH_SHORT).show();
                        progresDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progresDialog.dismiss();
                }

            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();

                    params.put("Nombre",name);
                    params.put("Apellido",ap);
                    params.put("username",username);
                    params.put("correo",correo);
                    params.put("contrasena",contrasena);
                    params.put("rol",rol);




                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(RegistroForm.this);
            requestQueue.add(request);



        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }


}