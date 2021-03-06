package com.app.icesi.proyectoappsmoviles.client_activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Servicio;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AdaptadorNotificacionCliente extends RecyclerView.Adapter<AdaptadorNotificacionCliente.CustomViewHolder> {


    private List<Servicio> lista_servicios_solicitados;
    FirebaseStorage storage;

    public AdaptadorNotificacionCliente.OnItemClickListener getListener() {
        return listener;
    }

    private AdaptadorNotificacionCliente.OnItemClickListener listener;

    FirebaseDatabase rtdb;
    FirebaseAuth auth;


    public AdaptadorNotificacionCliente(ArrayList<Servicio> lista_servicios_solicitados){
        this.lista_servicios_solicitados = lista_servicios_solicitados;
    }

    public AdaptadorNotificacionCliente(){
        lista_servicios_solicitados =new ArrayList<>();
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_notificacion, parent, false);
        AdaptadorNotificacionCliente.CustomViewHolder vh = new AdaptadorNotificacionCliente.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        rtdb.getReference().child("usuarios").child("colaboradores").child(lista_servicios_solicitados.get(position).getId_colab()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario us = dataSnapshot.getValue(Usuario.class);
                        ((TextView) holder.root.findViewById(R.id.renglon_noti_empleado_nombre)).setText(us.getNombres()+" ");
                        ((TextView) holder.root.findViewById(R.id.renglon_noti_empleado_apellidos)).setText(us.getApellidos());

                        String serviciosSolicitados="";

                        HashMap<String,Boolean> tiposServicios = lista_servicios_solicitados.get(position).getTiposServicios();
                        Iterator<String> iterator = tiposServicios.keySet().iterator();


                        while(iterator.hasNext()){
                            String a=iterator.next();
                            if(tiposServicios.get(a)==true){
                                serviciosSolicitados+=a+" , ";
                            }

                        }

                        ((TextView) holder.root.findViewById(R.id.txtServiciosEmpleadoNoti)).setText(serviciosSolicitados);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy ");

        ((TextView) holder.root.findViewById(R.id.txtFechaEmpleadoNoti)).setText(" "+dateFormat.format(lista_servicios_solicitados.get(position).getFecha())+"");
        ((TextView) holder.root.findViewById(R.id.txtHoraEmpleadoNoti)).setText(" "+ lista_servicios_solicitados.get(position).getHoraInicio()+"");


    }

    @Override
    public int getItemCount() {
        return lista_servicios_solicitados.size();
    }
    public void setListener(AdaptadorNotificacionCliente.OnItemClickListener listener){
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
