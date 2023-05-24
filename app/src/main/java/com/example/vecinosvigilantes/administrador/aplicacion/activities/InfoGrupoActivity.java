package com.example.vecinosvigilantes.administrador.aplicacion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.aplicacion.activities.CompartirGrupoActivity;

public class InfoGrupoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);

        ImageButton btnAtras = (ImageButton) findViewById(R.id.btnAtras);
        ImageButton btnCambiarFotoGrupo = (ImageButton) findViewById(R.id.btnCambiarFotoGrupo);
        ImageButton btnCambiarNombreGrupo = (ImageButton) findViewById(R.id.btnCambiarNombreGrupo);
        ImageButton btnCompartir = (ImageButton) findViewById(R.id.btnCompartir);
        ImageButton btnSalirGrupo = (ImageButton) findViewById(R.id.btnSalirGrupo);
        ImageButton btnEliminarGrupo = (ImageButton) findViewById(R.id.btnEliminarGrupo);

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompartirGrupoActivity.class);
                startActivity(intent);
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }
}