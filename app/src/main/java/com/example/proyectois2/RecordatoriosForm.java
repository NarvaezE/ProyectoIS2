package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.*;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.os.Bundle;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RecordatoriosForm extends AppCompatActivity {

    private AsyncHttpClient cliente;
    private Spinner spMeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios_form);

        cliente=new AsyncHttpClient();
        spMeds=(Spinner)findViewById(R.id.spinner2);
        llenarSpinner();
    }

    private void llenarSpinner(){
        String url="https://ggabysgs.lucusvirtual.es/mostrar_medicamentos.php";
        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private void cargarSpinner(String respuesta){
        ArrayList <Meds> lista=new ArrayList<Meds>();
        try {
            JSONArray jsonArray = new JSONArray(respuesta);
            for(int i=0;i<jsonArray.length();i++){
                Meds m=new Meds();
                m.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                lista.add(m);
            }
            ArrayAdapter<Meds> adaptador=new ArrayAdapter<Meds>(this, android.R.layout.simple_dropdown_item_1line,lista);
            spMeds.setAdapter(adaptador);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}