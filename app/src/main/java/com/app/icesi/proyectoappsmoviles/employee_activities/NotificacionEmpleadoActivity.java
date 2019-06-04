package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.MuroChatsEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.client_activities.AdaptadorNotificacionCliente;
import com.app.icesi.proyectoappsmoviles.client_activities.NotificacionClienteActivity;
import com.app.icesi.proyectoappsmoviles.client_activities.PerfilClienteActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificacionEmpleadoActivity extends AppCompatActivity implements AdaptadorNotificacionEmpleado.OnItemClickListener
{

    private BottomNavigationView btn_navigation;
    private RecyclerView lv_listaServiciosNoti;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    private AdaptadorNotificacionEmpleado adapter;
    ArrayList<Servicio> listaServicios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_empleado);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        listaServicios=new ArrayList<Servicio>();


        lv_listaServiciosNoti=findViewById(R.id.lv_listaServiciosNoti);
        lv_listaServiciosNoti.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AdaptadorNotificacionEmpleado(listaServicios);
        lv_listaServiciosNoti.setAdapter(adapter);
        adapter.setListener(this);

        lv_listaServiciosNoti.setHasFixedSize(true);

        llenarRenglones();


        btn_navigation=findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(NotificacionEmpleadoActivity.this, PerfilEmpleadoActivity.class);
                    i.putExtra("userType","employee");
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(NotificacionEmpleadoActivity.this, NotificacionEmpleadoActivity.class);
                    startActivity(i);
                    finish();

                }else if(menuItem.getItemId()==R.id.menu_chat){
                    Intent i= new Intent(NotificacionEmpleadoActivity.this, MuroChatsEmpleadoActivity.class);
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
                    if(servicio.getId_colab().equals(auth.getCurrentUser().getUid())){
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
