package com.app.icesi.proyectoappsmoviles.client_activities;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.CustomViewHolder> {

    ArrayList<Usuario> data;
    FirebaseStorage storage;

    public AdaptadorCliente(ArrayList<Usuario> listaUsuarios){
        this.data=listaUsuarios;
    }

    public void showAllAmigos(ArrayList<Usuario> allUsuarios) {
        for(int i = 0 ; i<allUsuarios.size() ; i++){
            if(!data.contains(allUsuarios.get(i))) data.add(allUsuarios.get(i));
        }
        notifyDataSetChanged();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdaptadorCliente(){
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_buscar_servicios, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.renglon_nombreEmpleado)).setText(data.get(position).getNombres());
        ((TextView) holder.root.findViewById(R.id.renglon_apellidoEmpleado)).setText(data.get(position).getApellidos());
        holder.root.findViewById(R.id.renglon_empleado).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });
        ((TextView) holder.root.findViewById(R.id.renglon_calEmpleado)).setText(data.get(position).getCalificacion()+"");

        storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child("usuarios").child("colaboradores").child(data.get(position).getUid());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView img = holder.root.findViewById(R.id.renglon_imagenEmpleado);
                Glide.with(holder.root.getContext()).load(uri).into(img);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //OBSERVER
    public interface OnItemClickListener{
        void onItemClick(Usuario usuario);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
