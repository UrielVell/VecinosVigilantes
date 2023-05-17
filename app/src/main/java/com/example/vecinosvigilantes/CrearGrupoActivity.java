package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    SharedPreferences preferences;
    private boolean msj;
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


            //ponerlo con la key del miembro
            preferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = preferences.edit();// se crea un editos de shared reference

            verificarUsuarioEnGrupo(preferences.getString("GrupoUsuario",""));
            //referencia ala base de datos
                if(msj==false){
                    Toast.makeText(CrearGrupoActivity.this,"Este usuario ya tiene un grupo asignado",Toast.LENGTH_SHORT).show();
                }else if(msj==true){
                    DatabaseReference miDRef= FirebaseDatabase.getInstance().getReference().child("Grupos");
                    DatabaseReference nuevoGrupo=miDRef.push();
                    String key=nuevoGrupo.getKey();
                    nuevoGrupo.setValue(grupo1);
                    myEdit.putString("GrupoUsuario",key);
                    myEdit.commit();
                    String apodo=preferences.getString("GrupoUsuario","");
                    nombreGrupo.setText(apodo);
                    Toast.makeText(CrearGrupoActivity.this,"Grupo creado con exito:"+apodo,Toast.LENGTH_SHORT).show();
                }

            }
            {

            }

        }



    public void abreGaleria(View view){
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
      //  Uri path=intent.getData();
       // imj.setImageURI(path);
    }


    public  void verificarUsuarioEnGrupo(String id){
        referencia.child("Grupos").child("administrador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(id).exists()){
                msj=false;
                }else{

                }
                msj=false;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}



