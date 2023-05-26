package com.example.vecinosvigilantes.vecino.aplicacion.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vecinosvigilantes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;

public class CompartirGrupoActivity extends AppCompatActivity {
    //private final String GrupoUsuario = sh.getString("GrupoUsuario", "");
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference grupoRef;
    private String grupoUsuario;
    //private Query query = grupoRef.orderByChild("nombre").equalTo(GrupoUsuario);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_grupo);
        setReferences();
        ImageButton regresar = (ImageButton) findViewById(R.id.regresarGruposbtn);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setReferences(){
        System.out.println(mAuth.getCurrentUser().getUid());
        DatabaseReference grupoU = db.getReference().child("Usuarios").child(mAuth.getCurrentUser().getUid()).child("id_grupo");
        grupoU.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    /*
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        grupoUsuario = dataSnapshot.getValue().toString();
                        System.out.println(dataSnapshot.getValue().toString());
                    }*/
                    grupoUsuario = snapshot.getValue().toString();
                    printQR(grupoUsuario);
                } else {
                    // No se encontró el nodo "id_grupo" para el usuario
                    Toast.makeText(CompartirGrupoActivity.this, "Error: el usuario no tiene un grupo asignado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompartirGrupoActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void printQR(String value){
        ImageView qrImage = (ImageView) findViewById(R.id.imageViewQR);
        String url = "https://chart.googleapis.com/chart?cht=qr&chs=350x350&chl="+value+"&choe=UTF-8";
        Glide.with(getApplicationContext()).load(url).into(qrImage);
    }
}