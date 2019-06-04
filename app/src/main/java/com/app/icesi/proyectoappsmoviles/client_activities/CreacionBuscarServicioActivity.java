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

public class CreacionBuscarServicioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    CheckBox cbox,cbox0,cbox1,cbox2,cbox3,cbox4,cbox5,cbox6,cbox7;
    Button btn_Siguiente;
    Button btn_agregarFecha;
    private Date fecha;
    private Usuario user;
    private Servicio servicio1;
    HashMap<String,Boolean> tiposServicios= new HashMap<String,Boolean>();

    TextView txtDate;

    ArrayList<String>servicioEnprocess;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_buscar_servicio);

        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();
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
        btn_agregarFecha=findViewById(R.id.btn_agregar_hora_fecha);
        btn_agregarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker2 = new DatePickerFragment();
                datePicker2.show(getSupportFragmentManager(),"date picker");
            }
        });

        btn_Siguiente = findViewById(R.id.btn_sigte);

        //TODO - checkboxes
        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificarServicios();

                final DatabaseReference databaseReference = rtdb.getReference().child("servicios_en_progreso").child("solicitado");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       for (final DataSnapshot snapshot:dataSnapshot.getChildren())
                       databaseReference.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               Servicio servicio1=snapshot.getValue(Servicio.class);
                               servicio1.setEstado("solicitado");
                               servicio1.setFecha(new Date());


                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(cbox.isChecked()) {

                    tiposServicios.put("barrer",true);
                    tiposServicios.put("trapear",true);
                    tiposServicios.put("desempolvar",true);

                } else {
                    tiposServicios.put("barrer",false);
                    tiposServicios.put("trapear",false);
                    tiposServicios.put("desempolvar",false);
                } if(cbox0.isChecked()) {
                    tiposServicios.put("barrer",true);
                } else {
                    tiposServicios.put("barrer",false);
                } if(cbox1.isChecked()) {
                    tiposServicios.put("trapear",true);
                } else {
                    tiposServicios.put("trapear",false);
                } if(cbox2.isChecked()) {
                    tiposServicios.put("desempolvar",true);
                } else {
                    tiposServicios.put("trapear",false);
                } if(cbox3.isChecked()) {
                    tiposServicios.put("lavado_ropa",true);

                } else {
                    tiposServicios.put("lavado_ropa",false);
                } if(cbox4.isChecked()) {
                    tiposServicios.put("planchado_ropa",true);

                } else {
                    tiposServicios.put("planchado_ropa",false);

                } if(cbox5.isChecked()) {
                    tiposServicios.put("limpieza_banos",true);

                } else {
                    tiposServicios.put("limpieza_banos",false);

                } if(cbox6.isChecked()) {
                    tiposServicios.put("cocinar",true);

                } else {
                    tiposServicios.put("cocinar",false);

                } if(cbox7.isChecked()) {
                    tiposServicios.put("limpieza_cocina",true);

                } else {
                    tiposServicios.put("limpieza_cocina",false);

                }

                servicio1.setTiposServicios(tiposServicios);

                databaseReference.child(auth.getCurrentUser().getUid()).setValue(servicio1);

                Intent i = new Intent(CreacionBuscarServicioActivity.this, BuscarServicioActivity.class);
                startActivity(i);
                finish();
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

       // txtDate.setText(currentDateString);
    }
}
