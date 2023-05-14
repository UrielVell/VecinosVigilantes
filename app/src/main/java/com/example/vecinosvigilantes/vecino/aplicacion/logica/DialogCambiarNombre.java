package com.example.vecinosvigilantes.vecino.aplicacion.logica;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.vecinosvigilantes.PerfilFragment;
import com.example.vecinosvigilantes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DialogCambiarNombre extends AppCompatDialogFragment {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    EditText txtNuevoNombre;
    EditText txtUsuarioLog;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_editar_nombre, null);
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View view2 = inflater2.inflate(R.layout.fragment_perfil, null);

        firebaseAuth = FirebaseAuth.getInstance();

        txtNuevoNombre = view.findViewById(R.id.txtNuevoNombre);
        txtUsuarioLog = view2.findViewById(R.id.nombreUsuarioLog);
        String idUsuarioLog = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);

        builder.setView(view).setTitle("Cambiar nombre").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newNombre = txtNuevoNombre.getText().toString().trim();
                if (!newNombre.isEmpty()) {
                    HashMap map = new HashMap();
                    map.put("nombre", newNombre);
                    databaseReference.updateChildren(map);
                    txtUsuarioLog.setText(newNombre);
                    Toast.makeText(getContext(), "Nombre Cambiado", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(getContext(), "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return builder.create();
    }
}
