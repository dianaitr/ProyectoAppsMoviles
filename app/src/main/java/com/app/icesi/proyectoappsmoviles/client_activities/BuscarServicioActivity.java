package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;

public class BuscarServicioActivity extends AppCompatActivity {

    private EditText buscador;
private TextView nombreEmpleado;
private TextView apellidoEmpelado;
private TextView calificacionEmpleado;
private ImageView fotoEmpleado;
private GridView grid;
    private BottomNavigationView btn_navigationview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_servicio);
        grid= findViewById(R.id.gv_buscarServicioCliente);
        fotoEmpleado=findViewById(R.id.imagenEmpleado_cliente);
        nombreEmpleado=findViewById(R.id.tv_nameEmpleado_cliente);
        apellidoEmpelado=findViewById(R.id.tv_apellidoEmpleado_cliente);
        calificacionEmpleado=findViewById(R.id.tv_numeroCalificionEmpleado_cliente);
        buscador=findViewById(R.id.et_buscadorServicioCliente);
        btn_navigationview2=findViewById(R.id.menu_navigation);
        btn_navigationview2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(BuscarServicioActivity.this, PerfilClienteActivity.class);
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(BuscarServicioActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });








    }
}
