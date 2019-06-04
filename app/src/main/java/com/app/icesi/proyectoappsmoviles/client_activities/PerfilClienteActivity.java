package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.LoginActivity;
import com.app.icesi.proyectoappsmoviles.MuroChatsClienteActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Locale;

public class PerfilClienteActivity extends AppCompatActivity {

    FirebaseDatabase rtdb;
    FirebaseStorage storage;
    FirebaseAuth auth;
    private Usuario me;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final int CAMERA_CALLBACK_ID = 100;
    private static final int GALLERY_CALLBACK_ID = 101;


    private BottomNavigationView btn_navigation;
    private ImageView person1_imagen;
    private TextView tv_nameCliente;
    private TextView tv_apellidosCliente;
    private TextView tv_ubicacionCliente;
    private TextView tv_calificacion_perfilCliente;
    private TextView tv_ayudaCliente;


    private Button btn_historialCliente;
    private Button btn_nuevoServicioCliente;
    private Button btn_sign_out;

    //stars
    private ImageView imv_star_one;
    private ImageView imv_star_two;
    private ImageView imv_star_three;
    private ImageView imv_star_four;
    private ImageView imv_star_five;

    private ImageView imv_perfilCliente;

    private String typeUser;

    private Switch sw_activo;
    private Button bt_servicios;
    private Button bt_disponibilidad;
    private Button bt_historial;
    private Button bt_ayuda;
    private Button btn_take_pic;
    private Button btn_open_gal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);


        //mapeo de xml
        tv_nameCliente = findViewById(R.id.tv_nameCliente);
        tv_apellidosCliente = findViewById(R.id.tv_apellidosCliente);
        tv_ubicacionCliente = findViewById(R.id.tv_ubicacionCliente);
        tv_calificacion_perfilCliente = findViewById(R.id.tv_calificacion_perfilCliente);


        //Stars
        imv_star_one = findViewById(R.id.imv_star_one);
        imv_star_two = findViewById(R.id.imv_star_two);
        imv_star_three = findViewById(R.id.imv_star_three);
        imv_star_four = findViewById(R.id.imv_star_four);
        imv_star_five = findViewById(R.id.imv_star_five);

        imv_perfilCliente = findViewById(R.id.imv_perfilCliente);


        btn_take_pic = findViewById(R.id.btn_take_pic);
        btn_open_gal = findViewById(R.id.btn_open_gal);

        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_historialCliente = findViewById(R.id.btn_historialCliente);
        btn_historialCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilClienteActivity.this, HistorialClienteActivity.class);
                startActivity(i);
            }
        });
        btn_nuevoServicioCliente = findViewById(R.id.btn_nuevoServicioCliente);
        tv_ayudaCliente = findViewById(R.id.tv_ayudaCliente);

        btn_navigation = findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.menu_perfilUsuario) {

                    Intent i = new Intent(PerfilClienteActivity.this, PerfilClienteActivity.class);
                    i.putExtra("userType", "client");
                    startActivity(i);
                    finish();
                } else if (menuItem.getItemId() == R.id.menu_notificaciones) {
                    Intent i = new Intent(PerfilClienteActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                } else if (menuItem.getItemId() == R.id.menu_chat) {
                    Intent i = new Intent(PerfilClienteActivity.this, MuroChatsClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });

        btn_nuevoServicioCliente = findViewById(R.id.btn_nuevoServicioCliente);
        btn_nuevoServicioCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilClienteActivity.this, CreacionBuscarServicioActivity.class);
                startActivity(i);
            }
        });
        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        me = new Usuario();

        typeUser = getIntent().getExtras().getString("userType");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // tv_user = findViewById(R.id.tv_user);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        //Si no hay usuario loggeado
        if (auth.getCurrentUser() == null) {

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();

            return;
        }

        // TODO Inicializar los componentes gráficos

        llenarPerfil();
    }

    private void llenarPerfil() {
      /*  StorageReference ref = storage.getReference().child("profiles").child(auth.getCurrentUser().getUid());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(person1_imagen);
            }
        });*/

        rtdb.getReference().child("usuarios").child("clientes").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Usuario.class);


                tv_nameCliente.setText(me.getNombres());
                tv_apellidosCliente.setText(me.getApellidos());
                //search for locality
                double latitude = me.getLatitude();
                double longitude = me.getLongitude();
                if (latitude == 0.0 || longitude == 0.0) {
                    //entro por red social
                } else {
                    String nameLocation = getUbicacionByLatitudLongitude(latitude, longitude);
                    tv_ubicacionCliente.setText(nameLocation);
                }

                double clientCalification = (int) me.getCalificacion();
                changeViewStars(clientCalification);
                tv_calificacion_perfilCliente.setText(clientCalification + "");
                //sw_activo.setChecked(me.isActivo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void changeViewStars(double clientCalification) {
        if (clientCalification < 1) {
            //no tiene estrellas
            imv_star_one.setVisibility(View.INVISIBLE);
            imv_star_two.setVisibility(View.INVISIBLE);
            imv_star_three.setVisibility(View.INVISIBLE);
            imv_star_four.setVisibility(View.INVISIBLE);
            imv_star_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 1 && clientCalification < 2) {
            imv_star_one.setVisibility(View.INVISIBLE);
            imv_star_two.setVisibility(View.INVISIBLE);
            imv_star_three.setVisibility(View.VISIBLE);
            imv_star_four.setVisibility(View.INVISIBLE);
            imv_star_five.setVisibility(View.INVISIBLE);

        } else if (clientCalification >= 2 && clientCalification < 3) {
            imv_star_one.setVisibility(View.INVISIBLE);
            imv_star_two.setVisibility(View.VISIBLE);
            imv_star_three.setVisibility(View.INVISIBLE);
            imv_star_four.setVisibility(View.VISIBLE);
            imv_star_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 3 && clientCalification < 4) {
            imv_star_one.setVisibility(View.INVISIBLE);
            imv_star_two.setVisibility(View.VISIBLE);
            imv_star_three.setVisibility(View.VISIBLE);
            imv_star_four.setVisibility(View.VISIBLE);
            imv_star_five.setVisibility(View.INVISIBLE);
        } else if (clientCalification >= 4 && clientCalification < 5) {
            imv_star_one.setVisibility(View.INVISIBLE);
            imv_star_two.setVisibility(View.VISIBLE);
            imv_star_three.setVisibility(View.VISIBLE);
            imv_star_four.setVisibility(View.VISIBLE);
            imv_star_five.setVisibility(View.VISIBLE);
        } else if (clientCalification == 5) {
            imv_star_one.setVisibility(View.VISIBLE);
            imv_star_two.setVisibility(View.VISIBLE);
            imv_star_three.setVisibility(View.VISIBLE);
            imv_star_four.setVisibility(View.VISIBLE);
            imv_star_five.setVisibility(View.VISIBLE);
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


    ///////////////metodos autenticacion google

    private void signOut() {
        // Firebase sign out
        auth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(PerfilClienteActivity.this, LoginActivity.class);
                        i.putExtra("userType", typeUser);
                        startActivity(i);
                        finish();


                    }
                });
    }
}
