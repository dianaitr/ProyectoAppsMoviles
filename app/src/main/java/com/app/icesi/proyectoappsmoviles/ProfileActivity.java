package com.app.icesi.proyectoappsmoviles;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.db.DBHandler;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase rtdb;
    FirebaseStorage storage;
    FirebaseAuth auth;
    private Usuario me;

    private ImageView iv_foto;
    private TextView tv_nombre;
    private TextView tv_apellidos;
    private TextView tv_telefono;
    private TextView tv_calificacion;

    private Switch sw_activo;
    private Button bt_servicios;
    private Button bt_disponibilidad;
    private Button bt_historial;
    private Button bt_ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

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
                Glide.with(getApplicationContext()).load(uri).into(iv_foto);
            }
        });

        rtdb.getReference().child("usuarios").child("colaboradores").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Usuario.class);
                tv_nombre.setText(me.getNombres());
                tv_apellidos.setText(me.getApellidos());
                tv_telefono.setText(me.getTelefono());
                tv_calificacion.setText(me.getCalificacion() + "");
                sw_activo.setChecked(me.isActivo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
