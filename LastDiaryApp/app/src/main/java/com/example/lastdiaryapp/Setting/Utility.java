package com.example.lastdiaryapp.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lastdiaryapp.R;

public class Utility {

    public static void setTheme(Context context,int theme)
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(context.getString(R.string.theme),theme);
        prefs.edit().apply();
    }
    public static int getTheme(Context context)
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(context.getString(R.string.theme),R.style.Default);
    }
}
