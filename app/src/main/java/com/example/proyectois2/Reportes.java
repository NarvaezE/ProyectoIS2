package com.example.proyectois2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Reportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        this.setTitle(R.string.tReportes);
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