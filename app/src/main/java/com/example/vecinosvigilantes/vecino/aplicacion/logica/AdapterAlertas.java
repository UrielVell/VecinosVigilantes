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

import java.util.ArrayList;
import java.util.List;

public class AdapterAlertas extends RecyclerView.Adapter<AdapterAlertas.ViewHolder> {

    Context context;
    ImageView fotoUsuario;
    private ArrayList<AlertaClass> listaAlertas;

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView txtTipoAlerta;
        TextView txtFecha;
        TextView txtHora;
        TextView txtUbicacion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoUsuario = itemView.findViewById(R.id.imgUsuarioAlerta);
            txtTipoAlerta = itemView.findViewById(R.id.txtTipoAlerta);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtHora = itemView.findViewById(R.id.txtHora);
            txtUbicacion = itemView.findViewById(R.id.txtUbicacion);
        }
    }


    public AdapterAlertas(Context context, ArrayList<AlertaClass> listaAlertas){
        this.listaAlertas= (ArrayList<AlertaClass>) listaAlertas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_alerta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlertaClass alertas = listaAlertas.get(position);
        holder.txtTipoAlerta.setText( listaAlertas.get(position).getTipoAlarma());
        Glide.with(context).load(listaAlertas.get(position).getPpUsuario()).into(fotoUsuario);
        holder.txtFecha.setText(listaAlertas.get(position).getFecha());
        holder.txtHora.setText(listaAlertas.get(position).getHora());
        holder.txtUbicacion.setText(listaAlertas.get(position).getUbicacion());
    }


    @Override
    public int getItemCount() {
        return listaAlertas.size();
    }


}
