package com.example.vecinosvigilantes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecinosvigilantes.vecino.aplicacion.activities.CompartirGrupoActivity;
import com.example.vecinosvigilantes.vecino.aplicacion.activities.SeleccionMetodoEntradaGrupoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GrupoFragment extends Fragment {
    private View root;

    FirebaseAuth firebaseAuth;

    DatabaseReference referenciaUsuario;

    DatabaseReference referenciaGrupo;

    TextView nomGrupo;
    ImageButton newGrup;
    ImageButton entrarGrupo;
    ImageButton btnAjustes;

    public GrupoFragment() {
        //requiere un constructor publico vacio
    }

    public static GrupoFragment newInstance() {
        GrupoFragment fragment = new GrupoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();

        root=inflater.inflate(R.layout.fragment_grupo, container,false);
        newGrup = root.findViewById(R.id.imageButtonCrearGrupo);
        entrarGrupo = root.findViewById(R.id.entrarAGrupoButton);
        btnAjustes = (ImageButton) root.findViewById(R.id.btnAjustesGrupo);
        nomGrupo = (TextView) root.findViewById(R.id.txtGrupo);

        String idUsuarioLog = firebaseAuth.getCurrentUser().getUid();

        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);

        verificarGrupo();

        newGrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulsa(root);
            }
        });



        entrarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SeleccionMetodoEntradaGrupoActivity.class);
                startActivity(intent);
            }
        });
        return root;

    }
    public void pulsa(View view){
        Intent intent=new Intent(getContext(),CrearGrupoActivity.class);
        startActivity(intent);
    }

    public void verificarGrupo(){
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id_grupo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    id_grupo = snapshot.child("id_grupo").getValue(String.class);
                }
                if (id_grupo.isEmpty()) {
                    btnAjustes.setVisibility(View.INVISIBLE);
                } else {
                    infoGrupo(id_grupo);
                    newGrup.setVisibility(View.INVISIBLE);
                    entrarGrupo.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void infoGrupo(String idGrupo){
        referenciaGrupo = FirebaseDatabase.getInstance().getReference("Grupos").child(idGrupo);

        referenciaGrupo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String nombreGrupo = snapshot.child("nombreGrupo").getValue(String.class);
                    nomGrupo.setTextSize(22);
                    nomGrupo.setText(nombreGrupo);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}