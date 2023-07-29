package com.example.reproductor_musica_versionfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Onclick_ReproduccionSDCARD extends AppCompatActivity {

    Button btPlay, btNext, btPrev, btAdelante, btRebobinar;
    ImageView ivPortada;
    TextView txtCancion,txtStart,txtStop;
    SeekBar skBarra;

    //declaro un objeto mediaPlaner
    MediaPlayer mediaPlayer;


    //variables para recoger los datos enviados del intent
    int position;
    ArrayList <File> archivos;
    ArrayList<File> nombreCanciones;
    String nombreCancion;

    //Files rutas
    File ruta_memoria_interna_Xiaomi;
    File ruta_memoria_interna_Resto;
    File ruta_debo_Utilizar;

    Thread updateSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_reproduccion_sdcard);

        //Conectamos con la parte gráfica
        btPlay = findViewById(R.id.btPlay);
        btNext = findViewById(R.id.btNext);
        btPrev = findViewById(R.id.btPrev);
        btAdelante = findViewById(R.id.btAdelante);
        btRebobinar = findViewById(R.id.btRebobinar);
        ivPortada = findViewById(R.id.ivPortada);
        txtCancion = findViewById(R.id.txtCancion);
        txtStart = findViewById(R.id.txtStart);
        txtStop = findViewById(R.id.txtStop);
        skBarra = findViewById(R.id.skBarra);


//Si se esta reproduciendo paramos la música
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        //Recuperamos los datos de la intent anterior
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        archivos = (ArrayList) bundle.getParcelableArrayList("songs");
        nombreCanciones = (ArrayList) bundle.getParcelableArrayList("songname");
        position = bundle.getInt("pos", 0);

        System.out.println(archivos.get(position));
        System.out.println(nombreCanciones.get(position));
        System.out.println(position);


        nombreCancion = String.valueOf(nombreCanciones.get(position));
        //Mostramos el nombre de la cancion
        txtCancion.setSelected(true);
        txtCancion.setText(nombreCancion);



        if(mediaPlayer == null){
            //No tengo que verificar si existe o no, ya que para llegar a esta pantalla, la carpeta tiene que existir siempre
            ruta_memoria_interna_Resto = new File("/storage/");
            File[] files_directory_music = ruta_memoria_interna_Resto.listFiles();
            System.out.println("Ruta devuelta" + files_directory_music[0].toString());

            ruta_debo_Utilizar = new File(files_directory_music[0].toString() + "/Music/");
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(ruta_debo_Utilizar +"/"+ String.valueOf(archivos.get(position))));
        System.out.println(ruta_debo_Utilizar + "/"+ String.valueOf(archivos.get(position)));
        mediaPlayer.start();



        updateSeekbar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentposition = 0;

                while (currentposition< totalDuration){
                    try {
                        sleep(50000);
                        currentposition = mediaPlayer.getCurrentPosition();
                        skBarra.setProgress(currentposition);
                    }catch (InterruptedException | IllegalStateException e){
                        e.printStackTrace();
                    }
                }

            }
        };

        skBarra.setMax(mediaPlayer.getDuration());
        updateSeekbar.start();
        skBarra.getProgressDrawable().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.MULTIPLY);
        skBarra.getThumb().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.SRC_IN);

        skBarra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        String tiempoFinal = mostrarTiempos(mediaPlayer.getDuration());
        txtStop.setText(tiempoFinal);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String tiempoActual= mostrarTiempos(mediaPlayer.getCurrentPosition());
                txtStop.setText(tiempoActual);
                handler.postDelayed(this,delay);
            }
        }, delay);


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    btPlay.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }else{
                    btPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                }
            }
        });

        //next listview
        //implementar el detector de finalización para la instancia de mediaPlayer y libere los recursos de audio.
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btNext.performClick();
            }
        });



        btNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position + 1)%archivos.size());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(ruta_debo_Utilizar +"/"+ String.valueOf(archivos.get(position))));
                nombreCancion = String.valueOf(nombreCanciones.get(position));
                txtCancion.setText(nombreCancion);
                mediaPlayer.start();


                btPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);
            }
        });



        btPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position - 1)<0)?(archivos.size()-1):(position-1);
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(ruta_debo_Utilizar +"/"+ String.valueOf(archivos.get(position))));
                nombreCancion = String.valueOf(nombreCanciones.get(position));
                txtCancion.setText(nombreCancion);
                mediaPlayer.start();

                btPlay.setBackgroundResource(R.drawable.ic_baseline_pause_24);

            }
        });

        btAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                }
            }
        });

        btRebobinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                }
            }
        });


    }


    //Para mostrar el tiempo transcurrido de la cancion
    public String mostrarTiempos( int duracion) {
        String tiempo = "";
        int minutos = duracion / 1000 / 60;
        int segundos = duracion / 1000 % 60;
        tiempo += minutos + ":";

        if (segundos < 10) {
            tiempo +="0";
        }
        tiempo += segundos;
        return  tiempo;
    }

    //Para parar la aplicación cuando salgamos de la aplicación
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop(); // detiene la reproducción de audio
        mediaPlayer.release();//No funciona
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            mediaPlayer.release();//No funciona
        }
    }
}
