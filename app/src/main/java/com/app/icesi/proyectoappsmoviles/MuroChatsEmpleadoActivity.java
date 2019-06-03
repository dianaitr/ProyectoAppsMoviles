package com.app.icesi.proyectoappsmoviles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.icesi.proyectoappsmoviles.client_activities.NotificacionClienteActivity;
import com.app.icesi.proyectoappsmoviles.client_activities.PerfilClienteActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.NotificacionEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.PerfilEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MuroChatsEmpleadoActivity extends AppCompatActivity {

    private BottomNavigationView btn_navigation;
    private ListView lv_listaChats;
    ArrayList<Usuario> listaUsuarios;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;
    ArrayAdapter<Usuario> arrayAdapter;

    private ArrayList<Servicio> servicios_aceptados;

    private Usuario clienteSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muro_chats_empleado);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        lv_listaChats=findViewById(R.id.lv_listaChats);
        listaUsuarios=new ArrayList<Usuario>();
        servicios_aceptados=new ArrayList<Servicio>();



        //obtengo lista con servicios aceptados
        rtdb.getReference().child("servicios_en_progreso").child("aceptado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    Servicio p = objSnapshot.getValue(Servicio.class);
                    servicios_aceptados.add(p);
                    Log.e("Serviciooooo:",servicios_aceptados.get(0).getId_cliente()+"");

                }
                cargarChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_navigation=findViewById(R.id.menu_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(MuroChatsEmpleadoActivity.this, PerfilEmpleadoActivity.class);
                    i.putExtra("userType","employee");
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(MuroChatsEmpleadoActivity.this, NotificacionEmpleadoActivity.class);
                    startActivity(i);
                    finish();

                }else if(menuItem.getItemId()==R.id.menu_chat){
                    Intent i= new Intent(MuroChatsEmpleadoActivity.this, MuroChatsEmpleadoActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });

        lv_listaChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clienteSelected=new Usuario();
                clienteSelected=(Usuario) parent.getItemAtPosition(position);
                Intent i= new Intent(MuroChatsEmpleadoActivity.this,ChatActivity.class);
                i.putExtra("clienteSelected",clienteSelected.getUid());
                i.putExtra("typeUser","colaboradores");
                startActivity(i);
            }
        });
    }

    public void cargarChats(){
        //encuentra los servicios aceptados por el cliente actual
        final ArrayList<String> ids_clientes_aceptados=new ArrayList<String>();
        String id_me=auth.getCurrentUser().getUid();


        if(servicios_aceptados.size()!=0){
            for (Servicio servicio:servicios_aceptados ) {

                if(servicio.getId_colab().equals(id_me)){
                    ids_clientes_aceptados.add(servicio.getId_cliente());
                }
            }


            final ArrayList<Usuario> clients=new ArrayList<Usuario>();
            //lista de todos los colaboradores de la bd
            rtdb.getReference().child("usuarios").child("clientes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    clients.clear();

                    for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                        Usuario p= objSnapshot.getValue(Usuario.class);
                        clients.add(p);

                    }

                    listaUsuarios.clear();

                    //obtiene listaUsuarios
                    for (String id_colab:ids_clientes_aceptados ) {
                        boolean encontro=false;
                        for (int i=0; i<clients.size() && !encontro ; i++){
                            if(clients.get(i).getUid().equals(id_colab)){
                                listaUsuarios.add(clients.get(i));
                                encontro=true;
                            }
                        }
                    }

                    arrayAdapter =new ArrayAdapter<Usuario>(MuroChatsEmpleadoActivity.this,
                            android.R.layout.simple_list_item_1, listaUsuarios);
                    lv_listaChats.setAdapter(arrayAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




    }
}
