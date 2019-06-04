package com.app.icesi.proyectoappsmoviles.client_activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.employee_activities.PerfilEmpleadoBuscadoActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BuscarServicioActivity extends AppCompatActivity implements  AdaptadorCliente.OnItemClickListener {

    private Spinner sp_filtro;
    private TextView nombreEmpleado;
    private TextView apellidoEmpelado;
    private TextView calificacionEmpleado;
    private ImageView fotoEmpleado;
    private Button btn_seleccionarEmpleado;


    private RecyclerView lvServices;

    private AdaptadorCliente adapter;

    FirebaseDatabase rtdb;
    FirebaseAuth auth;

    ArrayList<Usuario> listaUsuarios; // mostrados en el adapter
    //List<Usuario> usuariosBuscados; // filtrados por servicio, fecha y hora

    private Map<String, Boolean> servicios_solicitados;
    private int hora_solicitada;
    private Date fecha_solicitada;

    private BottomNavigationView btn_navigation;

    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_servicio);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        myLocation = new Location(LocationManager.NETWORK_PROVIDER);

        obtenerValoresSolicitud();
        getMyLocation();

        listaUsuarios = new ArrayList<Usuario>();


        fotoEmpleado = findViewById(R.id.renglon_imagenEmpleado);
        nombreEmpleado = findViewById(R.id.renglon_nombreEmpleado);
        apellidoEmpelado = findViewById(R.id.renglon_apellidoEmpleado);
        calificacionEmpleado = findViewById(R.id.renglon_calEmpleado);

        sp_filtro = findViewById(R.id.sp_filtro);
        String[] opciones = {"Ordenar(Por defecto)","Cercanía", "Calificación"};
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.filter_spinner_row, R.id.filter, opciones);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        sp_filtro.setAdapter(arrayAdapter);

        lvServices = findViewById(R.id.lvServices);
        lvServices.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdaptadorCliente(listaUsuarios);
        lvServices.setAdapter(adapter);
        adapter.setListener(this);

        lvServices.setHasFixedSize(true);

        sp_filtro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, final long id) {

                if(position==0){
                    buscarServicio();
                }
                else if(position==1){
                    HashMap<Usuario, Float> distances = new HashMap<Usuario, Float>();

                    buscarServicio();
                    for (Usuario usuario : listaUsuarios){
                        Location userLocation = new Location(LocationManager.NETWORK_PROVIDER);
                        userLocation.setLatitude(usuario.getLatitude());
                        userLocation.setLongitude(usuario.getLongitude());
                        float distance = myLocation.distanceTo(userLocation)/1000;
                        distances.put(usuario,distance);
                    }

                    HashMap<Usuario, Float> sortDistances = sortByValue(distances);
                    Iterator<Usuario> iterator = sortDistances.keySet().iterator();
                    listaUsuarios.clear();
                    while(iterator.hasNext()){
                        listaUsuarios.add(iterator.next());
                    }

                    adapter.notifyDataSetChanged();


                }else if (position==2){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_navigation = findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.menu_perfilUsuario) {

                    Intent i = new Intent(BuscarServicioActivity.this, PerfilClienteActivity.class);
                    startActivity(i);
                    finish();
                } else if (menuItem.getItemId() == R.id.menu_notificaciones) {
                    Intent i = new Intent(BuscarServicioActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });

    }

    private void obtenerValoresSolicitud() {
        Intent intent = getIntent();
        servicios_solicitados = (Map<String, Boolean>) intent.getSerializableExtra("servicios_solicitados");
        hora_solicitada = intent.getExtras().getInt("hora_solicitada");
        String strFecha = intent.getExtras().getString("fecha_solicitada");
        SimpleDateFormat format = new SimpleDateFormat("d MMM. yyyy", Locale.US);
        try {
            fecha_solicitada = format.parse(strFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (fecha_solicitada==null){
            format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            try {
                fecha_solicitada = format.parse(strFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void getMyLocation() {

        rtdb.getReference().child("usuarios").child("clientes").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = (Usuario) dataSnapshot.getValue(Usuario.class);
                myLocation.setLatitude(usuario.getLatitude());
                myLocation.setLongitude(usuario.getLongitude());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // function to sort hashmap by values
    public static HashMap<Usuario, Float> sortByValue(HashMap<Usuario, Float> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Usuario, Float> > list =
                new LinkedList<Map.Entry<Usuario, Float> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Usuario, Float> >() {
            public int compare(Map.Entry<Usuario, Float> o1,
                               Map.Entry<Usuario, Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Usuario, Float> temp = new LinkedHashMap<Usuario, Float>();
        for (Map.Entry<Usuario, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    private void buscarServicio() {

        rtdb.getReference().child("servicios_en_progreso").child("ofertado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Servicio servicio = (Servicio) objSnapshot.getValue(Servicio.class);

                    if (servicio.getTiposServicios().values().containsAll(servicios_solicitados.values()) &&
                            compararFechas(servicio.getFecha(), fecha_solicitada) && servicio.getHoraInicio() == hora_solicitada) {
                        final String userID = servicio.getId_colab();
                        Log.e(">>>", userID);
                        rtdb.getReference().child("usuarios").child("colaboradores").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.e(">>>","entró");
                                Usuario usuario = (Usuario) dataSnapshot.getValue(Usuario.class);
                                Log.e(">>>", usuario.getUid());
                                listaUsuarios.add(usuario);
                                Log.e(">>>",listaUsuarios.size()+"");
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean compararFechas(Date fecha_servicio, Date fecha_solicitada) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fecha_servicio);
        cal2.setTime(fecha_solicitada);
        boolean iguales = cal1.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR);
        return iguales;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //adapter.showAllAmigos(localdb.getAllAmigosOfUser(auth.getCurrentUser().getUid()));
    }


    @Override
    public void onItemClick(Usuario usuario) {
        rtdb.getReference().child("servicios_en_progreso").child("ofertado").child(usuario.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Servicio servicio=  (Servicio) dataSnapshot.getValue(Servicio.class);
                    servicio.setEstado("solicitado");
                    rtdb.getReference().child("servicios_en_progreso").child("solicitado").child(servicio.getId_colab()).setValue(servicio);
                    rtdb.getReference().child("servicios_en_progreso").child("ofertado").child(servicio.getId_colab()).removeValue();
                    Toast.makeText(BuscarServicioActivity.this, "Se ha solicitado el servicio correctamente", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BuscarServicioActivity.this, NotificacionClienteActivity.class);
                    i.putExtra("id_colab",servicio.getId_colab());
                    startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
