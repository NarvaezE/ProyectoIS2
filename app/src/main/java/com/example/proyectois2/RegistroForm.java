package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_form);
        relacionarVistas();
        nombre.setText("");
        nickname.setText("");
        apellido.setText("");
        email.setText("");
        password.setText("");
        //Arreglo que guarda los elementos de nuestro spinner
        String[] roles = {"Cuidador", "Paciente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        spinnerRol.setAdapter(adapter);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarRegistro();
                Toast.makeText(RegistroForm.this, "-" + nombre.getText() + "-" +
                        "" + apellido.getText() + "-" + nickname.getText() + "-" + email.getText() + "-" + password.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistroForm.this, LoginForm.class));
                Toast.makeText(RegistroForm.this, "Registro Exitoso!! Inicia sesión con su username", Toast.LENGTH_LONG).show();
            }

        });

    }

    public void relacionarVistas() {
        nombre = (EditText) findViewById(R.id.nombreR);
        apellido = (EditText) findViewById(R.id.apellido);
        nickname = (EditText) findViewById(R.id.nickname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnEnviar = (Button) findViewById(R.id.btnEnviarR);
    }

    //metodo que inserta los datos

    public void agregarRegistro() {
        String aux = "";
        final String username = nickname.getText().toString().trim(), name = nombre.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando. Por favor Espere");
        String seleccion = spinnerRol.getSelectedItem().toString().trim();
        //se hace la validacion del id (int) que devolvera el catalogo
        if (seleccion == "Analgésicos") {
            aux = "1";
        } else if (seleccion == "Laxantes") {
            aux = "2";
        } else if (seleccion == "Antiálergicos") {
            aux = "3";
        } else if (seleccion == "Antidiarreicos") {
            aux = "4";
        } else if (seleccion == "Antiinflamatorios") {
            aux = "5";
        } else if (seleccion == "Antiinfecciosos") {
            aux = "6";
        } else if (seleccion == "Mucolitícos") {
            aux = "7";
        } else if (seleccion == "Antipiréticos") {
            aux = "8";
        } else if (seleccion == "Antiulcerosos") {
            aux = "9";
        }
        final String tipoCatalogo = aux;
        if (name.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del medicamento", Toast.LENGTH_SHORT).show();
            return;
        } else if (username.isEmpty()) {
            Toast.makeText(this, "Ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://localhost/insertar_meds.php/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equalsIgnoreCase("Se han agregado medicamentos")) {
                        Toast.makeText(RegistroForm.this, "Se han agregado medicamentos", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), Medicamentos.class));
                        finish();
                    } else {
                        Toast.makeText(RegistroForm.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistroForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();

                    params.put("nombre", name);
                    params.put("cantidad", username);
                    params.put("tipoCategoria", tipoCatalogo);


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