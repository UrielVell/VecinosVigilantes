package com.example.vecinosvigilantes.vecino.aplicacion.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.aplicacion.logica.Captureactivityportrain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SeleccionMetodoEntradaGrupoActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference grupoRef= db.getInstance().getReference().child("Grupos");
    FirebaseAuth mAuth;
    //String id = mAuth.getCurrentUser().getUid();
    //String name = mAuth.getCurrentUser().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_metodo_entrada_grupo);
        scancode();
    }
    private void scancode(){
        ImageButton boton = (ImageButton) findViewById(R.id.imageButtonAbrirScanner);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escaner();
            }
        });
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getApplicationContext(), "Escaneo cancelado", Toast.LENGTH_SHORT).show();
                } else {
                    if (existOnDatabase(result.getContents())){
                        String email = mAuth.getCurrentUser().toString();
                        DatabaseReference grupoUsuario = db.getInstance().getReference().child("Grupos").child(result.getContents());
                        DatabaseReference miembros = grupoUsuario.child("miembros");
                        DatabaseReference nuevoMiembro = miembros.push();
                        nuevoMiembro.setValue(email);

                        //el grupo del usuario se guarda en un SharedPreferences para mayor acceso
                        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putString("GrupoUsuario",grupoUsuario.getKey());
                        myEdit.commit();
                        Toast.makeText(getApplicationContext(), "Escaneado: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "El qr no es valido", Toast.LENGTH_SHORT).show();
                    }

                }
            });

    boolean exist = false;
    public boolean existOnDatabase(String key){
        grupoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot temp: snapshot.getChildren()) {
                    if(temp.getKey().equals(key)){
                        exist = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return exist;
    }
    private void escaner(){
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES);
        options.setCameraId(0);
        options.setPrompt("Escanea un qr valido");
        options.setOrientationLocked(false);
        options.setBeepEnabled(true);
        options.setCaptureActivity(Captureactivityportrain.class);
        options.setBarcodeImageEnabled(false);

        barcodeLauncher.launch(options);
    }

}