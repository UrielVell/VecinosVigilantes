package com.example.vecinosvigilantes;

import androidx.activity.OnBackPressedDispatcher;
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
    ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        btnAtras = (ImageButton) findViewById(R.id.btnAtrasCrearGrupo);
        autenticacion=FirebaseAuth.getInstance();
        referencia=FirebaseDatabase.getInstance().getReference();


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void extraeInfoGrupo(View view) {
        nombreGrupo = findViewById(R.id.editTextTextPersonName);
        if (nombreGrupo.getText().toString().isEmpty()){
            Toast.makeText(CrearGrupoActivity.this,"Valor de nombre no puede ser vacia",Toast.LENGTH_SHORT).show();
        }else {
            String newNombre = nombreGrupo.getText().toString();
            String idUsuarioLog = autenticacion.getCurrentUser().getUid();
            String urlImagen = "";
            Grupo grupo1 = new Grupo(newNombre, idUsuarioLog, urlImagen);
            referenciaUsuario=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(idUsuarioLog);
            verificarGrupo(idUsuarioLog, grupo1);

        }
        }

    private void creaGrupo(Grupo grupo1,String idUsuarioLog) {
        DatabaseReference miDRef = FirebaseDatabase.getInstance().getReference().child("Grupos");
        DatabaseReference nuevoGrupo = miDRef.push();
        String key = nuevoGrupo.getKey();
        nuevoGrupo.setValue(grupo1);
        cambiarIdGrupo(idUsuarioLog,key);
        nombreGrupo.setText(null);
        Toast.makeText(CrearGrupoActivity.this, "Grupo creado con exito:" , Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void verificarGrupo(String id, Grupo grupo1){
        //llamada al metodo de verificar grupo
        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(id);
        referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id_grupo = null;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    id_grupo = snapshot.child("id_grupo").getValue(String.class);
                }
                if(id_grupo.isEmpty()){
                    creaGrupo(grupo1,id);
                } else if (!id_grupo.isEmpty()) {
                    Toast.makeText(CrearGrupoActivity.this, "Este ususario ya tiene un grupo", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void cambiarIdGrupo(String id_usuario,String id_grupo){
        HashMap map=new HashMap();
        map.put("id_grupo",id_grupo);
        referenciaUsuario.updateChildren(map);
    }


    public void SalirGrupo(){
        String idUsuarioLog = autenticacion.getCurrentUser().getUid();
        referenciaUsuario = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog).child("id_grupo");
        String idGtupoUs=referenciaUsuario.toString();
    }



}



