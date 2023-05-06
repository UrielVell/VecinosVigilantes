package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegistroActivity extends AppCompatActivity {
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
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void irInicioSesion(View view) {
        finish();
    }


    public void registrarse(View view) {
        EditText nombreUsuario = (EditText) findViewById(R.id.nombreUsuario);
        String usuario = nombreUsuario.getText().toString().trim();
        EditText correoUsuario = (EditText) findViewById(R.id.correoUsuario);
        String correo = correoUsuario.getText().toString().trim();
        EditText contraUsuario = (EditText) findViewById(R.id.contraUsuario);
        String contrasena = contraUsuario.getText().toString().trim();

        if (validarDatos(usuario, correo, contrasena)) {
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
                                        UsuarioClass usuarioNuevo = new UsuarioClass(usuario, correo, ppuser);
                                        String id = mAuth.getCurrentUser().getUid();
                                        mDatabase.child("Usuarios").child(id).setValue(usuarioNuevo);

                                        irAIniciarSesionActivity();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //updateUI(user);
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                error(errorCode);
                            }
                        }
                    });
        }
    }

    private void irAIniciarSesionActivity() {
        Toast.makeText(RegistroActivity.this, "Registro Exitoso.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegistroActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
    }

    private boolean validarDatos(String usuario, String correo, String contrasena) {
        boolean valido = true;
        String mensaje = null;

        if (usuario.isEmpty()) {
            valido = false;
            mensaje = "Campo usuario vacío. ";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        if (correo.isEmpty()) {
            valido = false;
            mensaje = "Campo correo vacío.";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        if (contrasena.isEmpty()) {
            valido = false;
            mensaje = "Campo contraseña vacío.";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        return valido;
    }

    public void error(String errorCode) {
        switch (errorCode) {
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegistroActivity.this, "Email ya registrado.   ", Toast.LENGTH_LONG).show();
                break;
        }
    }
}