package com.example.lastdiaryapp.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lastdiaryapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangeLockActivity extends AppCompatActivity {
    private ImageButton reply;
    private Button Change;
    private TextInputEditText edtNhap,editNew,editGet;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_lock);
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        password=settings.getString(getString(R.string.Password),"");
        reply=(ImageButton)findViewById(R.id.reply);
        Change=(Button)findViewById(R.id.taomatkhau);
        edtNhap=(TextInputEditText)findViewById(R.id.nhappasscu);
        editNew=(TextInputEditText)findViewById(R.id.matkhau);
        editGet=(TextInputEditText)findViewById(R.id.nhaplai);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhaucu=edtNhap.getText().toString();
                String matkhaumoi=editNew.getText().toString();
                String nhaplaipass=editGet.getText().toString();
                if(matkhaucu.equals("")&& matkhaumoi.equals("")&& nhaplaipass.equals(""))
                {
                    Toast.makeText(ChangeLockActivity.this,"Không được phép bỏ trống!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(matkhaucu.equals(password))
                    {
                        if(nhaplaipass.equals(matkhaumoi))
                        {
                            SharedPreferences settings=getSharedPreferences("PREFS",0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Password),matkhaumoi);
                            editor.apply();
                            //enter the app
                            onBackPressed();
                        }
                        else
                        {
                            Toast.makeText(ChangeLockActivity.this,"mat khau moi ko dung",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ChangeLockActivity.this,"Sai pass cu",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
