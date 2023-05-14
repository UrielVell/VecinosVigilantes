package com.example.vecinosvigilantes;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.vecino.aplicacion.logica.DialogCambiarNombre;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    StorageReference storageReference;

    private static final int GALLERY_INTENT = 1;

    ImageView fotoPerfil;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView txtUsuario =  root.findViewById(R.id.txtPerfil);
        TextView nomUsuarioLog = root.findViewById(R.id.nombreUsuarioLog);
        ImageButton btnCerrarSesion = (ImageButton) root.findViewById(R.id.btnCerrarSesion);
        ImageButton btnCambiarNombre = (ImageButton) root.findViewById(R.id.btnCambiarNombre);
        ImageButton btnCambiarFoto = (ImageButton) root.findViewById(R.id.btnCambiarFoto);
        fotoPerfil = (ImageView) root.findViewById(R.id.imagePerfil);

        String usuarioLog = firebaseAuth.getCurrentUser().getEmail();
        String idUsuarioLog = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);


        txtUsuario.setText("Usuario logeado " + usuarioLog);

        cargarInfoUsuario(fotoPerfil, nomUsuarioLog);



        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Toast.makeText(getContext(), "Sesion Cerrada con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogo();

            }
        });

        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });


        // Inflate the layout for this fragment
        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
                Uri uri = data.getData();

                StorageReference rutaFotos = storageReference.child("fotosPerfil/").child(uri.getLastPathSegment());

                rutaFotos.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Foto cambiada", Toast.LENGTH_SHORT).show();
                        storageReference.child("fotosPerfil").child(uri.getLastPathSegment()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri ppic = uri;
                                String newppuser = ppic.toString();
                                HashMap map = new HashMap();
                                map.put("pp", newppuser);
                                databaseReference.updateChildren(map);
                                Glide.with(getContext()).load(newppuser).into(fotoPerfil);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al cambiar la imagen", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }


    public void cargarInfoUsuario(ImageView fotoPerfil, TextView nombreUsLog){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String pp = snapshot.child("pp").getValue(String.class);
                    String nombre = snapshot.child("nombre").getValue(String.class);
                    Glide.with(getContext()).load(pp).into(fotoPerfil);
                    nombreUsLog.setText(nombre);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void abrirDialogo(){
        DialogCambiarNombre dialog = new DialogCambiarNombre();
        dialog.show(getParentFragmentManager(),"Cambiar nombre");
    }
}