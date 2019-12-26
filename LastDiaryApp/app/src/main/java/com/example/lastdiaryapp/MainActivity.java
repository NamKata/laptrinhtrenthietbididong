package com.example.lastdiaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.example.lastdiaryapp.Setting.CofirmPassActivity;
import com.example.lastdiaryapp.Setting.LockScreenActivity;
import com.example.lastdiaryapp.Setting.Utility;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String password,Khoamanhinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Utility.getTheme(this);
        //Load the password
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString(getString(R.string.Password),"");
        final SharedPreferences settingOn= getSharedPreferences("ONOF", 0);
        Khoamanhinh=settingOn.getString(getString(R.string.Khoamatkhau),"");
        LoadLocale();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Khoamanhinh.equals("ON"))
                {
                    if(password.equals(""))
                    {
                        Intent intent=new Intent(MainActivity.this,TabCrlActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Intent intent=new Intent(MainActivity.this, CofirmPassActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
               else
                {
                    Intent intent=new Intent(MainActivity.this,TabCrlActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },5000);
    }
    private void setLocale(final String lang) {
        if(lang.equalsIgnoreCase(""))return;
        Locale mlocale=new Locale(lang);
        Locale.setDefault(mlocale);
        Configuration configuration=new Configuration();
        configuration.locale=mlocale;
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        SharedPreferences settings=getSharedPreferences("NGONNGU",0);
        String ngonngu=settings.getString(getString(R.string.ngongu),"");
        if(ngonngu.equals(""))
        {
            ngonngu="en";
            editor.putString("My_Lang",ngonngu);
        }
        else
        {
            editor.putString("My_Lang",ngonngu);
        }

        editor.apply();
    }

    private void LoadLocale() {
        SharedPreferences sharedPreferences=this.getSharedPreferences("Settings", MODE_PRIVATE);
        String lang=sharedPreferences.getString("My_Lang","");
        setLocale(lang);
    }
}
