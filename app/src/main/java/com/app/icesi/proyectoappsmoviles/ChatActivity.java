package com.app.icesi.proyectoappsmoviles;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.model.Mensaje;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private String userSelected_id;
    private String me_id;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;
    String me ;

    String type_user;
    String idChat;

    private EditText et_mensaje_chat;
    private Button btn_enviar_chat;
    private TextView txt_mensajes;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        et_mensaje_chat=findViewById(R.id.et_mensaje_chat);
        btn_enviar_chat=findViewById(R.id.btn_enviar_chat);
        txt_mensajes=findViewById(R.id.txt_mensajes);
        txt_mensajes.setMovementMethod(new ScrollingMovementMethod());


        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        userSelected_id ="";
        type_user=getIntent().getExtras().getString("typeUser");



        if(!type_user.equals("")){

            if(type_user.equals("colaboradores")){
                userSelected_id =getIntent().getExtras().getString("clienteSelected");
            }else if(type_user.equals("clientes")){
                userSelected_id =getIntent().getExtras().getString("colabSelected");
            }
            rtdb.getReference().child("usuarios").child(type_user).child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Usuario me = dataSnapshot.getValue(Usuario.class);
                            me_id=me.getUid();
                            nombre = me.getNombres();
                            initChat();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }
            );
        }else{
            //TODO
        }

    }

    private void initChat() {
        rtdb.getReference().child("chat").child(me_id).child(userSelected_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){

                    String pushID=rtdb.getReference().child("chat").child(me_id).child(userSelected_id)
                            .push().getKey();
                    //creacion ramas genericas
                    rtdb.getReference().child("chat").child(me_id).child(userSelected_id).setValue(pushID);
                    rtdb.getReference().child("chat").child(userSelected_id).child(me_id).setValue(pushID);
                    idChat=pushID;

                }else{
                    idChat= dataSnapshot.getValue(String.class);
                }
                activarListenerBoton();
                cargarMensajes();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarMensajes() {

        rtdb.getReference().child("mensajes").child(idChat).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Carga todos los hijos de la rama y queda pendiente de los nuevos
                //que se agregan
                txt_mensajes.append(dataSnapshot.getValue(Mensaje.class).nombre+"\n"+
                        dataSnapshot.getValue(Mensaje.class).contenido+"\n\n");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void activarListenerBoton() {
        btn_enviar_chat.setVisibility(View.VISIBLE);
        btn_enviar_chat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //envio de mensajes
                String mensaje= et_mensaje_chat.getText().toString();
                Mensaje m = new Mensaje(nombre, mensaje);
                rtdb.getReference().child("mensajes").child(idChat).push().setValue(m);
            }
        });
    }
}
