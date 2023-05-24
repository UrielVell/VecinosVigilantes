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
import com.example.vecinosvigilantes.vecino.dominio.UsuarioClass;
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

import java.util.HashMap;
import java.util.Map;

public class SeleccionMetodoEntradaGrupoActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference grupoRef= db.getInstance().getReference().child("Grupos");
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    //String id = mAuth.getCurrentUser().getUid();
    //String name = mAuth.getCurrentUser().toString();
    //String email = mAuth.getCurrentUser().getEmail();


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

    boolean exist;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getApplicationContext(), "Escaneo cancelado", Toast.LENGTH_LONG).show();
                } else {
                    DatabaseReference queryRef = grupoRef.child(result.getContents());
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            exist = snapshot.exists();
                            Toast.makeText(SeleccionMetodoEntradaGrupoActivity.this, ""+exist, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    if (exist){

                        String id = mAuth.getCurrentUser().getUid();
                        DatabaseReference miembros = grupoRef.child(result.getContents()).child("miembros");
                        Map<String, String> miembro = new HashMap<>();
                        miembro.put("id_usuario",id);
                        miembros.setValue(miembro);


                        Toast.makeText(getApplicationContext(), "Escaneado: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "El qr no es valido", Toast.LENGTH_SHORT).show();
                    }

                }
            });


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