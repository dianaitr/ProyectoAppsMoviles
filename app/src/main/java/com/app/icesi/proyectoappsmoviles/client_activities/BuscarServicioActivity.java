package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuscarServicioActivity extends AppCompatActivity implements  AdaptadorCliente.OnItemClickListener {

    private EditText buscador;
    private TextView nombreEmpleado;
    private TextView apellidoEmpelado;
    private TextView calificacionEmpleado;
    private ImageView fotoEmpleado;


    private RecyclerView lvServices;


    private AdaptadorCliente adapter;

    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    ArrayList<Usuario> listaUsuarios;

    ImageButton btn_buscarServicios;

    private BottomNavigationView btn_navigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_servicio);

        rtdb = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();


        listaUsuarios=new ArrayList<Usuario>();


        fotoEmpleado=findViewById(R.id.renglon_imagenEmpleado);
        nombreEmpleado=findViewById(R.id.renglon_nombreEmpleado);
        apellidoEmpelado=findViewById(R.id.renglon_apellidoEmpleado);
        calificacionEmpleado=findViewById(R.id.renglon_calEmpleado);
        buscador=findViewById(R.id.et_buscadorServicioCliente);


        lvServices=findViewById(R.id.lvServices);
        lvServices.setLayoutManager(new LinearLayoutManager(this));

        adapter=new AdaptadorCliente(listaUsuarios);
        lvServices.setAdapter(adapter);
        adapter.setListener(this);

        lvServices.setHasFixedSize(true);
        llenarRenglones();

        btn_buscarServicios= (ImageButton) findViewById(R.id.btn_buscarServicios);
        btn_buscarServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - buscarServicios
            }
        });

        btn_navigation=findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(BuscarServicioActivity.this, PerfilClienteActivity.class);
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(BuscarServicioActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });




    }

    private void llenarRenglones() {

        rtdb.getReference().child("usuarios").child("colaboradores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Usuario p= objSnapshot.getValue(Usuario.class);
                    listaUsuarios.add(p);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //adapter.showAllAmigos(localdb.getAllAmigosOfUser(auth.getCurrentUser().getUid()));
    }


    @Override
    public void onItemClick(Usuario amigo) {
        Intent i = new Intent( Intent.ACTION_CALL );
        //i.setData( Uri.parse("tel:"+amigo.getTelefono()) );
        startActivity(i);
    }


}
