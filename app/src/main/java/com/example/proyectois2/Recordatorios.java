package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Recordatorios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);
    }
    public void agregarRecordatorio(View v) {
        startActivity(new Intent(getApplicationContext(),RecordatoriosForm.class));
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

//                Intent i;
//                i = new Intent (Recordatorios.this, MainActivity.class);
//                startActivity(i);
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