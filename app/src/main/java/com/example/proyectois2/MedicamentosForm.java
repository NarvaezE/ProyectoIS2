package com.example.proyectois2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class MedicamentosForm extends AppCompatActivity {
    private Spinner spinnerMeds;
    private EditText e1,e2;
    private Button btnAgregarM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_form);
        //Asignamos las vistas a nuestros elementos del layout
        e1= (EditText) findViewById(R.id.e1);
        e2= (EditText) findViewById(R.id.e2);
        btnAgregarM=(Button) findViewById(R.id.btnAgregarM);
        spinnerMeds=(Spinner) findViewById(R.id.spinner);
        //Arreglo que guarda los elementos de nuestro spinner
        String [] catalogos= {"Analgésicos","Laxantes", "Antiálergicos"
                , "Antidiarreicos","Antiinflamatorios", "Antiinfecciosos","Mucolitícos"
                , "Antipiréticos", "Antiulcerosos"};
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,catalogos);
        spinnerMeds.setAdapter(adapter);

        btnAgregarM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                crearMed();
            }
        });

    }
    //metodo que inserta los datos

    public void crearMed(){
        String aux="";
        final String nombre=e2.getText().toString();
        final String cantidad=e1.getText().toString();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Cargando. Por favor Espere");
        String seleccion=spinnerMeds.getSelectedItem().toString();
        //se hace la validacion del id (int) que devolvera el catalogo
        if(seleccion=="Analgésicos"){
            aux="1";
        }else if(seleccion=="Laxantes"){
            aux="2";
        }else if(seleccion=="Antiálergicos"){
            aux="3";
        }else if(seleccion=="Antidiarreicos"){
            aux="4";
        }else if(seleccion=="Antiinflamatorios"){
            aux="5";
        }else if(seleccion=="Antiinfecciosos"){
            aux="6";
        }else if(seleccion=="Mucolitícos"){
            aux="7";
        }else if(seleccion=="Antipiréticos"){
            aux="8";
        }else if(seleccion=="Antiulcerosos"){
            aux="9";
        }
        final String tipoCatalogo=aux;
        if (nombre.isEmpty()){
            Toast.makeText(this, "Ingrese el nombre del medicamento", Toast.LENGTH_SHORT).show();
            return;
        }else if (e1.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "https://ggabysgs.lucusvirtual.es/conexion.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.equalsIgnoreCase("Se han agregado medicamentos")){
                        Toast.makeText(MedicamentosForm.this, "Se han agregado medicamentos", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Medicamentos.class));
                        finish();
                    }
                    else{
                        Toast.makeText(MedicamentosForm.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MedicamentosForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }){
                @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nombre",nombre);
                params.put("cantidad",cantidad);
                params.put("tipoCatalogo",tipoCatalogo);




                return params;
                }
            };


        RequestQueue requestQueue = Volley.newRequestQueue(MedicamentosForm.this);
        requestQueue.add(request);



        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }



}