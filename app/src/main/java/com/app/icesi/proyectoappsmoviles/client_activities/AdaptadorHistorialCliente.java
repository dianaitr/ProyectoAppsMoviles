package com.app.icesi.proyectoappsmoviles.client_activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptadorHistorialCliente extends RecyclerView.Adapter<AdaptadorHistorialCliente.CustomViewHolder> {


    private List<Servicio> lista_servicios_terminados;
    FirebaseStorage storage;

    public AdaptadorHistorialCliente.OnItemClickListener getListener() {
        return listener;
    }

    private AdaptadorHistorialCliente.OnItemClickListener listener;

    FirebaseDatabase rtdb;
    FirebaseAuth auth;


    public AdaptadorHistorialCliente(ArrayList<Servicio> lista_servicios_terminados){
        this.lista_servicios_terminados=lista_servicios_terminados;
    }

    public AdaptadorHistorialCliente(){
        lista_servicios_terminados=new ArrayList<>();
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_historial_cliente, parent, false);
        AdaptadorHistorialCliente.CustomViewHolder vh = new AdaptadorHistorialCliente.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        rtdb.getReference().child("usuarios").child("colaboradores").child(lista_servicios_terminados.get(position).getId_colab()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario us = dataSnapshot.getValue(Usuario.class);
                        ((TextView) holder.root.findViewById(R.id.renglon_hist_cliente_nombre)).setText(us.getNombres()+" ");
                        ((TextView) holder.root.findViewById(R.id.renglon_hist_cliente_apellidos)).setText(us.getApellidos());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy ");

        ((TextView) holder.root.findViewById(R.id.txtFechaClienteHist)).setText(" "+dateFormat.format(lista_servicios_terminados.get(position).getFecha())+"");
        ((TextView) holder.root.findViewById(R.id.txtHoraClienteHist)).setText(" "+lista_servicios_terminados.get(position).getHoraInicio()+"");

    }

    @Override
    public int getItemCount() {
        return lista_servicios_terminados.size();
    }
    public void setListener(AdaptadorHistorialCliente.OnItemClickListener listener){
        this.listener = listener;
    }

    //OBSERVER
    public interface OnItemClickListener{
        void onItemClick(Servicio servicio);
    }




    public static class CustomViewHolder extends RecyclerView.ViewHolder
    {
            public LinearLayout root;
            public CustomViewHolder(LinearLayout v) {
                 super(v);
                 root = v;
         }
    }
}
