package com.example.reproductor_musica_version5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class GrabacionAudio extends AppCompatActivity {

    private MediaRecorder grabacion;
    private String archivograbado = null;
    private Button btGrabar;
    int i =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabacion_audio);
        btGrabar = findViewById(R.id.btGrabar);

    }

    public void Grabar(View view) {
        if(grabacion == null){//Es decir si no se esta grabando nada
            //Le indicamos el nombre del archivo que queremos que utilice

            String nombreAudio="Grabación" + i +".mp3";
            archivograbado = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombreAudio;
            i++;//Cada audio se grabará con un nombre diferente

            //creamos el objeto MediaRecorder
            grabacion = new MediaRecorder();
            //Capturamos el audio
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);//Cuando quiero utilizar como sensor el microfono ponemos MIC
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//Formato de salida
            //Transformamos el audio
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivograbado);

            try{
                grabacion.prepare(); //Preparamos el objeto para empezar a graba
                grabacion.start();
            }catch (IOException e){


            }
            btGrabar.setBackgroundResource(R.drawable.pausa);//Cambiamos la apariencia del boton
            Toast.makeText(this, "Grabando.....", Toast.LENGTH_SHORT).show();

        }else if(grabacion != null){//Si se esta grabando usando el objeto grabación
            grabacion.stop();//paramos la grabación
            grabacion.release();//la pasamos a un estado finalizado

            //Devolvemos el objeto al estado null
            grabacion = null;
            btGrabar.setBackgroundResource(R.drawable.microfono);//Cambiamos la apariencia del boton a microfono otra vez
            Toast.makeText(this, "Grabacion finalizada.", Toast.LENGTH_SHORT).show();

        }


    }

    public void Reproducir (View view){
        //Creamos un objeto mediaPlayer
        MediaPlayer mediaPlayer = new MediaPlayer();

        //Por si no se reproduce
        try{
            mediaPlayer.setDataSource(archivograbado);
            mediaPlayer.prepare();//preparamos el audio
        }catch (IOException e){

        }

        mediaPlayer.start();
        Toast.makeText(this, "Reproduciendo audio.", Toast.LENGTH_SHORT).show();
    }
}
