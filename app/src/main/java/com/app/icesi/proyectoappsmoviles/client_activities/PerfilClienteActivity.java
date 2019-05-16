package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
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

public class PerfilClienteActivity extends AppCompatActivity {

    FirebaseDatabase rtdb;
    FirebaseStorage storage;
    FirebaseAuth auth;
    private Usuario me;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    private BottomNavigationView btn_navigation;
    private ImageView person1_imagen;
    private TextView tv_nameCliente;
    private TextView tv_localidadCliente;
    private TextView tv_calificacionCliente;
    private TextView tv_ayudaCliente;


    private Button btn_historialCliente;
    private Button btn_nuevoServicioCliente;
    private Button btn_sign_out;

    private String typeUser;

    private Switch sw_activo;
    private Button bt_servicios;
    private Button bt_disponibilidad;
    private Button bt_historial;
    private Button bt_ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);



        //mapeo de xml
        person1_imagen=findViewById(R.id.person1_imagen);
        tv_nameCliente=findViewById(R.id.tv_nameCliente);
        tv_localidadCliente=findViewById(R.id.tv_localidadCliente);
        tv_calificacionCliente=findViewById(R.id.tv_nameCliente);
        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_historialCliente=findViewById(R.id.btn_historialCliente);
        btn_nuevoServicioCliente=findViewById(R.id.btn_nuevoServicioCliente);
        tv_ayudaCliente=findViewById(R.id.tv_ayudaCliente);

        btn_navigation=findViewById(R.id.btn_navigation);
        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.menu_perfilUsuario){

                    Intent i= new Intent(PerfilClienteActivity.this, PerfilClienteActivity.class);
                    startActivity(i);
                    finish();
                }else if(menuItem.getItemId()==R.id.menu_notificaciones){
                    Intent i= new Intent(PerfilClienteActivity.this, NotificacionClienteActivity.class);
                    startActivity(i);
                    finish();

                }

                return false;
            }
        });

        btn_nuevoServicioCliente=findViewById(R.id.btn_nuevoServicioCliente);
        btn_nuevoServicioCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(PerfilClienteActivity.this, BuscarServicioActivity.class );
            }
        });
        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        me =new Usuario();

        typeUser=getIntent().getExtras().getString("userType");

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
        StorageReference ref = storage.getReference().child("profiles").child(auth.getCurrentUser().getUid());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(person1_imagen);
            }
        });

        rtdb.getReference().child("usuarios").child("colaboradores").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Usuario.class);
                tv_nameCliente.setText(me.getNombres());
                tv_localidadCliente.setText(me.getUbicacion()+"");
                tv_calificacionCliente.setText(me.getCalificacion()+"");
                sw_activo.setChecked(me.isActivo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                        i.putExtra("userType",typeUser);
                        startActivity(i);
                        finish();


                    }
                });
    }
}
