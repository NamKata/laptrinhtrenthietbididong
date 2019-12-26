package com.example.lastdiaryapp.Setting;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lastdiaryapp.R;

public class BaseActivity extends AppCompatActivity {
    private final static int THEME_BLUE=1;
    private final static int THEME_RED=2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateTheme();
    }

    private void updateTheme() {
        if(Utility.getTheme(getApplicationContext())<= THEME_BLUE)
        {
            setTheme(R.style.AppTheme);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent_Black));
            }
        }
        else if(Utility.getTheme(getApplicationContext())==THEME_RED)
        {
            setTheme(R.style.Black);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
