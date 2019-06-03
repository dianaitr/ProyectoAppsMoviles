package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.LoginActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.client_activities.PerfilClienteActivity;
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

public class PerfilEmpleadoActivity extends AppCompatActivity {

    private TextView et_nombre_empleado, et_telefono_empleado,et_calificacion_empleado,et_apellidos_empleado;
    private Switch sw_estado_empleado;
    private Button btn_sign_out_empleado;


    private String userType;
    private GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;


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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
