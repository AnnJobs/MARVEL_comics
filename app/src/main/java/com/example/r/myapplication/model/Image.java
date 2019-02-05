package com.example.r.myapplication.model;

import android.util.Log;
import android.widget.ImageView;

import com.example.r.myapplication.Const;
import com.example.r.myapplication.R;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Image {

    @SerializedName("path")
    public String imgPath;

    @SerializedName("extension")
    public String extension;

    public void downloadImg(String aspectRatio, ImageView imageView){

        if (imgPath.equals(Const.IMAGE_NOT_FOUND_PATH)) {
            imageView.setImageResource(R.drawable.no_photo);
        } else {
            Picasso.get().load(imgPath + "/" +  aspectRatio + "." + extension).into(imageView);
        }

    }
}
