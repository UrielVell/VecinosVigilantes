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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GrupoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GrupoFragment extends Fragment {

    View root;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GrupoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GrupoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GrupoFragment newInstance(String param1, String param2) {
        GrupoFragment fragment = new GrupoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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