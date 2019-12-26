package com.example.lastdiaryapp.ClassCrl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DbBitmapUtility {

    //covert from bitmap to byte array
    public  byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();
    }

    //covert from byte array to bitmap
    public  Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
}
