package com.example.vecinosvigilantes.vecino.aplicacion.logica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.vecinosvigilantes.AlertasFragment;

public class PowerButtonReceiver extends BroadcastReceiver {
    private static final long TIME_INTERVAL = 3000; // 3 segundos
    private static final int MAX_CLICKS = 3;

    private int clickCount = 0;
    private long lastClickTime = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        long currentTime = System.currentTimeMillis();

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            if (currentTime - lastClickTime > TIME_INTERVAL) {
                clickCount = 0;
            }
            clickCount++;
            lastClickTime = currentTime;

            if (clickCount >= MAX_CLICKS) {
                sendAlarm(context);
            }
        }
    }
    private AlertasFragment alertasFragment;

    public void setAlertasFragment(AlertasFragment alertasFragment) {
        this.alertasFragment = alertasFragment;
    }

    private void sendAlarm(Context context) {
        alertasFragment.mandarAlerta("¡Emergencia!");
        Toast.makeText(context, "Alarma enviada", Toast.LENGTH_SHORT).show();
    }
}
