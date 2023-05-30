package com.example.vecinosvigilantes.administrador.aplicacion.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.aplicacion.activities.CompartirGrupoActivity;
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

public class InfoGrupoActivity extends AppCompatActivity {

    private FirebaseAuth autenticacion;
    FirebaseAuth auth;

    DatabaseReference referenciaUsuario;
    DatabaseReference referenciaGrupo;
    ImageView fotoGrupo;
    EditText txtNombreGrupo;

    ImageButton btnCambiarFotoGrupo;
    ImageButton btnCambiarNombreGrupo;
    ImageButton btnCompartir;
    ImageButton btnEliminarGrupo;
    ImageButton btnSalirGrupo;
    StorageReference storageReference;
    public String id_Grupo;
    public  String idUsuarioLog;
    private static final int GALLERY_INTENT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        ImageButton btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        btnCambiarFotoGrupo = (ImageButton) findViewById(R.id.btnCambiarFotoGrupo);
        btnCambiarNombreGrupo = (ImageButton) findViewById(R.id.btnCambiarNombreGrupo);
        btnCompartir = (ImageButton) findViewById(R.id.btnCompartir);
        btnEliminarGrupo = (ImageButton) findViewById(R.id.btnEliminarGrupo);
        btnSalirGrupo = (ImageButton) findViewById(R.id.btnSalirGrupo);
        fotoGrupo = (ImageView) findViewById(R.id.imgFotoGrupo);
        txtNombreGrupo = (EditText) findViewById(R.id.txtNombreGrupo);
        storageReference= FirebaseStorage.getInstance().getReference();
        autenticacion=FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        idUsuarioLog=auth.getCurrentUser().getUid();
        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);
        referenciaGrupo = FirebaseDatabase.getInstance().getReference("Grupos");
        buscarGrupo(idUsuarioLog);


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompartirGrupoActivity.class);
                startActivity(intent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSalirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salirGrupo();

            }
        });
    }

    //salir grupo
   public void salirGrupo(){

        String refId_grupo=id_Grupo;
        DatabaseReference referenciaAdmin=FirebaseDatabase.getInstance().getReference("Grupos")
                .child(refId_grupo).child("administrador");


       DatabaseReference referenciaGrupo2=FirebaseDatabase.getInstance().getReference("Grupos")
               .child(refId_grupo).child("miembros").child(idUsuarioLog);

       DatabaseReference referenceUsDelete=FirebaseDatabase.getInstance().
               getReference("Usuarios").child(idUsuarioLog);

        referenciaAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String accs=snapshot.getValue(String.class);
                if(accs.equals(idUsuarioLog)){
                    Toast.makeText(InfoGrupoActivity.this, "Necesitas asignar a otra persona como administrador para poder salir"
                            , Toast.LENGTH_SHORT).show();
                }else{
                  //Elimina el usuario del grupo
                  referenciaGrupo2.removeValue();


                    HashMap map = new HashMap();
                    String vacio="";
                    map.put("id_grupo", vacio);
                    referenceUsDelete.updateChildren(map);

                    Toast.makeText(InfoGrupoActivity.this, "usuario eliminado", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //referenciaGrupo2.removeValue();
        //Toast.makeText(InfoGrupoActivity.this, refId_grupo, Toast.LENGTH_SHORT).show();
    }



    //////
/*String refId_grupo=id_Grupo;


        referenciaGrupo2.removeValue();
        Toast.makeText(InfoGrupoActivity.this, refId_grupo, Toast.LENGTH_SHORT).show();*/

    ////


    public void abrirGaleria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            Uri uri = data.getData();

            StorageReference rutaFotos = storageReference.child("fotosGrupo/").child(uri.getLastPathSegment());

            rutaFotos.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Foto cambiada", Toast.LENGTH_SHORT).show();
                    storageReference.child("fotosGrupo").child(uri.getLastPathSegment()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri ppic = uri;
                            String newppuser = ppic.toString();
                            HashMap map = new HashMap();
                            map.put("logo", newppuser);
                            referenciaGrupo.child(id_Grupo).updateChildren(map);
                            Glide.with(getApplicationContext()).load(newppuser).into(fotoGrupo);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al cambiar la imagen", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void buscarGrupo(String idUsuario){
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            String idGrupo;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   idGrupo = snapshot.child("id_grupo").getValue(String.class);
                }
                id_Grupo=idGrupo;
                cargarInfoGrupo(idGrupo,fotoGrupo,txtNombreGrupo,idUsuario);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void cargarInfoGrupo(String idGrupo, ImageView foto, EditText nombre, String idUsuario){
        referenciaGrupo.child(idGrupo).addListenerForSingleValueEvent(new ValueEventListener() {
            String nombreGrupo;
            String fotoGrupo;
            String admin;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    admin = snapshot.child("administrador").getValue(String.class);
                    nombreGrupo = snapshot.child("nombreGrupo").getValue(String.class);
                    fotoGrupo = snapshot.child("logo").getValue(String.class);
                }
                nombre.setText(nombreGrupo);
                Glide.with(getApplicationContext()).load(fotoGrupo).into(foto);
                if (!admin.equals(idUsuario)){
                    btnCambiarFotoGrupo.setVisibility(View.INVISIBLE);
                    btnCompartir.setVisibility(View.INVISIBLE);
                    btnCambiarNombreGrupo.setVisibility(View.INVISIBLE);
                    btnEliminarGrupo.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}