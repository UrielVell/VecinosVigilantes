package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearGrupoActivity extends AppCompatActivity {
    private FirebaseFirestore mAuth;
    private DatabaseReference mDatabase;

    Button nombre_grupo;
    EditText nombreGrupo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        mAuth=FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void crearGrupo(View view) {
        nombreGrupo=findViewById(R.id.editTextTextPersonName);
        String newNombre=nombreGrupo.getText().toString();
        Map <String, Object> map =new HashMap<>();
        map.put("Nombre", newNombre);
        mAuth.collection("grupo").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Toast.makeText(getApplicationContext(),"Error al crear grupo", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /////

    /*public void nuevoGrupo2(String nombre){

        mAuth.createGrupoWithName(nombre)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map <String, Object> map = new HashMap<>();
                            map.put("nomgreGrupo", nombre);
                            String id = mAuth.getCurrentGroup().getUid();
                            mDatabase.child("Usuarios").child(id).setValue(map);

                            Toast.makeText(Registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Registro.this, IniciarSesionActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else{
                            Toast.makeText(Registro.this, "No se pudo registrar, intente de nuevo.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }*/
    ////

    public void crearGrupo2(View view){
        Toast.makeText(getApplicationContext(),"prueba",Toast.LENGTH_SHORT).show();
    }





}