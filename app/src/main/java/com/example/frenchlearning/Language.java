package com.example.frenchlearning;

public class Language {
    private int mImageId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private String mFrenchTranslation;
    private String mEnglishTranslation;
    private int mAudioId;


    public Language( String englishTranslation ,String frenchTranslation, int audioId){
        mEnglishTranslation = englishTranslation;
        mFrenchTranslation = frenchTranslation;
        mAudioId = audioId;
    }

    public Language( String englishTranslation ,String frenchTranslation, int audioId , int imageId){
        mEnglishTranslation = englishTranslation;
        mFrenchTranslation = frenchTranslation;
        mAudioId = audioId;
        mImageId = imageId ;
    }

    public String getenglishTranslation(){
        return mEnglishTranslation;
    }
    public String getfrenchTranslation(){
        return mFrenchTranslation;
    }
    public int getaudioId(){
        return mAudioId;
    }
    public int getimageId(){
        return mImageId ;
    }

    public boolean hasImage() {
        return mImageId != NO_IMAGE_PROVIDED;
    }


}
