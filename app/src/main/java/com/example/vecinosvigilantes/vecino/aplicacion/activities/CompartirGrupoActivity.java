package com.example.vecinosvigilantes.vecino.aplicacion.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vecinosvigilantes.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.bumptech.glide.Glide;

public class CompartirGrupoActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference grupoRef = db.getReference("Grupos");
    Query query = grupoRef.orderByChild("nombre").equalTo("User.Grup");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir_grupo);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String value = snapshot.getValue().toString();
                    ImageView qrImage = (ImageView) findViewById(R.id.imageViewQR);
                    String url = "https://chart.googleapis.com/chart?cht=qr&chs=350x350&chl="+value+"&choe=UTF-8";
                    Glide.with(getApplicationContext()).load(url).into(qrImage);
                }else {
                    Toast.makeText(CompartirGrupoActivity.this, "Error el usuario no esta en ningùn grupo", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompartirGrupoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton regresar = (ImageButton) findViewById(R.id.regresarGruposbtn);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}