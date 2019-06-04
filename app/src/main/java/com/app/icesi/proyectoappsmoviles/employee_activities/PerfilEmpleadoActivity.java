package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.LoginActivity;
import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.MuroChatsEmpleadoActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.client_activities.NotificacionClienteActivity;
import com.app.icesi.proyectoappsmoviles.client_activities.PerfilClienteActivity;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class PerfilEmpleadoActivity extends AppCompatActivity {

    private TextView et_nombre_empleado, et_telefono_empleado,et_calificacion_empleado,et_apellidos_empleado;
    private Switch sw_estado_empleado;
    private Button btn_sign_out_empleado;


    private String userType;
    private GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    private BottomNavigationView btn_navigation;
    //stars
    private ImageView im_star_one_one;
    private ImageView im_star_one_two;
    private ImageView im_star_one_three;
    private ImageView im_star_one_four;
    private ImageView im_star_one_five;
    private ImageView employe_ima;

    private TextView tv_ubicaEMple;
    private TextView  tv_numeroCalificacionEmpleado;
    private TextView  tv_numeroTelEmpleado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_empleado);

        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();

        userType=getIntent().getExtras().getString("userType");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //Stars
        im_star_one_one = findViewById(R.id.im_star_one_one);
        im_star_one_two = findViewById(R.id.im_star_one_two);
        im_star_one_three = findViewById(R.id.im_star_one_three);
        im_star_one_four = findViewById(R.id.im_star_one_four);
        im_star_one_five = findViewById(R.id.im_star_one_five);

        employe_ima = findViewById(R.id.employe_ima);
        tv_ubicaEMple =findViewById(R.id.tv_ubicaEMple);
        tv_numeroCalificacionEmpleado= findViewById(R.id.tv_numeroCalificacionEmpleado);
        tv_numeroTelEmpleado=findViewById(R.id.tv_numeroTelEmpleado);

        btn_navigation=findViewById(R.id.menu_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(PerfilEmpleadoActivity.this, PerfilEmpleadoActivity.class);
                    i.putExtra("userType","employee");
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(PerfilEmpleadoActivity.this, NotificacionEmpleadoActivity.class);
                    i.putExtra("userType","employee");
                    startActivity(i);
                    finish();

                }else if(menuItem.getItemId()==R.id.menu_chat){
                    Intent i= new Intent(PerfilEmpleadoActivity.this, MuroChatsEmpleadoActivity.class);
                    startActivity(i);
                    i.putExtra("userType","employee");
                    finish();

                }

                return false;
            }
        });
        btn_sign_out_empleado = findViewById(R.id.btn_sign_out_empleado);
        btn_sign_out_empleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        et_nombre_empleado = findViewById(R.id.tv_nameEmpleado);
        et_telefono_empleado = findViewById(R.id.tv_numeroTelEmpleado);
        et_calificacion_empleado=findViewById(R.id.tv_numeroCalificacionEmpleado);
        et_apellidos_empleado=findViewById(R.id.tv_apellidoEmpleado);

        sw_estado_empleado = findViewById(R.id.sw_estadoEmpleado);
        sw_estado_empleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtdb.getReference().child("usuarios").child("colaboradores").child(auth.getCurrentUser().getUid()).child("estado").setValue(sw_estado_empleado.isChecked());
            }
        });




        llenarCampos();
    }

    private void llenarCampos() {
        rtdb.getReference().child("usuarios").child("colaboradores").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = (Usuario) dataSnapshot.getValue(Usuario.class);
                et_nombre_empleado.setText(usuario.getNombres());
                et_apellidos_empleado.setText(usuario.getApellidos());
                et_calificacion_empleado.setText(""+usuario.getCalificacion());
                et_telefono_empleado.setText(usuario.getTelefono());
                if(usuario.getTelefono()!=null) {
                    tv_numeroTelEmpleado.setText(usuario.getTelefono());
                }


                //search for locality
                double latitude = usuario.getLatitude();
                double longitude = usuario.getLongitude();
                if (latitude == 0.0 || longitude == 0.0) {
                    //entro por red social
                } else {
                    String nameLocation = getUbicacionByLatitudLongitude(latitude, longitude);
                    tv_ubicaEMple.setText(nameLocation);
                }

                double clientCalification = (int) usuario.getCalificacion();
                changeViewStars(clientCalification);
                tv_numeroCalificacionEmpleado.setText(clientCalification + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void changeViewStars(double clientCalification) {
        if (clientCalification < 1) {
            //no tiene estrellas
            im_star_one_one.setVisibility(View.INVISIBLE);
            im_star_one_two.setVisibility(View.INVISIBLE);
            im_star_one_three.setVisibility(View.INVISIBLE);
            im_star_one_four.setVisibility(View.INVISIBLE);
            im_star_one_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 1 && clientCalification < 2) {
            im_star_one_one.setVisibility(View.INVISIBLE);
            im_star_one_two.setVisibility(View.INVISIBLE);
            im_star_one_three.setVisibility(View.VISIBLE);
            im_star_one_four.setVisibility(View.INVISIBLE);
            im_star_one_five.setVisibility(View.INVISIBLE);

        } else if (clientCalification >= 2 && clientCalification < 3) {
            im_star_one_one.setVisibility(View.INVISIBLE);
            im_star_one_two.setVisibility(View.VISIBLE);
            im_star_one_three.setVisibility(View.INVISIBLE);
            im_star_one_four.setVisibility(View.VISIBLE);
            im_star_one_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 3 && clientCalification < 4) {
            im_star_one_one.setVisibility(View.INVISIBLE);
            im_star_one_two.setVisibility(View.VISIBLE);
            im_star_one_three.setVisibility(View.VISIBLE);
            im_star_one_four.setVisibility(View.VISIBLE);
            im_star_one_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 4 && clientCalification < 5) {
            im_star_one_one.setVisibility(View.INVISIBLE);
            im_star_one_two.setVisibility(View.VISIBLE);
            im_star_one_three.setVisibility(View.VISIBLE);
            im_star_one_four.setVisibility(View.VISIBLE);
            im_star_one_five.setVisibility(View.VISIBLE);
        } else if (clientCalification == 5) {
            im_star_one_one.setVisibility(View.VISIBLE);
            im_star_one_two.setVisibility(View.VISIBLE);
            im_star_one_three.setVisibility(View.VISIBLE);
            im_star_one_four.setVisibility(View.VISIBLE);
            im_star_one_five.setVisibility(View.VISIBLE);
        }
    }

    public String getUbicacionByLatitudLongitude(Double latitude, Double longitude) {

        String result = "";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            result=address+"";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void signOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(PerfilEmpleadoActivity.this, LoginActivity.class);
                        i.putExtra("userType",userType);
                        startActivity(i);
                        finish();


                    }
                });
    }
}
