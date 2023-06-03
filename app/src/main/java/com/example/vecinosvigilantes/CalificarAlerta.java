package com.example.vecinosvigilantes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CalificarAlerta extends AppCompatActivity {

    TextView rateCount, showRating;
    Button submit;
    RatingBar ratingBar;
    float rateValue;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_alerta);
        ratingBar = findViewById(R.id.ratingBar);
        submit = findViewById(R.id.btnEnviar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();
                if(rateValue<=1 && rateValue>0){

                }else if(rateValue<=2 && rateValue>1){

                }else if(rateValue<=3 && rateValue>2){

                }else if(rateValue<=4 && rateValue>3){

                }else if(rateValue<=5 && rateValue>4){

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CalificarAlerta.this, "Alerta Calificada", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
}