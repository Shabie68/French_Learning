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

public class Phrases extends AppCompatActivity{

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

        final ArrayList<Language> languages = new ArrayList<>(  );
        languages.add( new Language( "Hello","Bonjour",R.raw.bonjour ) );
        languages.add( new Language( "My name is Shabie","Jme'appelle Shabie",R.raw.appelle ) );
        languages.add( new Language( "How are you?","Comment vas-tu",R.raw.comment ) );
        languages.add( new Language( "I'm fine","Je vais bien",R.raw.bien ) );
        languages.add( new Language( "Where do you live?","Ou habite tu" ,R.raw.habite) );
        languages.add( new Language( "You speak English","parlez-vous-anglais?" ,R.raw.parle) );
        languages.add( new Language( "Is this wifi free","Le WiFi est-il gratuit? ",R.raw.wifi ) );
        languages.add( new Language( "You eat an apple","Tu manages une pomme",R.raw.manger) );
        languages.add( new Language( "Have a happy new year","Bonne annee" ,R.raw.bonn_anne) );
        languages.add( new Language( "Enjoy the holidays","Profitez des vacances!" ,R.raw.vacann) );
        languages.add( new Language( "I'm Sleepy","J'ai sommeil." ,R.raw.sommeil) );

        LanguageAdapter languageAdapter = new LanguageAdapter( this,languages ,R.color.category_phrases );
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
                    mediaPlayer = MediaPlayer.create( Phrases.this , language.getaudioId() );
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