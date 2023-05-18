package com.example.vecinosvigilantes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AlertasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
    / * @param param1 Parameter 1.
    / * @param param2 Parameter 2.
     * @return A new instance of fragment AlertasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlertasFragment newInstance() {
        AlertasFragment fragment = new AlertasFragment();
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
        // alerta
        View root = inflater.inflate(R.layout.fragment_alertas2, container, false);
        ImageButton alertaVecino = root.findViewById(R.id.imageButton);
        ImageButton alertaSospechoso = root.findViewById(R.id.imageButton2);
        alertaVecino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mostrarDialogoAlerta();
            }
        });
        alertaSospechoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoSospechoso();
            }
        });

        return root;
    }

    private void mostrarDialogoAlerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alerta");
        builder.setMessage("Estan asaltando a un vecino, urge ir a ayudarlo")
                .setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(false)
                .show();

    }
    private void mostrarDialogoSospechoso(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alerta");
        builder.setMessage("Hay un sospechoso en la calle, estar al pendiente por cualquier cosa")
                .setPositiveButton("Enterado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(false)
                .show();
    }


}