package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;

public class ServiciosActivity extends AppCompatActivity {

    CheckBox cb,cb0,cb1,cb2,cb3,cb4,cb5,cb6,cb7;
    Button btn_register_finish;

    FirebaseDatabase rtdb;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();

        cb= findViewById(R.id.cb);
        cb0 = findViewById(R.id.cb0);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);

        btn_register_finish = findViewById(R.id.btn_register_finish);

        //TODO - checkboxes
        btn_register_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarServicios();
                Intent i = new Intent(ServiciosActivity.this, PerfilEmpleadoActivity.class);
                i.putExtra("userType","employee");
                startActivity(i);
                finish();
            }
        });

    }

    private void verificarServicios() {

        DatabaseReference databaseReference = rtdb.getReference().child("servicios_en_progreso").child("ofertado");

        Servicio servicio= new Servicio();
        servicio.setCalificacion(0.0);
        servicio.setComentarios("");
        servicio.setEstado("ofertado");
        servicio.setFecha(new Date());
        servicio.setHoraInicio(0);
        servicio.setId_cliente("");
        servicio.setId_colab(auth.getCurrentUser().getUid());

        HashMap<String,Boolean> tiposServicios= new HashMap<String,Boolean>();

        if(cb.isChecked()) {

            tiposServicios.put("barrer",true);
            tiposServicios.put("trapear",true);
            tiposServicios.put("desempolvar",true);

        } else {
            tiposServicios.put("barrer",false);
            tiposServicios.put("trapear",false);
            tiposServicios.put("desempolvar",false);
        } if(cb0.isChecked()) {
            tiposServicios.put("barrer",true);
        } else {
            tiposServicios.put("barrer",false);
        } if(cb1.isChecked()) {
            tiposServicios.put("trapear",true);
        } else {
            tiposServicios.put("trapear",false);
        } if(cb2.isChecked()) {
            tiposServicios.put("desempolvar",true);
        } else {
            tiposServicios.put("trapear",false);
        } if(cb3.isChecked()) {
            tiposServicios.put("lavado_ropa",true);

        } else {
            tiposServicios.put("lavado_ropa",false);
        } if(cb4.isChecked()) {
            tiposServicios.put("planchado_ropa",true);

        } else {
            tiposServicios.put("planchado_ropa",false);

        } if(cb5.isChecked()) {
            tiposServicios.put("limpieza_banos",true);

        } else {
            tiposServicios.put("limpieza_banos",false);

        } if(cb6.isChecked()) {
            tiposServicios.put("cocinar",true);

        } else {
            tiposServicios.put("cocinar",false);

        } if(cb7.isChecked()) {
            tiposServicios.put("limpieza_cocina",true);

        } else {
            tiposServicios.put("limpieza_cocina",false);

        }

        servicio.setTiposServicios(tiposServicios);

        databaseReference.child(auth.getCurrentUser().getUid()).setValue(servicio);
    }

}
