package com.example.vecinosvigilantes.vecino.aplicacion.logica;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.dominio.AlertaClass;
import com.example.vecinosvigilantes.vecino.dominio.MiembroClass;
import com.example.vecinosvigilantes.vecino.dominio.UsuarioClass;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMiembros extends RecyclerView.Adapter<AdapterMiembros.ViewHolder> implements View.OnCreateContextMenuListener{
    Context context;
    ImageView fotoUsuario;
    ArrayList<MiembroClass> listaMiembros;

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Acciones");
        MenuItem admin = contextMenu.add(contextMenu.NONE,1,1,"Hacer Administrador");
        MenuItem eliminar = contextMenu.add(contextMenu.NONE,1,1,"Eliminar de Grupo");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtNombre;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            fotoUsuario = itemView.findViewById(R.id.ImageViewFotoPerfil);

        }
    }

    public AdapterMiembros(Context context,ArrayList<MiembroClass> listaMiembros){
        this.listaMiembros= (ArrayList<MiembroClass>) listaMiembros;
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
        MiembroClass miembros = listaMiembros.get(position);
        holder.txtNombre.setText( listaMiembros.get(position).getNombre());
        Glide.with(context).load(listaMiembros.get(position).getImagen_perfil()).into(fotoUsuario);
    }


    @Override
    public int getItemCount() {
        return listaMiembros.size();
    }

}
