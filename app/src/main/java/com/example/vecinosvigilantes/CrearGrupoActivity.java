package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vecinosvigilantes.vecino.dominio.Grupo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CrearGrupoActivity extends AppCompatActivity {


    private FirebaseAuth autenticacion;
    private DatabaseReference referencia;
    DatabaseReference referenciaUsuario;
    private static final int GALLERY_INTENT=1;
    private EditText nombreGrupo=null;
    private ImageButton imj=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        autenticacion=FirebaseAuth.getInstance();
        referencia=FirebaseDatabase.getInstance().getReference();

    }

    public void crearGrupo(View view) {
        nombreGrupo = findViewById(R.id.editTextTextPersonName);
        if (nombreGrupo.getText().toString().isEmpty()){
            Toast.makeText(CrearGrupoActivity.this,"Valor de nombre no puede ser vacia",Toast.LENGTH_SHORT).show();
        }else {
            String newNombre = nombreGrupo.getText().toString();
            String idUsuarioLog = autenticacion.getCurrentUser().getUid();
            String urlImagen = "logo";
            Grupo grupo1 = new Grupo(newNombre, idUsuarioLog, urlImagen);

            referenciaUsuario=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(idUsuarioLog);
            //llamada al metodo de verificar grupo

            //el methodo if que esta comentado es el que verifica si ya tiene un grupo el ususario
            //verificarUsuarioEnGrupo(preferences.getString("GrupoUsuario",""));
            //referencia ala base de datos
            //if (!verificarUsuarioEnGrupo().equals("")) {
                Toast.makeText(CrearGrupoActivity.this, "Este usuario ya tiene un grupo asignado:", Toast.LENGTH_SHORT).show();
           // } else {
                DatabaseReference miDRef = FirebaseDatabase.getInstance().getReference().child("Grupos");
                DatabaseReference nuevoGrupo = miDRef.push();
                String key = nuevoGrupo.getKey();
                nuevoGrupo.setValue(grupo1);

                cambiarIdGrupo(idUsuarioLog,key);
                nombreGrupo.setText(null);
                Toast.makeText(CrearGrupoActivity.this, "Grupo creado con exito:" , Toast.LENGTH_SHORT).show();
          //  }

        }
        }



    public void abreGaleria(View view){
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
      //  Uri path=intent.getData();
       // imj.setImageURI(path);
    }

//este es el que no jala
    /*public  String verificarUsuarioEnGrupo() {
        final String[] id = new String[1];
       referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot data: snapshot.getChildren()){
                   String id_grupo=snapshot.child("id_grupo").getValue(String.class);
                   id[0] =id_grupo;
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
       return id[0];
    }*/



    public void cambiarIdGrupo(String id_usuario,String id_grupo){
        HashMap map=new HashMap();
        map.put("id_grupo",id_grupo);
        referenciaUsuario.updateChildren(map);
    }
}



