package com.example.vecinosvigilantes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AlertasFragment extends Fragment {
    public AlertasFragment() {
        // Required empty public constructor
    }

    public static AlertasFragment newInstance() {
        AlertasFragment fragment = new AlertasFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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