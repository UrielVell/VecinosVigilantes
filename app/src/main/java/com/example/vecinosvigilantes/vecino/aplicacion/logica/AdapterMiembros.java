package com.example.vecinosvigilantes.vecino.aplicacion.logica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.dominio.AlertaClass;
import com.example.vecinosvigilantes.vecino.dominio.UsuarioClass;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMiembros extends RecyclerView.Adapter<AdapterMiembros.ViewHolder> {
    Context context;
    ImageView fotoUsuario;
    private HashMap listaMiembros;

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtNombre;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            fotoUsuario = itemView.findViewById(R.id.ImageViewFotoPerfil);
        }
    }

    public AdapterMiembros(Context context,HashMap listaAlertas){
        this.listaMiembros= listaAlertas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_miembros, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNombre.setText( listaMiembros.get("nombre").toString());
        Glide.with(context).load(listaMiembros.get("pp")).into(fotoUsuario);
    }


    @Override
    public int getItemCount() {
        return listaMiembros.size();
    }

}
