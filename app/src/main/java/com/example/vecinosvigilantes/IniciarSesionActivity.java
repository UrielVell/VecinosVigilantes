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

public class IniciarSesionActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(this, InicioAppActivity.class);
            startActivity(intent);
        }
    }

    public void iniciarRegistroActivity(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    public void iniciarInicioAppActivity(){
        Intent intent = new Intent(this, InicioAppActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion(View view){
        EditText correoUsuarioInicio = (EditText) findViewById(R.id.correoUsuarioInicio);
        String correoUsuario = correoUsuarioInicio.getText().toString().trim();
        EditText contraUsuarioInicio = (EditText) findViewById(R.id.contraUsuarioInicio);
        String contraUsuario = contraUsuarioInicio.getText().toString().trim();

        if(validarDatos(correoUsuario, contraUsuario)){
            mAuth.signInWithEmailAndPassword(correoUsuario,contraUsuario )
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(IniciarSesionActivity.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                iniciarInicioAppActivity();

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(IniciarSesionActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }

    private boolean validarDatos(String correo, String contrasenia) {
        boolean valido = true;
        String mensaje = null;

        if(correo.isEmpty()){
            valido = false;
            mensaje = "Campo correo vacío. ";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        if(contrasenia.isEmpty()){
            valido = false;
            mensaje = "Campo contraseña vacío.";
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }

        return valido;
    }
}