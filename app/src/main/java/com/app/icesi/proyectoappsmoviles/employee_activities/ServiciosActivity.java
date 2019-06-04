package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.app.icesi.proyectoappsmoviles.DatePickerFragment;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.client_activities.BuscarServicioActivity;
import com.app.icesi.proyectoappsmoviles.client_activities.CreacionBuscarServicioActivity;
import com.app.icesi.proyectoappsmoviles.client_activities.TimePickerClass;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ServiciosActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerClass.TimePickerListener{

    CheckBox cb,cb0,cb1,cb2,cb3,cb4,cb5,cb6,cb7;
    Button btn_register_finish;
    Button btn_agregarFecha2;
    Button btn_agregarHora2;
    private int hora_solicitada2;
    private String fecha_solicitada2;

    HashMap<String,Boolean> servicios_solicitados2= new HashMap<String,Boolean>();

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



        btn_agregarFecha2=findViewById(R.id.btn_agregar_fecha2);
        btn_agregarFecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker3 = new DatePickerFragment();
                datePicker3.show(getSupportFragmentManager(),"date picker servicio");

            }
        });

        btn_agregarHora2=findViewById(R.id.btn_agregar_hora2);
        btn_agregarHora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment tp=new TimePickerClass();
                tp.setCancelable(false);
                tp.show(getSupportFragmentManager(),"timePicker2");
            }
        });

        btn_register_finish = findViewById(R.id.btn_register_finish);

        //TODO - checkboxes
        btn_register_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarServicios();

                Intent i = new Intent(ServiciosActivity.this, PerfilEmpleadoActivity.class);
                i.putExtra("userType","employee");
                i.putExtra("servicios_solicitados",servicios_solicitados2);
                i.putExtra("fecha_solicitada",fecha_solicitada2);
                i.putExtra("hora_solicitada",hora_solicitada2);

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


        if(cb.isChecked()) {

            servicios_solicitados2.put("barrer",true);
            servicios_solicitados2.put("trapear",true);
            servicios_solicitados2.put("desempolvar",true);

        } else {
            servicios_solicitados2.put("barrer",false);
            servicios_solicitados2.put("trapear",false);
            servicios_solicitados2.put("desempolvar",false);
        } if(cb0.isChecked()) {
            servicios_solicitados2.put("barrer",true);
        } else {
            servicios_solicitados2.put("barrer",false);
        } if(cb1.isChecked()) {
            servicios_solicitados2.put("trapear",true);
        } else {
            servicios_solicitados2.put("trapear",false);
        } if(cb2.isChecked()) {
            servicios_solicitados2.put("desempolvar",true);
        } else {
            servicios_solicitados2.put("trapear",false);
        } if(cb3.isChecked()) {
            servicios_solicitados2.put("lavado_ropa",true);

        } else {
            servicios_solicitados2.put("lavado_ropa",false);
        } if(cb4.isChecked()) {
            servicios_solicitados2.put("planchado_ropa",true);

        } else {
            servicios_solicitados2.put("planchado_ropa",false);

        } if(cb5.isChecked()) {
            servicios_solicitados2.put("limpieza_banos",true);

        } else {
            servicios_solicitados2.put("limpieza_banos",false);

        } if(cb6.isChecked()) {
            servicios_solicitados2.put("cocinar",true);

        } else {
            servicios_solicitados2.put("cocinar",false);

        } if(cb7.isChecked()) {
            servicios_solicitados2.put("limpieza_cocina",true);

        } else {
            servicios_solicitados2.put("limpieza_cocina",false);

        }

        servicio.setTiposServicios(servicios_solicitados2);

        databaseReference.child(auth.getCurrentUser().getUid()).setValue(servicio);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        hora_solicitada2=hour;
    }

    @Override
    public void onDateSet(DatePicker datePicker,int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        fecha_solicitada2=currentDateString;
    }
}
