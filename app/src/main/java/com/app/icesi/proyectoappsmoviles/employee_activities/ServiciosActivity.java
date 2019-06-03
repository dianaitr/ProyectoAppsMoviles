package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        DatabaseReference databaseReference = rtdb.getReference().child("servicios_ofertados").child(auth.getCurrentUser().getUid());

        if(cb.isChecked()) {
            databaseReference.child("barrer").setValue(true);
            databaseReference.child("trapear").setValue(true);
            databaseReference.child("desempolvar").setValue(true);
        } else {
            databaseReference.child("barrer").setValue(false);
            databaseReference.child("trapear").setValue(false);
            databaseReference.child("desempolvar").setValue(false);
        } if(cb0.isChecked()) {
            databaseReference.child("barrer").setValue(true);
        } else {
            databaseReference.child("barrer").setValue(false);
        } if(cb1.isChecked()) {
            databaseReference.child("trapear").setValue(true);
        } else {
            databaseReference.child("trapear").setValue(false);
        } if(cb2.isChecked()) {
            databaseReference.child("desempolvar").setValue(true);
        } else {
            databaseReference.child("desempolvar").setValue(false);
        } if(cb3.isChecked()) {
            databaseReference.child("lavado_ropa").setValue(true);
        } else {
            databaseReference.child("lavado_ropa").setValue(false);
        } if(cb4.isChecked()) {
            databaseReference.child("planchado_ropa").setValue(true);
        } else {
            databaseReference.child("planchado_ropa").setValue(false);
        } if(cb5.isChecked()) {
            databaseReference.child("limpieza_banos").setValue(true);
        } else {
            databaseReference.child("limpieza_banos").setValue(false);
        } if(cb6.isChecked()) {
            databaseReference.child("cocinar").setValue(true);
        } else {
            databaseReference.child("cocinar").setValue(false);
        } if(cb7.isChecked()) {
            databaseReference.child("limpieza_cocina").setValue(true);
        } else {
            databaseReference.child("limpieza_cocina").setValue(false);
        }
    }

}
