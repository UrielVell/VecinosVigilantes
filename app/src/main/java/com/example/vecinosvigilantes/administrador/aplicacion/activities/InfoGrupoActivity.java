package com.example.vecinosvigilantes.administrador.aplicacion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.aplicacion.activities.CompartirGrupoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoGrupoActivity extends AppCompatActivity {

    FirebaseAuth auth;

    DatabaseReference referenciaUsuario;
    DatabaseReference refernciaGrupo;
    ImageView fotoGrupo;
    EditText txtNombreGrupo;

    ImageButton btnCambiarFotoGrupo;
    ImageButton btnCambiarNombreGrupo;
    ImageButton btnCompartir;
    ImageButton btnEliminarGrupo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        ImageButton btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnCambiarFotoGrupo = (ImageButton) findViewById(R.id.btnCambiarFotoGrupo);
        btnCambiarNombreGrupo = (ImageButton) findViewById(R.id.btnCambiarNombreGrupo);
        btnCompartir = (ImageButton) findViewById(R.id.btnCompartir);
        ImageButton btnSalirGrupo = (ImageButton) findViewById(R.id.btnSalirGrupo);
        btnEliminarGrupo = (ImageButton) findViewById(R.id.btnEliminarGrupo);
        fotoGrupo = (ImageView) findViewById(R.id.imgFotoGrupo);
        txtNombreGrupo = (EditText) findViewById(R.id.txtNombreGrupo);





        auth = FirebaseAuth.getInstance();

        String idUsuarioLog = auth.getCurrentUser().getUid();
        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);
        refernciaGrupo = FirebaseDatabase.getInstance().getReference("Grupos");

        buscarGrupo(idUsuarioLog);


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompartirGrupoActivity.class);
                startActivity(intent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void buscarGrupo(String idUsuario){
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            String idGrupo;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   idGrupo = snapshot.child("id_grupo").getValue(String.class);
                }
                cargarInfoGrupo(idGrupo,fotoGrupo,txtNombreGrupo,idUsuario);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cargarInfoGrupo(String idGrupo, ImageView foto, EditText nombre, String idUsuario){
        refernciaGrupo.child(idGrupo).addListenerForSingleValueEvent(new ValueEventListener() {
            String nombreGrupo;
            String fotoGrupo;
            String admin;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    admin = snapshot.child("administrador").getValue(String.class);
                    nombreGrupo = snapshot.child("nombreGrupo").getValue(String.class);
                    fotoGrupo = snapshot.child("logo").getValue(String.class);
                }
                nombre.setText(nombreGrupo);
                Glide.with(getApplicationContext()).load(fotoGrupo).into(foto);
                if (!admin.equals(idUsuario)){
                    btnCambiarFotoGrupo.setVisibility(View.INVISIBLE);
                    btnCompartir.setVisibility(View.INVISIBLE);
                    btnCambiarNombreGrupo.setVisibility(View.INVISIBLE);
                    btnEliminarGrupo.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}