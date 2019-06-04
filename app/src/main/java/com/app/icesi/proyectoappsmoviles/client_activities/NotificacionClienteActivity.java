package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificacionClienteActivity extends AppCompatActivity implements AdaptadorNotificacionCliente.OnItemClickListener{


    private BottomNavigationView btn_navigation;
    private RecyclerView lv_listaServiciosNoti;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    private AdaptadorNotificacionCliente adapter;
    ArrayList<Servicio> listaServicios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_cliente);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        listaServicios=new ArrayList<Servicio>();


        lv_listaServiciosNoti=findViewById(R.id.lv_listaServiciosNoti);
        lv_listaServiciosNoti.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AdaptadorNotificacionCliente(listaServicios);
        lv_listaServiciosNoti.setAdapter(adapter);
        adapter.setListener(this);

        lv_listaServiciosNoti.setHasFixedSize(true);

        llenarRenglones();


        btn_navigation=findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(NotificacionClienteActivity.this, PerfilClienteActivity.class);
                    i.putExtra("userType","client");
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(NotificacionClienteActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }else if(menuItem.getItemId()==R.id.menu_chat){
                    Intent i= new Intent(NotificacionClienteActivity.this, MuroChatsClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });

    }

    private void llenarRenglones() {
        rtdb.getReference().child("servicios_en_progreso").child("solicitado").addValueEventListener(new ValueEventListener() {
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
}
