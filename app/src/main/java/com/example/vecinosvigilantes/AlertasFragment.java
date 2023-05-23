package com.example.vecinosvigilantes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vecinosvigilantes.vecino.dominio.AlertaClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class AlertasFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference referenciaUsuario;
    DatabaseReference referenciaGrupo;
    String idUsuarioLog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        String alertaE = "¡Emergencia!";
        String alertaS = "¡Sospechoso!";

        View root = inflater.inflate(R.layout.fragment_alertas2, container, false);

        ImageButton btnEmergencia = (ImageButton) root.findViewById(R.id.btnEmergencia);
        ImageButton btnSospechoso = (ImageButton) root.findViewById(R.id.btnSospechoso);

        idUsuarioLog = firebaseAuth.getCurrentUser().getUid();


        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);
        referenciaGrupo = FirebaseDatabase.getInstance().getReference("Grupos");

       btnEmergencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              verificarGrupo(alertaE);
           }
       });

       btnSospechoso.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               verificarGrupo(alertaS);
           }
       });
        return root;
    }

    public void verificarGrupo(String tipoAlerta){
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id_grupo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    id_grupo = snapshot.child("id_grupo").getValue(String.class);
                }
                if (id_grupo.isEmpty()) {
                    Toast.makeText(getContext(), "Debes de unirte a un grupo para mandar alertas", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getContext(), "ID:" + id_grupo, Toast.LENGTH_SHORT).show();
                    mandarAlerta(tipoAlerta);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void mandarAlerta(String tipoAlerta){
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id_grupo=null;
                String pp2 = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     id_grupo = snapshot.child("id_grupo").getValue(String.class);
                     pp2 = snapshot.child("pp").getValue(String.class);
                }
                String fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                TimeZone myTimeZone = TimeZone.getTimeZone("America/Mexico_City");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                simpleDateFormat.setTimeZone(myTimeZone);
                String hora = simpleDateFormat.format(new Date());
                String ubicacion = "Cerca";
                AlertaClass alerta = new AlertaClass(tipoAlerta,idUsuarioLog,pp2,fecha,hora,ubicacion);
                referenciaGrupo.child(id_grupo).child("Alertas").push().setValue(alerta);
                referenciaUsuario.child("AlertasGeneradas").push().setValue(alerta);
                Toast.makeText(getContext(), "Alerta enviada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al enviar alerta", Toast.LENGTH_SHORT).show();
            }
        });


    }
}