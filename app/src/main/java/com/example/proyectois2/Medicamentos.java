package com.example.proyectois2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Medicamentos extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    public static ArrayList<Meds>medsArrayList=new ArrayList<>();
    String url="https://ggabysgs.lucusvirtual.es/mostrar_medicamentos.php";
    Meds meds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        listView=findViewById(R.id.listMeds);
        adapter=new Adapter(this,medsArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view,  position, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            ProgressDialog progressDialog = new ProgressDialog(view.getContext());

            CharSequence[] dialogItem = {"Detalles","Editar","Eliminar"};
            builder.setTitle(medsArrayList.get(position).getNombre());
            builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    switch (i){

                        case 0:

                            startActivity(new Intent(getApplicationContext(),detallesMeds.class)
                                    .putExtra("position",position));

                            break;

                        case 1:
                            startActivity(new Intent(getApplicationContext(),editarMeds.class)
                                    .putExtra("position",position));

                            break;

                        case 2:

                            deleteData(medsArrayList.get(position).getId());

                            break;


                    }



                }
            });


            builder.create().show();


        });

        retrieveData();


    }

    private void deleteData(final String id) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://arsltechmysql.000webhostapp.com/delete.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equalsIgnoreCase("Data Deleted")){
                            Toast.makeText(Medicamentos.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Medicamentos.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Medicamentos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void retrieveData(){
        Toast.makeText(Medicamentos.this, "entra al retrieve", Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        medsArrayList.clear();
                        try{
                            Toast.makeText(Medicamentos.this, "entra al try", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("1");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");


                            if(sucess.equals("1")){

                                Toast.makeText(Medicamentos.this, "entra al if equals 1", Toast.LENGTH_SHORT).show();
                                for(int i=0;i<jsonArray.length();i++){

                                    Toast.makeText(Medicamentos.this, "entra al for", Toast.LENGTH_SHORT).show();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("idMEDICAMENTOS");
                                    String nombre = object.getString("nombre");
                                    String cantidad = object.getString("cantidad");
                                    String tipoCatalogo = object.getString("tipoCatalogo");


                                    meds = new Meds(id,nombre,cantidad,tipoCatalogo);
                                    medsArrayList.add(meds);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(Medicamentos.this, "entra al for", Toast.LENGTH_SHORT).show();


                                    Toast.makeText(Medicamentos.this, "id:"+id+"\nnombre:"+nombre+"\ncantidad:"+cantidad+"tipoCatalogo:"+tipoCatalogo, Toast.LENGTH_SHORT).show();

                                }



                            }




                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Medicamentos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




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
        }
        return super.onOptionsItemSelected(item);
    }
}