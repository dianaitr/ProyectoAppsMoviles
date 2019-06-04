package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HistorialClienteActivity extends AppCompatActivity implements  AdaptadorCliente.OnItemClickListener{

    private BottomNavigationView btn_navigation;
    private RecyclerView lv_listaServiciosHist;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_cliente);


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

    @Override
    public void onItemClick(Usuario usuario) {

    }
}
