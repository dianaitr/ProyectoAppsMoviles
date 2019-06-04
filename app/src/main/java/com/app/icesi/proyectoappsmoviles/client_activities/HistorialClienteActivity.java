package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HistorialClienteActivity extends AppCompatActivity implements  AdaptadorHistorialCliente.OnItemClickListener{

    private BottomNavigationView btn_navigation;
    private RecyclerView lv_listaServiciosHist;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    private AdaptadorHistorialCliente adapter;
    ArrayList<Servicio> listaServicios;

    private String comentariosSelected;
    private double calificacionSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_cliente);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        listaServicios=new ArrayList<Servicio>();

        
        lv_listaServiciosHist=findViewById(R.id.lv_listaServiciosHist);
        lv_listaServiciosHist.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AdaptadorHistorialCliente(listaServicios);
        lv_listaServiciosHist.setAdapter(adapter);
        adapter.setListener(this);

        lv_listaServiciosHist.setHasFixedSize(true);
        actualizarServiciosATerminado();
        llenarRenglones();


        btn_navigation=findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(HistorialClienteActivity.this, PerfilClienteActivity.class);
                    i.putExtra("userType","client");
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(HistorialClienteActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }else if(menuItem.getItemId()==R.id.menu_chat){
                    Intent i= new Intent(HistorialClienteActivity.this, MuroChatsClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });
    }

    private void actualizarServiciosATerminado() {

        final ArrayList<Servicio> serv=new ArrayList<Servicio>();
        rtdb.getReference().child("servicios_en_progreso").child("aceptado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serv.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Servicio servicio=  objSnapshot.getValue(Servicio.class);
                    Date date = servicio.getFecha();
                    if(date.compareTo(new Date())<0){
                        serv.add(servicio);
                    }
                    servicio.setEstado("terminado");
                    rtdb.getReference().child("servicios_en_progreso").child("terminado").child(servicio.getId_colab()).setValue(servicio);
                    rtdb.getReference().child("servicios_en_progreso").child("aceptado").child(servicio.getId_colab()).removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void llenarRenglones() {

        rtdb.getReference().child("servicios_en_progreso").child("terminado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaServicios.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Servicio servicio=  objSnapshot.getValue(Servicio.class);
                    if(servicio.getId_cliente().equals(auth.getCurrentUser().getUid())){
                        listaServicios.add(servicio);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onItemClick(Servicio servicio) {



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
