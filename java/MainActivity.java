package com.example.reproductor_musica_versionfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void pantallaBienvenida(View view) {
        //Log para pobar que hacemos click y ponerlo en los métodos para ver que funcionan
        //Log.i("ImageButton", "Clicleckd");
        Intent intent = new Intent(this, EleccionReproduccion.class);
        startActivity(intent);
    }
}
