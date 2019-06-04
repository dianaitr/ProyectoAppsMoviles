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

public class CreacionBuscarServicioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    CheckBox cbox,cbox0,cbox1,cbox2,cbox3,cbox4,cbox5,cbox6,cbox7;
    Button btn_Siguiente;
    Button btn_agregarFecha;
    private Date fecha;
    private Usuario user;
    private Servicio servicio1;

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

                final DatabaseReference databaseReference = rtdb.getReference().child("servicios_en_progreso");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       for (final DataSnapshot snapshot:dataSnapshot.getChildren())
                       databaseReference.child("aceptado").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               Servicio servicio1=snapshot.getValue(Servicio.class);
                               String estado= servicio1.getEstado();
                               String cedula_colab=servicio1.getId_colab();
                               String cedula_cliente=servicio1.getId_cliente();

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
                if(cbox.isChecked()){
                    databaseReference.child("Estado").setValue(servicio1.getEstado());
                    Log.e("",user.getUid().toString());
                  //  if(){
                    //    databaseReference.child("Id_Colab").setValue(user.getCedula());
                    //}else {
                        databaseReference.child("Id_Cliente").setValue(user.getCedula());
                    //}

                    databaseReference.child("Calificacion").setValue(servicio1.getCalificacion());
                    databaseReference.child("Comentarios").setValue("");
                    databaseReference.child("Servicios").child(cbox.toString()).setValue(true);
                    databaseReference.child("Fecha servicio realizado").setValue(fecha);
                   // Servicio ser=new Servicio();
                    //ser.setEstado("");
                    //ser.setId_colab("");
                    //ser.setId_cliente("");
                    //ser.setCalificacion(0);
                    //ser.setComentarios("");
                    //servicioEnprocess.add(cbox.getText().toString());
                    //ser.setTiposServicios(ser.getTiposServicios());
                    //ser.setFechaServicio( ser.getFechaServicio());

                }

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
