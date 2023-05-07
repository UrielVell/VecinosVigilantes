package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vecinosvigilantes.vecino.dominio.Grupo;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearGrupoActivity extends AppCompatActivity {


    private FirebaseAuth autenticacion;

    private StorageReference reference;
    private static final int GALLERY_INTENT=1;
    EditText nombreGrupo=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        autenticacion=FirebaseAuth.getInstance();
        reference= FirebaseStorage.getInstance().getReference();

    }

    public void crearGrupo(View view) {
        nombreGrupo = findViewById(R.id.editTextTextPersonName);
        String newNombre = nombreGrupo.getText().toString();
        String idUsuarioLog = autenticacion.getCurrentUser().getUid();
        ArrayList<String> lista = new ArrayList<String>();
        String urlImagen = "logo";
        Grupo grupo1 = new Grupo(newNombre, idUsuarioLog, urlImagen);
        //referencia ala base de datos
        DatabaseReference miDRef= FirebaseDatabase.getInstance().getReference().child("Grupos");
        DatabaseReference miDRef2= FirebaseDatabase.getInstance().getReference().child("Grupos").child("mienbros");
        DatabaseReference nuevoGrupo=miDRef.push();
        nuevoGrupo.setValue(grupo1);


    }

    public void abreGaleria(View view){
        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GALLERY_INTENT && requestCode == RESULT_OK){

            Uri uri=data.getData();

            StorageReference file =reference.child("fotos").child(uri.getLastPathSegment());

            file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CrearGrupoActivity.this,"se subió exitosamente la foto", Toast.LENGTH_LONG).show();
                }

            });
        }
    }
}



