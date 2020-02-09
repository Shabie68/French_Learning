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

public class PartsOfBody extends AppCompatActivity {
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
        languages.add( new Language( "Eyes","Yeux" ,R.raw.yeux ,R.drawable.yeux) );
        languages.add( new Language( "Hand","Main",R.raw.main ,R.drawable.main ) );
        languages.add( new Language( "Nose","Nez",R.raw.nez , R.drawable.nez) );
        languages.add( new Language( "Finger","Doigt",R.raw.doigt , R.drawable.doigt) );
        languages.add( new Language( "Foot","Pied" ,R.raw.pied ,R.drawable.pied) );
        languages.add( new Language( "Legs","Jambes" ,R.raw.jambes ,R.drawable.jambes) );
        languages.add( new Language( "Heart","Coeur" ,R.raw.coeur ,R.drawable.coeur) );
        languages.add( new Language( "Neck","cou",R.raw.cou  ,R.drawable.cou) );
        languages.add( new Language( "Stomach","estomach",R.raw.estomcah ,R.drawable.estomach) );
        languages.add( new Language( "Kidneys","rein" ,R.raw.reins ,R.drawable.reins) );
        languages.add( new Language( "Elbow","Coude" ,R.raw.coude ,R.drawable.coude) );
        languages.add( new Language( "Hair","Cheveux" ,R.raw.cheveux ,R.drawable.cheveux) );
        languages.add( new Language( "Chin","Menton",R.raw.menton ,R.drawable.levres ) );


        LanguageAdapter languageAdapter = new LanguageAdapter( this,languages ,R.color.category_parts_of_body);
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
                    mediaPlayer = MediaPlayer.create( PartsOfBody.this , language.getaudioId() );
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
