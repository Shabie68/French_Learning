package com.example.frenchlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        TextView familyView =findViewById( R.id.family );
        familyView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this , Family.class);
                startActivity( intent );
            }
        } );
        TextView animalView =findViewById( R.id.animals );
        animalView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this , Animals.class);
                startActivity( intent );
            }
        } );
        TextView numbersView =findViewById( R.id.numbers );
        numbersView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this , Numbers.class);
                startActivity( intent );
            }
        } );
        TextView partOfBodyView =findViewById( R.id.body_parts );
        partOfBodyView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this , PartsOfBody.class);
                startActivity( intent );
            }
        } );
        TextView phraseView =findViewById( R.id.phrases );
        phraseView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this , Phrases.class);
                startActivity( intent );
            }
        } );

    }
}
