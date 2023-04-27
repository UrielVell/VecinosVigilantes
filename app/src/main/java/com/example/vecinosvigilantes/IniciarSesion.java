package com.example.vecinosvigilantes;

import static com.google.firebase.FirebaseError.ERROR_EMAIL_ALREADY_IN_USE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        EditText correoUsuarioInicio = (EditText) findViewById(R.id.correoUsuarioInicio);
        EditText contraUsuarioInicio = (EditText) findViewById(R.id.contraUsuarioInicio);

        Button btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);



        mAuth = FirebaseAuth.getInstance();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoUsuario = correoUsuarioInicio.getText().toString().trim();
                String contraUsuario = contraUsuarioInicio.getText().toString().trim();

                if (correoUsuario.isEmpty()&&contraUsuario.isEmpty()){
                    Toast.makeText(IniciarSesion.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }else {
                    IniciarSesion(correoUsuario,contraUsuario);
                }
            }
        });

    }

    public void irRegistro(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void IniciarSesion(String correoUsuario, String contraUsuario){
        mAuth.signInWithEmailAndPassword(correoUsuario,contraUsuario )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(IniciarSesion.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(IniciarSesion.this, InicioAppActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(IniciarSesion.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),InicioAppActivity.class));
        }
    }
}