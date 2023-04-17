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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

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
                    Toast.makeText(InicioSesion.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(InicioSesion.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(InicioSesion.this, PantallaInicio.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(InicioSesion.this, "Usuario o Contraseña Incorrestos", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}