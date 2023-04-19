package com.example.vecinosvigilantes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class InicioApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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