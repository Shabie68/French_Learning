package com.example.frenchlearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class LanguageAdapter extends ArrayAdapter<Language>{
    private int mColorResourceId ;
    public LanguageAdapter(@NonNull Context context, ArrayList<Language>languages, int colorResourceId ) {
        super( context, 0, languages );
        mColorResourceId = colorResourceId ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView =convertView ;
        if(listView == null){
            listView= LayoutInflater.from( getContext()  ).inflate( R.layout.list_item , parent ,false );
        }
        Language currentLanguage = getItem( position );

        TextView englishView = listView.findViewById( R.id.english );

        englishView.setText( currentLanguage.getenglishTranslation() );

        TextView frenchTextView = listView.findViewById( R.id.french );
        frenchTextView.setText( currentLanguage.getfrenchTranslation() );

        ImageView imageView = (ImageView) listView.findViewById(R.id.image);
        // Check if an image is provided for this word or not
        if (currentLanguage.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentLanguage.getimageId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listView.findViewById( R.id.text_container );
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        return listView ;
    }
}
