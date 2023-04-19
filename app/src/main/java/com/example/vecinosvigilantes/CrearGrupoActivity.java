package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearGrupoActivity extends AppCompatActivity {
    private FirebaseFirestore mfirestore;
    Button nombre_grupo;
    EditText nombreGrupo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        mfirestore=FirebaseFirestore.getInstance();
        nombreGrupo=findViewById(R.id.editTextTextPersonName);
        NuevoGrupo(nombreGrupo.getText().toString());

    }


    public void NuevoGrupo(String nombre){
        String nombreG=nombre;
        crearGrupo(nombreG);
    }
    public void crearGrupo( String nombregrupo) {
        Map <String, Object> map =new HashMap<>();
        map.put("Nombre", nombregrupo);
        mfirestore.collection("grupo").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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


}