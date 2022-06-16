package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.AdapterView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/*
Programador: Edgar Narvaez
Fecha: 10/06/22
 */
public class Medicamentos extends AppCompatActivity {
    private AsyncHttpClient cliente;
    private ListView listView;
    private String [] catalogos= {"Seleccionar","Analgésicos","Laxantes", "Antiálergicos"
            , "Antidiarreicos","Antiinflamatorios", "Antiinfecciosos","Mucolitícos"
            , "Antipiréticos", "Antiulcerosos"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        cliente=new AsyncHttpClient();
        listView=findViewById(R.id.listReminder);
        this.setTitle(R.string.tMedicamentos);
        listarMedicamentos();
    }

    private void listarMedicamentos(){
        String url="https://ggabysgs.lucusvirtual.es/mostrar_medicamentos.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    cargarLista(new String(responseBody));

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    private void cargarLista(String respuesta){
        ArrayList <Meds> lista=new ArrayList<Meds>();
        try {
            JSONArray jsonArray = new JSONArray(respuesta);
            for(int i=0;i<jsonArray.length();i++){
                Meds m=new Meds();
                m.setId(jsonArray.getJSONObject(i).getString("idMEDICAMENTOS"));
                m.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                m.setCantidad(jsonArray.getJSONObject(i).getString("cantidad"));
                m.setTipoCatalogo(jsonArray.getJSONObject(i).getString("tipoCatalogo"));
                lista.add(m);
            }
            ArrayAdapter<Meds> adaptador=new ArrayAdapter<Meds>(this, android.R.layout.simple_list_item_1,lista);
            listView.setAdapter(adaptador);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final Meds m = lista.get(i);
                    android.app.AlertDialog.Builder a = new android.app.AlertDialog.Builder(Medicamentos.this);
                    a.setCancelable(true);
                    a.setTitle("PREGUNTA");
                    a.setMessage("¿Desea Eliminar El Producto "+m.getNombre()+"?");

                    a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String url = "https://ggabysgs.lucusvirtual.es/eliminar_med.php?idMEDICAMENTOS="+m.getId();
                            cliente.post(url, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    if(statusCode == 200){
                                        Toast.makeText(Medicamentos.this, "Producto Eliminado Correctamente!!", Toast.LENGTH_SHORT).show();
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        listarMedicamentos();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });

                        }
                    });

                    a.show();
                    return true;
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Meds m = lista.get(i);

                    StringBuffer b = new StringBuffer();
                    b.append("ID: " + m.getId() + "\n");
                    b.append("NOMBRE: " + m.getNombre() + "\n");
                    b.append("CANTIDAD: " + m.getCantidad() + "\n");
                    b.append("CATEGORIA: " + catalogos[Integer.parseInt(m.getTipoCatalogo())]);

                    android.app.AlertDialog.Builder a = new AlertDialog.Builder(Medicamentos.this);
                    a.setCancelable(true);
                    a.setTitle("Detalle");
                    a.setMessage(b.toString());
                    a.setIcon(R.drawable.okk);
                    a.show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void agregarMedicamento(View v) {
        startActivity(new Intent(getApplicationContext(),MedicamentosForm.class));
    }
    //Metodo que muestra y oculta nuestro menu
    public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_recordatorios, menu);
        return true;
    }

    //metodo que asigna las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.itemRecordatorios){
            startActivity(new Intent(getApplicationContext(),Recordatorios.class));
            Toast.makeText(this, "Recordatarios", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.itemMedicamentos){
           startActivity(new Intent(getApplicationContext(),Medicamentos.class));
            Toast.makeText(this, "Medicamentos", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.itemReportes){
            startActivity(new Intent(getApplicationContext(),Reportes.class));
            Toast.makeText(this, "Reportes", Toast.LENGTH_SHORT).show();
        }else if (id==R.id.cerrarSesion){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            Toast.makeText(this, "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}