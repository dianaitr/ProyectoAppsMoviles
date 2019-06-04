package com.app.icesi.proyectoappsmoviles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.icesi.proyectoappsmoviles.employee_activities.NotificacionEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class SolicitudActivity extends AppCompatActivity {

    private BottomNavigationView btn_navigation;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    TextView nombre,apellidos,fecha,horaInicio,lugar,serviciosSolicitados;
    Button btn_aceptarSolicitud,btn_rechazarSolicitud, btn_verInfoEmpleado;

    String id_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        id_cliente =getIntent().getExtras().getString("id_cliente");

        nombre=findViewById(R.id.nombreS);
        apellidos=findViewById(R.id.apellidoS);
        fecha=findViewById(R.id.txtFechaSolicitud);
        horaInicio=findViewById(R.id.txtHoraInicioSolicitud);
        lugar=findViewById(R.id.txtLugarSolicitud);
        serviciosSolicitados=findViewById(R.id.txtServiciosSolicitados);

        btn_aceptarSolicitud=findViewById(R.id.btn_aceptarSolicitud);
        btn_aceptarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtdb.getReference().child("servicios_en_progreso").child("solicitado").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Servicio servicio= dataSnapshot.getValue(Servicio.class);
                        if(servicio.getId_cliente().equals(id_cliente)){
                            servicio.setEstado("aceptado");
                            rtdb.getReference().child("servicios_en_progreso").child("aceptado").child(servicio.getId_colab()).setValue(servicio);
                            rtdb.getReference().child("servicios_en_progreso").child("solicitado").child(servicio.getId_colab()).removeValue();
                            Toast.makeText(SolicitudActivity.this, "Se ha aceptado satisfactoriamente la solicitud", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(SolicitudActivity.this, NotificacionEmpleadoActivity.class);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btn_rechazarSolicitud=findViewById(R.id.btn_rechazarSolicitud);
        btn_rechazarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtdb.getReference().child("servicios_en_progreso").child("solicitados").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Servicio servicio= dataSnapshot.getValue(Servicio.class);
                        if(servicio.getId_cliente().equals(id_cliente)){
                            servicio.setEstado("rechazado");
                            rtdb.getReference().child("servicios_en_progreso").child("rechazado").child(servicio.getId_colab()).setValue(servicio);
                            rtdb.getReference().child("servicios_en_progreso").child("solicitado").child(servicio.getId_colab()).removeValue();
                            Toast.makeText(SolicitudActivity.this, "Se ha rechazado satisfactoriamente la solicitud", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(SolicitudActivity.this, NotificacionEmpleadoActivity.class);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btn_verInfoEmpleado=findViewById(R.id.btn_verPerfilSolicitante);

        rtdb.getReference().child("usuarios").child("clientes").child(id_cliente).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario u= dataSnapshot.getValue(Usuario.class);
                nombre.setText(u.getNombres());
                apellidos.setText(u.getApellidos());
                //lugar.setText(u.get);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rtdb.getReference().child("servicios_en_progreso").child("solicitado").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Servicio u= dataSnapshot.getValue(Servicio.class);

                HashMap<String,Boolean> tiposServicios = u.getTiposServicios();
                Iterator<String> iterator = tiposServicios.keySet().iterator();


                String b="";
                while(iterator.hasNext()){
                    String a=iterator.next();
                    if(tiposServicios.get(a)==true){
                       b+=a+" , ";
                    }

                }
                serviciosSolicitados.setText(b);


                horaInicio.setText(u.getHoraInicio()+"h");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
