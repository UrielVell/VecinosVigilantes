package com.example.vecinosvigilantes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.vecinosvigilantes.vecino.aplicacion.activities.CompartirGrupoActivity;
import com.example.vecinosvigilantes.vecino.aplicacion.activities.SeleccionMetodoEntradaGrupoActivity;
import com.google.firebase.auth.FirebaseAuth;

public class GrupoFragment extends Fragment {
    private View root;

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
        root=inflater.inflate(R.layout.fragment_grupo, container,false);
        ImageButton newGrup = root.findViewById(R.id.imageButtonCrearGrupo);

        newGrup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pulsa(root);
            }
        });

        ImageButton entrarGrupo = root.findViewById(R.id.entrarAGrupoButton);

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




}