package com.app.icesi.proyectoappsmoviles.client_activities;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.icesi.proyectoappsmoviles.DatePickerFragment;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.employee_activities.PerfilEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.RegisterEmployeeActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.ServiciosActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CreacionBuscarServicioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerClass.TimePickerListener  {

    CheckBox cbox,cbox0,cbox1,cbox2,cbox3,cbox4,cbox5,cbox6,cbox7;
    Button btn_Siguiente;
    Button btn_agregarFecha;
    Button btn_agregarHora;
    Button btn_atras2;
    private Usuario user;
    private Servicio servicio1;
    private int hora_solicitada;
    private String fecha_solicitada;
    HashMap<String,Boolean> servicios_solicitados= new HashMap<String,Boolean>();

    TextView txtDate;


    //FirebaseDatabase rtdb;
    //FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_buscar_servicio);

      //  auth = FirebaseAuth.getInstance();
        //rtdb = FirebaseDatabase.getInstance();
        user=new Usuario();
        servicio1=new Servicio();



        cbox= findViewById(R.id.cb_2);
        cbox0 = findViewById(R.id.cb0_2);
        cbox1 = findViewById(R.id.cb1_2);
        cbox2 = findViewById(R.id.cb2_2);
        cbox3 = findViewById(R.id.cb3_2);
        cbox4 = findViewById(R.id.cb4_2);
        cbox5 = findViewById(R.id.cb5_2);
        cbox6 = findViewById(R.id.cb6_2);
        cbox7 = findViewById(R.id.cb7_2);
        btn_agregarFecha=findViewById(R.id.btn_agregar_fecha1);
        btn_agregarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker2 = new DatePickerFragment();
                datePicker2.show(getSupportFragmentManager(),"date picker");

            }
        });

        btn_agregarHora=findViewById(R.id.btn_agregar_hora);
        btn_agregarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment tp=new TimePickerClass();
                tp.setCancelable(false);
                tp.show(getSupportFragmentManager(),"timePicker");
            }
        });
        btn_Siguiente = findViewById(R.id.btn_sigte2);

        //TODO - checkboxes
        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificarServicios();

                if(cbox.isChecked()) {

                    servicios_solicitados.put("barrer",true);
                    servicios_solicitados.put("trapear",true);
                    servicios_solicitados.put("desempolvar",true);

                } else {
                    servicios_solicitados.put("barrer",false);
                    servicios_solicitados.put("trapear",false);
                    servicios_solicitados.put("desempolvar",false);
                } if(cbox0.isChecked()) {
                    servicios_solicitados.put("barrer",true);
                } else {
                    servicios_solicitados.put("barrer",false);
                } if(cbox1.isChecked()) {
                    servicios_solicitados.put("trapear",true);
                } else {
                    servicios_solicitados.put("trapear",false);
                } if(cbox2.isChecked()) {
                    servicios_solicitados.put("desempolvar",true);
                } else {
                    servicios_solicitados.put("trapear",false);
                } if(cbox3.isChecked()) {
                    servicios_solicitados.put("lavado_ropa",true);

                } else {
                    servicios_solicitados.put("lavado_ropa",false);
                } if(cbox4.isChecked()) {
                    servicios_solicitados.put("planchado_ropa",true);

                } else {
                    servicios_solicitados.put("planchado_ropa",false);

                } if(cbox5.isChecked()) {
                    servicios_solicitados.put("limpieza_banos",true);

                } else {
                    servicios_solicitados.put("limpieza_banos",false);

                } if(cbox6.isChecked()) {
                    servicios_solicitados.put("cocinar",true);

                } else {
                    servicios_solicitados.put("cocinar",false);

                } if(cbox7.isChecked()) {
                    servicios_solicitados.put("limpieza_cocina",true);

                } else {
                    servicios_solicitados.put("limpieza_cocina",false);

                }

                //servicio1.setTiposServicios(tiposServicios);

                //databaseReference.child(auth.getCurrentUser().getUid()).setValue(servicio1);

                Intent i = new Intent(CreacionBuscarServicioActivity.this, BuscarServicioActivity.class);
                i.putExtra("servicios_solicitados",servicios_solicitados);
                i.putExtra("fecha_solicitada",fecha_solicitada);
                i.putExtra("hora_solicitada",hora_solicitada);


                startActivity(i);
               // finish();
            }
        });

        btn_atras2=findViewById(R.id.btn_atras_CreacionBuscarService);
        btn_atras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreacionBuscarServicioActivity.this, PerfilClienteActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker,int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());

        fecha_solicitada=currentDateString;
       // txtDate.setText(currentDateString);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        hora_solicitada=hour;
    }
}
