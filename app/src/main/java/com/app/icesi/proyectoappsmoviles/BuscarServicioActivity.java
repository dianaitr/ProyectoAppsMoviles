package com.app.icesi.proyectoappsmoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BuscarServicioActivity extends AppCompatActivity {

    private EditText buscador;
private TextView nombreEmpleado;
private TextView apellidoEmpelado;
private TextView calificacionEmpleado;
private ImageView fotoEmpleado;
private GridView grid;

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








    }
}
