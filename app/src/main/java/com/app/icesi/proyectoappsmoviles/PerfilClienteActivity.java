package com.app.icesi.proyectoappsmoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilClienteActivity extends AppCompatActivity {

    private Button btn_buscarService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);
        btn_buscarService=findViewById(R.id.btn_nuevoServicioCliente);
        btn_buscarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PerfilClienteActivity.this,BuscarServicioActivity.class );
            }
        });
    }
}
