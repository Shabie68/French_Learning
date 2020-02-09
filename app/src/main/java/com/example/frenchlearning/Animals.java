package com.example.frenchlearning;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Animals extends AppCompatActivity {

    private MediaPlayer mediaPlayer ;
    private AudioManager audioManager ;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    } ;

    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0  );
            }
            else  if( focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if( focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.word_list );
        audioManager = (AudioManager) getSystemService( Context.AUDIO_SERVICE ) ;

        final ArrayList<Language>languages = new ArrayList<>(  );
        languages.add( new Language( "Bird","Oiseau",R.raw.oiseau ,R.drawable.bird ) );
        languages.add( new Language( "Horse","Cheval",R.raw.cheval  ,R.drawable.horse) );
        languages.add( new Language( "Cow","Vashe",R.raw.vashe ,R.drawable.cow)  );
        languages.add( new Language( "Hen","Poulette" ,R.raw.poulette ,R.drawable.hen) );
        languages.add( new Language( "Tiger","Tigre" ,R.raw.tigre ,R.drawable.tiger) );
        languages.add( new Language( "Mouse","Souris" ,R.raw.souris ,R.drawable.mouse) );
        languages.add( new Language( "Rabbit","Lapen" ,R.raw.lapen ,R.drawable.rabbit) );
        languages.add( new Language( "Lamb","Agneau",R.raw.agneau  ,R.drawable.lamb) );
        languages.add( new Language( "Snake","Snake" ,R.raw.serpent ,R.drawable.snake) );
        languages.add( new Language( "Dog","Chien",R.raw.chien  ,R.drawable.dog) );
        languages.add( new Language( "Girrafe","Girafe" ,R.raw.girafe ,R.drawable.giraffe) );
        languages.add( new Language( "Tortoise","Tortue" ,R.raw.tortue ,R.drawable.tortoise) );
        languages.add( new Language( "Bat","Chauv souris",R.raw.chauv_souris ,R.drawable.bat) );

        LanguageAdapter languageAdapter = new LanguageAdapter( this,languages , R.color.category_animals );
        ListView listView =findViewById( R.id.list );
        listView.setAdapter( languageAdapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Language language = languages.get( position ) ;

                int result = audioManager.requestAudioFocus( audioFocusChangeListener ,
                        AudioManager.STREAM_MUSIC ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if (result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create( Animals.this , language.getaudioId() );
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener( completionListener );
                }
            }
        } );


    }


    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }

    }
}
