package com.example.lastdiaryapp.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.example.lastdiaryapp.R;

public class SizeApp extends Application {
    private String SizeTitle;
    private String SizeContent;
    private String SizeDate;
    private String SizeTime;
    private String SizeNut;
    private SharedPreferences settings;
    private Context mContext;
    public SizeApp(Context context)
    {
        this.mContext=context;
         settings=mContext.getSharedPreferences("SIZE",0);
    }
    public Float SizeTitle()
    {
        SizeTitle=settings.getString(getString(R.string.sizetieude),"");
        Float sizeTitle=Float.parseFloat(SizeTitle);
        return sizeTitle;
    }
    public Float SizeContent()
    {
        SizeContent=settings.getString(getString(R.string.SizeNoidung),"");
        Float sizeContent=Float.parseFloat(SizeContent);
        return sizeContent;
    }
    public Float SizeDate()
    {
        SizeDate=settings.getString(getString(R.string.SizeNgay),"");
        Float sizeDate=Float.parseFloat(SizeDate);
        return sizeDate;
    }
    public Float SizeTime()
    {
        SizeTime=settings.getString(getString(R.string.SizeGio),"");
        Float sizeContent=Float.parseFloat(SizeTime);
        return sizeContent;
    }
    public Float SizeButton()
    {

        SizeNut=settings.getString(getString(R.string.SizeNut),"");
        Float sizeButton=Float.parseFloat(SizeNut);
        return sizeButton;
    }
}
