package com.example.lastdiaryapp.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lastdiaryapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LockScreenActivity extends AppCompatActivity {
    private Spinner spinnerDanhsach;
    private TextInputEditText matkhau,nhaplai;
    private EditText edtTraloi;
    private Button btnTraloi;
    private ImageButton quaylai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        matkhau=(TextInputEditText)findViewById(R.id.matkhau);
        nhaplai=(TextInputEditText)findViewById(R.id.nhaplai);
        btnTraloi=(Button)findViewById(R.id.taomatkhau);
        quaylai=(ImageButton)findViewById(R.id.quaylai);
        btnTraloi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1=matkhau.getText().toString();
                String text2=nhaplai.getText().toString();
                if(text1.equals("")&& text2.equals(""))
                {
                    Toast.makeText(LockScreenActivity.this,"Không được phép bỏ trống!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(text1.equals(text2))
                    {
                        SharedPreferences settings=getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putString(getString(R.string.Password),text1);
                        editor.apply();
                        //enter the app
                        onBackPressed();
                    }
                    else
                    {
                        Toast.makeText(LockScreenActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
