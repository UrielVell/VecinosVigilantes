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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AlertasFragment extends Fragment {

    FirebaseAuth firebaseAuth;

    DatabaseReference referenciaUsuario;




    public AlertasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();


        View root = inflater.inflate(R.layout.fragment_alertas2, container, false);

        ImageButton btnEmergencia = (ImageButton) root.findViewById(R.id.btnEmergencia);
        ImageButton btnSospechoso = (ImageButton) root.findViewById(R.id.btnSospechoso);

        String idUsuarioLog = firebaseAuth.getCurrentUser().getUid();


        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);

       btnEmergencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
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
                           Toast.makeText(getContext(), "ID:" + id_grupo, Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });



           }
       });





        return root;
    }

    public void mandarAlerta(){

    }
}