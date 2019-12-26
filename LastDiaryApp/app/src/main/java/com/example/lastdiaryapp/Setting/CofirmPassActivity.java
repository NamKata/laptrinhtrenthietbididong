package com.example.lastdiaryapp.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lastdiaryapp.R;
import com.example.lastdiaryapp.TabCrlActivity;
import com.google.android.material.textfield.TextInputEditText;

public class CofirmPassActivity extends AppCompatActivity {
    private TextInputEditText editText;
    private Button button;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cofirm_pass);
        editText=(TextInputEditText)findViewById(R.id.matkhau);
        button=(Button)findViewById(R.id.taomatkhau);

        //Load the password
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString(getString(R.string.Password),"");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                if(text.equals(password))
                {
                    Intent intent=new Intent(getApplicationContext(), TabCrlActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(CofirmPassActivity.this,"Sai!!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
