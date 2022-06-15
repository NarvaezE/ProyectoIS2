package com.example.proyectois2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.*;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RecordatoriosForm extends AppCompatActivity {
    private TextView eFecha1,eFecha2,eHora,eRepetir;
    private AsyncHttpClient cliente;
    private Spinner spMeds,spH;
    private int hora, minuto, idMedicina=0;
    DatePickerDialog.OnDateSetListener setListener;
    DatePickerDialog.OnDateSetListener setListen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios_form);
        this.setTitle(R.string.tRecordForm);

        cliente=new AsyncHttpClient();
        spMeds=(Spinner)findViewById(R.id.spinner2);
        spH=(Spinner)findViewById(R.id.horasDosis);
        eFecha1=(EditText)findViewById(R.id.eFechaI);
        eFecha2=(EditText)findViewById(R.id.eFechaF);
        eHora=(EditText)findViewById(R.id.eHora);
        eRepetir=(EditText)findViewById(R.id.eRepeticiones);

        //Arreglo con horas que llena el spinnerHoras
        String [] horas= {"Seleccionar","01:00","02:00", "03:00"
                , "04:00","05:00", "06:00","07:00", "08:00", "09:00",
                "10:00","11:00","12:00"};
        ArrayAdapter <String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,horas);
        spH.setAdapter(adapter);
        //

        llenarSpinner();
        fechaInicial();
        fechaFinal();
        horaInicial();

        crearRecordatorio();
    }

    public void crearRecordatorio(){
        final String horaInicio=eHora.getText().toString();
        final String fechaI=eFecha1.getText().toString();
        final String fechaF=eFecha2.getText().toString();
        final String horaDosis=spH.getSelectedItem().toString();
        final String repeticion=eRepetir.getText().toString();
//        Meds medi=new Meds();
//        medi=spMeds.
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Cargando. Por favor Espere");
        //aun hace falta hacer que el metodo nos devuelva el id del medicamento
        final String idMedicamento= obtenerIdMedicina(spMeds.getSelectedItem().toString());
        if (horaInicio.isEmpty()){
            Toast.makeText(this, "Seleccione una hora en la que iniciaran los recordatorios", Toast.LENGTH_SHORT).show();
            return;
        }else if (fechaI.isEmpty()){
            Toast.makeText(this, "Seleccione una fecha de inicio", Toast.LENGTH_SHORT).show();
            return;
        }else if (fechaF.isEmpty()){
            Toast.makeText(this, "Seleccione una fecha de termino", Toast.LENGTH_SHORT).show();
            return;
        }else if (horaDosis.equalsIgnoreCase("Seleccionar")){
            Toast.makeText(this, "Seleccione un intervalo de horas", Toast.LENGTH_SHORT).show();
            return;
        }else if (repeticion.isEmpty()){
            Toast.makeText(this, "Ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
            return;
        }else{
            progressDialog.show();
            //CREAR EL WEBSERVICE PARA ACTUALIZAR EL RECORDATORIO
            StringRequest request=new StringRequest(Request.Method.POST, "https://ggabysgs.lucusvirtual.es/agregar_recordatorio.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if(response.equalsIgnoreCase("Se han agregado medicamentos")){
                        Toast.makeText(RecordatoriosForm.this, "Se han agregado medicamentos", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),Recordatorios.class));
                        finish();
                    }
                    else{
                        Toast.makeText(RecordatoriosForm.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RecordatoriosForm.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<>();
                    //actualizar con los valores de mi tabla RECORDATORIOS
                    params.put("hora",horaInicio);
                    params.put("fecha_inicial",fechaI);
                    params.put("fecha_final",fechaF);
//                    params.put("id_usuario",idUser);
                    params.put("repetir",repeticion);
                    params.put("id_medicamentos",idMedicamento);
                    params.put("hora_dosis",horaDosis);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(RecordatoriosForm.this);
            requestQueue.add(request);
        }

    }

    private void horaInicial(){
        eHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicializamos el timePickerDialog
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        RecordatoriosForm.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Inicializamos hora y minutos
                                hora=hourOfDay;
                                minuto=minute;
                                //Inicializamos el calendario
                                Calendar c =Calendar.getInstance();
                                //Agregamos la hora seleccionado en nuestro textview
                                c.set(0,0,0,hora,minuto);

                                eHora.setText(DateFormat.format("HH:mm",c));
                            }
                        },24,0,true
                );
                timePickerDialog.updateTime(hora,minuto);
                //mostramos el timepickerdialog
                timePickerDialog.show();
            }
        });

    }
    private void fechaFinal(){
        Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        eFecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker =new DatePickerDialog(
                        RecordatoriosForm.this,android.R.style.Theme_Holo_Light_Dialog,
                        setListen,year,month,day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        });
        setListen=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String date=year+"-"+0+(month+1)+"-"+dayOfMonth;
                eFecha2.setText(date);


            }
        };
    }
    private void fechaInicial(){
        Calendar calendario = Calendar.getInstance();
        final int anio=calendario.get(Calendar.YEAR);
        final int mes=calendario.get(Calendar.MONTH);
        final int dia=calendario.get(Calendar.DAY_OF_MONTH);

        eFecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =new DatePickerDialog(
                        RecordatoriosForm.this,android.R.style.Theme_Holo_Light_Dialog,
                        setListener,anio,mes,dia);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=mes+1;
                String date=year+"-"+0+month+"-"+dayOfMonth;
                eFecha1.setText(date);


            }
        };
    }

    private void llenarSpinner(){
        String url="https://ggabysgs.lucusvirtual.es/mostrar_medicamentos.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
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
                m.setId(jsonArray.getJSONObject(i).getString("idMEDICAMENTOS"));
                m.setNombre(jsonArray.getJSONObject(i).getString("nombre"));

                lista.add(m);
            }
            ArrayAdapter<Meds> adaptador=new ArrayAdapter<Meds>(this, android.R.layout.simple_dropdown_item_1line,lista);
            spMeds.setAdapter(adaptador);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String obtenerIdMedicina(String seleccion){
        //crear este web service
        String url="https://ggabysgs.lucusvirtual.es/obtener_idmed.php?nombre="+seleccion;

        return "2";
    }
}