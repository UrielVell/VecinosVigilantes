package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class CrearGrupoActivity extends AppCompatActivity {


    private FirebaseAuth autenticacion;
    private DatabaseReference referencia;

    private static final int GALLERY_INTENT=1;
    private EditText nombreGrupo=null;
    private ImageButton imj=null;

    private int usuarioExiste;
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

            //referencia ala base de datos

            if (verificarUsuarioEnGrupo(idUsuarioLog)==0){
                Toast.makeText(CrearGrupoActivity.this,"Este usuario ya tiene un grupo asignado"+usuarioExiste,Toast.LENGTH_SHORT).show();
            } else if (verificarUsuarioEnGrupo(idUsuarioLog)==1) {
                DatabaseReference miDRef= FirebaseDatabase.getInstance().getReference().child("Grupos");
                DatabaseReference nuevoGrupo=miDRef.push();
                nuevoGrupo.setValue(grupo1);
                nombreGrupo.setText(null);
                Toast.makeText(CrearGrupoActivity.this,"Grupo creado con exito"+usuarioExiste,Toast.LENGTH_SHORT).show();
            }
            {

            }

        }

    }

    public void abreGaleria(View view){
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
      //  Uri path=intent.getData();
       // imj.setImageURI(path);
    }


    public  int verificarUsuarioEnGrupo(String id){
        referencia.child("Grupos").child("administrador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                String com =snapshot.getValue().toString();
                if(com.equals(id)){
                   usuarioExiste=0;
                }else{
                    usuarioExiste=1;
                }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return usuarioExiste;
    }
}



