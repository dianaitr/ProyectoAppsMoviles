package com.app.icesi.proyectoappsmoviles;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {

    private String colab_id;
    private String me_id;
    FirebaseDatabase rtdb;
    FirebaseAuth auth;
    String me ;

    String type_user;
    String idChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        colab_id=getIntent().getExtras().getString("colabSelected");

        if(type_user.equals("clientes")){
            rtdb.getReference().child("usuarios").child("clientes").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Usuario me = dataSnapshot.getValue(Usuario.class);
                            me_id=me.getUid();

                            initChat();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }
            );
        }else{
           // rtdb.getReference().child("usuarios").child("colaboradores");
            //TODO
        }

    }

    private void initChat() {
        rtdb.getReference().child("chats").child(me_id).child(colab_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot==null){

                }else{
                    //TODO
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
