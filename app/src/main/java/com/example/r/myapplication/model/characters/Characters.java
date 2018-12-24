package com.example.r.myapplication.model.characters;

import android.graphics.Bitmap;

import com.example.r.myapplication.model.characterInfo.CharacterImage;
import com.google.gson.annotations.SerializedName;

public class Characters {

    @SerializedName("name")
    public String name;

    @SerializedName("thumbnail")
    public CharacterImage charImage;

    @SerializedName("id")
    public int charId;

    private Bitmap image = null;

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean isImageSet(){
        return image != null;
    }
}
