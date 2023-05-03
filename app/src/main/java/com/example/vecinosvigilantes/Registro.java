package com.example.vecinosvigilantes;

import static com.google.firebase.FirebaseError.ERROR_EMAIL_ALREADY_IN_USE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vecinosvigilantes.vecino.dominio.UsuarioClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private StorageReference pp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pp = FirebaseStorage.getInstance().getReference();


        Button buttonRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        EditText nombreUsuario = (EditText) findViewById(R.id.nombreUsuario);
        EditText correoUsuario = (EditText) findViewById(R.id.correoUsuario);
        EditText contraUsuario = (EditText) findViewById(R.id.contraUsuario);



        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = nombreUsuario.getText().toString().trim();
                String correo = correoUsuario.getText().toString().trim();
                String contrasena = contraUsuario.getText().toString().trim();

                if (usuario.isEmpty()&&correo.isEmpty()&&contrasena.isEmpty()){
                    Toast.makeText(Registro.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }
                else if (contrasena.length()<6) {
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                else {
                        Registrarse(usuario, correo, contrasena);
                    }

            }
        });
    }

    public void irInicioSesion(View view){
        finish();
    }



    public void Registrarse(String usuario, String correo, String contrasena){

        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pp.child("fotosPerfil/ojo.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri ppic = uri;
                                    String ppuser = ppic.toString();
                                    UsuarioClass usuarioNuevo = new UsuarioClass(usuario,correo,ppuser);
                                    String id = mAuth.getCurrentUser().getUid();
                                    mDatabase.child("Usuarios").child(id).setValue(usuarioNuevo);

                                    Toast.makeText(Registro.this, "Registro Exitoso.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Registro.this, IniciarSesionActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registro.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });


                            //updateUI(user);
                        } else{
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            erro(errorCode);
                        }
                    }
                });
    }

    public void erro(String errorCode) {

        switch (errorCode) {
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Registro.this, "Email ya registrado.   ", Toast.LENGTH_LONG).show();
                break;

        }

    }

    public void CrearGrupo(String nomGrupo){

    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}