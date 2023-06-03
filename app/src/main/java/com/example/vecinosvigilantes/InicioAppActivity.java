package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.vecinosvigilantes.vecino.aplicacion.activities.SeleccionMetodoEntradaGrupoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicioAppActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    PerfilFragment perfilFragment = new PerfilFragment();
    AlertasFragment alertasFragment = new AlertasFragment();
    GrupoFragment grupoFragment = new GrupoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.alertas);

    }
    public void abrirPantallaMapa(View view){
        Intent intent = new Intent(getApplicationContext(), MapasUsuarios.class);
        startActivity(intent);
    }

    public void abrirPantallaCalificar(View view){
        Intent intent = new Intent(getApplicationContext(),CalificarAlerta.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alertas:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, alertasFragment).commit();
                return true;
            case R.id.perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, perfilFragment).commit();
                return true;
            case R.id.grupo:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, grupoFragment).commit();
                return true;
        }
        return false;
    }
}