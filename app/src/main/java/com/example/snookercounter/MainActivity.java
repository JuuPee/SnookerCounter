package com.example.snookercounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        final Button peli, huipputulokset, pelipaikat;

        peli = findViewById(R.id.uusiPeli);
        peli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uusiPeli();
            }
        });

        huipputulokset = findViewById(R.id.huippuTulokset);
        huipputulokset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huipputulokset();
            }
        });

        pelipaikat = findViewById(R.id.peliPaikat);
        pelipaikat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pelipaikat();
            }
        });
    }
    public void uusiPeli() {
        Intent intent = new Intent(this, LaskuriActivity.class);
        startActivity(intent);
    }

    public void huipputulokset() {
        Intent intent = new Intent(this, HuipputuloksetActivity.class);
        startActivity(intent);
    }

    public void pelipaikat() {
        Intent intent = new Intent(this, PelipaikatActivity.class);
        startActivity(intent);

    }
}