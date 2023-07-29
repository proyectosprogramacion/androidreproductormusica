package com.example.reproductor_musica_versionfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class EleccionReproduccion extends AppCompatActivity implements View.OnClickListener {

    //Declaramos las variables de los botones que usaremos
    Button btReproduccionRaw, btReproduccionListView, btGrabacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_reproduccion);

        //Verificamos que en Manifest estan los permisos para escribir dentro del dispositivo y grabar audio
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //Muestra las ventanas emergentes para aceptar los permisos de escritura y lectura de audio y grabación de audio
            ActivityCompat.requestPermissions(EleccionReproduccion.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);

        }

        /*Any app that declares the WRITE_EXTERNAL_STORAGE permission is implicitly granted this permission.
        This permission is enforced starting in API level 19. Before API level 19, this permission is not enforced
        and all apps still have access to read from external storage.
        You can test your app with the permission enforced by enabling Protect USB
        storage under Developer options in the Settings app on a device running Android 4.1 or higher. */

        /* Si solo queremos pedir permisos de lectura ---Da error si luego queremos solicitar otros permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        */

        //Hilo para apagar la app en caso de que no se acepten los permisos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    mensaje();
                    finishAffinity();
                }
            }
        }, 5000);

        //Asociamos cada boton con su parte gráfica
        btReproduccionListView = findViewById(R.id.btReproduccionListView);
        btReproduccionListView.setOnClickListener(this);

        btReproduccionRaw = findViewById(R.id.btReproduccionRaw);
        btReproduccionRaw.setOnClickListener(this);

        btGrabacion = findViewById(R.id.btGrabacion);
        btGrabacion.setOnClickListener(this);

    }

    public void mensaje(){
        Toast.makeText(this, "Debes aceptar los permisos para utilizar la aplicación.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btReproduccionListView:
                Intent intent1 = new Intent(this, ReproduccionListView.class);
                startActivity(intent1);
                break;

            case R.id.btReproduccionRaw:
                Intent intent2 = new Intent(this, ReproduccionCarpetaRaw.class);
                startActivity(intent2);
                break;
            case R.id.btGrabacion:
                Intent intent3 = new Intent(this, GrabacionAudio.class);
                startActivity(intent3);
                break;

            default:
                break;
        }

    }

    //Metodo para controlar el botón back de la barra inferior de la pantalla
    @Override
    public void onBackPressed(){
        //Si lo dejamos sin nada, no funcionará
    }

}
