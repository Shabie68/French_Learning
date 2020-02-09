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

public class Numbers extends AppCompatActivity {
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

        final ArrayList<Language>languages =new ArrayList<>(  );
        languages.add( new Language( "Zero(0)" , "Zero" ,R.raw.zero,R.drawable.zero) );
        languages.add( new Language( "One(1)" , "un" ,R.raw.un ,R.drawable.un) );
        languages.add( new Language( "Two(2)" , "Deux" ,R.raw.deux ,R.drawable.deux) );
        languages.add( new Language( "Three(3)" , "Trois",R.raw.trois ,R.drawable.trois) );
        languages.add( new Language( "Four(4)" , "Quatre",R.raw.quatre ,R.drawable.quatre) );
        languages.add( new Language( "Five(5)" , "Cinq" ,R.raw.cinq ,R.drawable.cinq) );
        languages.add( new Language( "Six(6)" , "Six" ,R.raw.six ,R.drawable.six) );
        languages.add( new Language( "Seven(7)" , "Sept",R.raw.sept ,R.drawable.sept) );
        languages.add( new Language( "Eight(8)" , "Huit",R.raw.huit ,R.drawable.huit) );
        languages.add( new Language( "Nine(9)" , "Neuf",R.raw.neuf ,R.drawable.neuf) );
        languages.add( new Language( "Ten(10)" , "Dix" ,R.raw.dix ,R.drawable.dix) );
        languages.add( new Language( "Eleven(11)" , "Onze" ,R.raw.onze ,R.drawable.onze) );
        languages.add( new Language( "Twelve(12)" , "Douze",R.raw.douze ,R.drawable.douze) );
        languages.add( new Language( "Thirteen(13)" , "Treize",R.raw.treize ,R.drawable.trieze) );
        languages.add( new Language( "Fourteen(14)" , "Quatorze" ,R.raw.quatorze ,R.drawable.quatorze) );
        languages.add( new Language( "Fifteen(15)" , "Quinze",R.raw.quinze ,R.drawable.quinze) );
        languages.add( new Language( "Sixteen(16)" , "Seize" ,R.raw.seize ,R.drawable.seize) );
        languages.add( new Language( "Seventeen(17)" , "Dix-Sept" ,R.raw.dix_sept ,R.drawable.dix_sept) );
        languages.add( new Language( "Eighteen(18)" , "Dix_Huit" ,R.raw.dix_huit ,R.drawable.dix_huit) );
        languages.add( new Language( "Nineteen(19)" , "Dix-Neuf",R.raw.dix_neuf , R.drawable.dix_neuf) );
        languages.add( new Language( "Twenty(20)" , "Vingt" ,R.raw.vingt ,R.drawable.vingt) );

        LanguageAdapter languageAdapter = new LanguageAdapter( this,languages ,R.color.category_numbers );
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
                    mediaPlayer = MediaPlayer.create( Numbers.this , language.getaudioId() );
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
