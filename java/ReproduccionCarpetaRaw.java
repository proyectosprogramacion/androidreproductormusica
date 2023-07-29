package com.example.reproductor_musica_version5;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ReproduccionCarpetaRaw extends AppCompatActivity {


    //Variables de los botones y del imageView
    Button btPlay;
    Button btRepetir;
    ImageView ivImagenesAudio;


    boolean seguirReproduciendo =true;//Variable para hacer que se repita o no el audio
    int posicion= 0; //Posicion en el array de canciones

    //Array de la clase MediaPlayer para las canciones de la carpeta raw
    final int DIMENSIONESARRAY = 3;
    MediaPlayer[] canciones = new MediaPlayer [DIMENSIONESARRAY];


    //Objeto de la clase MediaPlayer
    MediaPlayer mediaPlayer = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduccion_carpeta_raw);



        btPlay = findViewById(R.id.btPlay);
        btRepetir = findViewById(R.id.btRepetir);
        ivImagenesAudio = findViewById(R.id.ivImagenesAudio);

        //Rellenamos el array
        canciones[0]= MediaPlayer.create(this,R.raw.race );
        canciones[1]= MediaPlayer.create(this,R.raw.sound );
        canciones[2]= MediaPlayer.create(this,R.raw.tea );
    }
    //Metodo para reproducir cancion
    public void playPause(View view){
        //el método isPlaying nos indica si el audio se esta reproduciendo o no
        if(canciones[posicion].isPlaying()){//Si se esta reproduciendo
            //Si el audio se reproduce, al pulsar el botón lo que tiene que hacer es lo contrarío es decir pararse la música
            canciones[posicion].pause();

            //Cambiamos la apariencia del audio a una imagen de reproducir
            btPlay.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        }else{//Si la canción no se esta reproducciendo
            //Si el audio no se reproduce, al pulsar el botón lo que tiene que hacer es lo contrarío es decir empezar la música
            canciones[posicion].start();

            //Cambiamos la apariencia del audio a una imagen de pausa
            btPlay.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Reproducir", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para parar la reproduccion

    public void Stop (View view){
        if(canciones[posicion]!= null) {//Cuando hay una canción para reproducir
            //paramos la reproduccion de cualquier canción
            canciones[posicion].stop();

            //Deberemos rellenar de nuevo el array con las canciones
            canciones[0]= MediaPlayer.create(this,R.raw.race );
            canciones[1]= MediaPlayer.create(this,R.raw.sound );
            canciones[2]= MediaPlayer.create(this,R.raw.tea );
            posicion=0;

            //Cambiamos la apariencia del audio a una imagen de reproducir
            btPlay.setBackgroundResource(R.drawable.reproducir);
            //Modificamos a la imagen principal
            ivImagenesAudio.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        }
    }

    //Método repetir la pista de audio
    public void Repetir (View view){
        if(seguirReproduciendo == false){
            btRepetir.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            //Método para reproducir continuamente la canción
            canciones[posicion].setLooping(false);//con false no se  repiten las canciones
            seguirReproduciendo = true;
        }else{
            btRepetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            //Método para reproducir continuamente la canción
            canciones[posicion].setLooping(true);//con true no se  repiten las canciones
            seguirReproduciendo = false;
        }
    }

    //Método siguiente
    public void Siguiente (View view){
        if(posicion < canciones.length -1 ){//Si la posición es menor a la longitud del array menos uno
            //Permite que el indice posicion siga recorriendo el array
            if(canciones[posicion].isPlaying()){//Si se esta reproduciendo la canción
                canciones[posicion].stop();//para la canción actual
                posicion++;
                canciones[posicion].start();//La canción siguiente se reproduce

                //Modificación portada canciones
                if(posicion == 0){
                    ivImagenesAudio.setImageResource(R.drawable.portada1);
                }else if(posicion == 1) {
                    ivImagenesAudio.setImageResource(R.drawable.portada2);
                }else if(posicion == 2) {
                    ivImagenesAudio.setImageResource(R.drawable.portada3);
                }

            }else{//Si no se reproduce ningún audio actualmente, no hay que pararlo solo avanzar a la siguiente
                posicion++;

                //Modificación portada canciones
                if(posicion == 0){
                    ivImagenesAudio.setImageResource(R.drawable.portada1);
                }else if(posicion == 1) {
                    ivImagenesAudio.setImageResource(R.drawable.portada2);
                }else if(posicion == 2) {
                    ivImagenesAudio.setImageResource(R.drawable.portada3);
                }
            }


        }else{
            Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }

    //Método anterior
    public void Anterior (View view){
        if(posicion >= 1){//La posición debe ser mayor o igual a uno

            //Permite que el indice posicion siga recorriendo el array
            if(canciones[posicion].isPlaying()){//Si se esta reproduciendo la canción
                canciones[posicion].stop();//parar la canción actual

                // tras la llamada al método stop() el objeto MediaPlayer queda en estado Stopped
                // y debe llamarse al método prepare() para que pase al estado Prepared

                //Se resuelve en este caso creandolo de nuevo
                //Deberemos rellenar de nuevo el array con las canciones
                canciones[0]= MediaPlayer.create(this,R.raw.race );
                canciones[1]= MediaPlayer.create(this,R.raw.sound );
                canciones[2]= MediaPlayer.create(this,R.raw.tea );

                posicion--;//El indice se decrementa
                canciones[posicion].start();//La canción anterior se reproduce

                //Modificación portada canciones
                if(posicion == 0){
                    ivImagenesAudio.setImageResource(R.drawable.portada1);
                }else if(posicion == 1) {
                    ivImagenesAudio.setImageResource(R.drawable.portada2);
                }else if(posicion == 2) {
                    ivImagenesAudio.setImageResource(R.drawable.portada3);
                }

            }else{//Si no se reproduce ningún audio actualmente, no hay que pararlo solo avanzar a la siguiente
                posicion--;

                //Modificación portada canciones
                if(posicion == 0){
                    ivImagenesAudio.setImageResource(R.drawable.portada1);
                }else if(posicion == 1) {
                    ivImagenesAudio.setImageResource(R.drawable.portada2);
                }else if(posicion == 2) {
                    ivImagenesAudio.setImageResource(R.drawable.portada3);
                }
            }


        }else{
            Toast.makeText(this, "No hay más canciones", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        canciones[posicion].stop(); // detiene la reproducción de audio
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
    }
}
