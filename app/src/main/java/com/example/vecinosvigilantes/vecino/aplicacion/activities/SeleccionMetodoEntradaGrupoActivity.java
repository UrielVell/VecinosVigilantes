package com.example.vecinosvigilantes.vecino.aplicacion.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecinosvigilantes.R;
import com.example.vecinosvigilantes.vecino.aplicacion.logica.Captureactivityportrain;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class SeleccionMetodoEntradaGrupoActivity extends AppCompatActivity {

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getApplicationContext(), "Escaneo cancelado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Escaneado: " + result.getContents(), Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_metodo_entrada_grupo);
        scancode();
    }
    private void scancode(){
        ImageButton boton = (ImageButton) findViewById(R.id.imageButtonAbrirScanner);
        TextView text = (TextView) findViewById(R.id.textView);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escaner();
            }
        });
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