package com.example.lastdiaryapp.AddOrEditEventCrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastdiaryapp.AddOrEditCrl.EditActivity;
import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.ClassCrl.RecyclerEventViewAdapter;
import com.example.lastdiaryapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewEventActivity extends AppCompatActivity {
    private TextView ngaythangnam,giohientai,ngayhen,giohen;
    private EditText tieudenhacnho,noidungnhacnho;
    private ImageView hinhbieucam;
    private ImageButton itentsua,quaylai,xoaevent,reply;
    private Dialog dialogalert,dialogicon,dialogtrash;
    private int getIDDD;
    private Event event;
    private DatabaseEventHelper thongthao;
    private List<Event> eventList=new ArrayList<Event>();
    private RecyclerEventViewAdapter viewAdapter;
    private int chu,nen,nentrong,nut,nutthanhcong,nuthatbai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        thongthao=new DatabaseEventHelper(ViewEventActivity.this);

        final SharedPreferences setcolor= getSharedPreferences("COLOR", 0);

        if(setcolor.getInt(getString(R.string.Mauchu),0)==0 && setcolor.getInt(getString(R.string.background),0)==0 && setcolor.getInt(getString(R.string.Maunut),0)==0 &&setcolor.getInt(getString(R.string.Maunutthanhcong),0)==0 && setcolor.getInt(getString(R.string.Maunutthatbai),0)==0 && setcolor.getInt(getString(R.string.Maunentrong),0)==0)
        {
//            chu=Color.rgb(255,255,255);
            chu= Color.rgb(255,96,96);
            nen=Color.rgb(117,71,71);
            nut=Color.rgb(96,73,47);
            nuthatbai=Color.rgb(33,40,58);
            nutthanhcong=Color.rgb(60,63,65);
            nentrong=Color.rgb(111,84,87);
        }
        else {
            chu=setcolor.getInt(getString(R.string.Mauchu),0);
            nen=setcolor.getInt(getString(R.string.background),0);
            nut=setcolor.getInt(getString(R.string.Maunut),0);
            nutthanhcong=setcolor.getInt(getString(R.string.Maunutthanhcong),0);
            nuthatbai=setcolor.getInt(getString(R.string.Maunutthatbai),0);
            nentrong=setcolor.getInt(getString(R.string.Maunentrong),0);
        }
        LinearLayout lll=(LinearLayout)findViewById(R.id.lllllllll);
        lll.setBackgroundColor(nentrong);
        RelativeLayout backg=(RelativeLayout)findViewById(R.id.background);
        backg.setBackgroundColor(nen);
        LinearLayout ll4=(LinearLayout)findViewById(R.id.ll4);
        ll4.setBackgroundColor(nentrong);
        ngaythangnam=(TextView)findViewById(R.id.ngaythangnam);
        ngaythangnam.setTypeface(settype());
        ngaythangnam.setTextSize(sizeNgay());
        ngaythangnam.setTextColor(chu);
        giohientai=(TextView)findViewById(R.id.giohientai);
        giohientai.setTypeface(settype());
        giohientai.setTextSize(sizeGio());
        giohientai.setTextColor(chu);
        ngayhen=(TextView)findViewById(R.id.ngayhen);
        ngayhen.setTypeface(settype());
        ngayhen.setTextSize(sizeNgay());
        ngayhen.setTextColor(chu);
        giohen=(TextView)findViewById(R.id.giohen);
        giohen.setTypeface(settype());
        giohen.setTextSize(sizeGio());
        giohen.setTextColor(chu);
        tieudenhacnho=(EditText)findViewById(R.id.tieudenhacnho);
        tieudenhacnho.setTypeface(settype());
        tieudenhacnho.setTextSize(sizeTitle());
        tieudenhacnho.setTextColor(chu);
        noidungnhacnho=(EditText)findViewById(R.id.noidungnhacnho);
        noidungnhacnho.setTypeface(settype());
        noidungnhacnho.setTextSize(sizeContent());
        noidungnhacnho.setTextColor(chu);
        hinhbieucam=(ImageView)findViewById(R.id.bieucam);
        quaylai=(ImageButton)findViewById(R.id.quaylai);
        itentsua=(ImageButton) findViewById(R.id.btnintentEdit);
        reply=(ImageButton) findViewById(R.id.reply);
        xoaevent=(ImageButton) findViewById(R.id.xoaEdit);


        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogalert=new Dialog(ViewEventActivity.this);
        dialogalert.setContentView(R.layout.dialog);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogalert.show();
                dialogalert.setCancelable(false);
                Button btnOk=(Button)dialogalert.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                btnOk.setTextSize(sizeButton());
                Button btnKhong=(Button)dialogalert.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
                btnOk.setTextSize(sizeButton());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                btnKhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogalert.cancel();
                    }
                });
            }
        });
        itentsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewEventActivity.this,EditEventActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putInt("ID",event.getID());
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        dialogtrash=new Dialog(ViewEventActivity.this);
        dialogtrash.setContentView(R.layout.dialog_trash);
        xoaevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)dialogtrash.findViewById(R.id.co);
                btnOk.setTextSize(sizeButton());
                btnOk.setTextColor(nut);
                btnOk.setTextColor(nutthanhcong);
                btnOk.setTypeface(settype());
                Button btnKhong=(Button)dialogtrash.findViewById(R.id.khong);
                btnKhong.setTextSize(sizeButton());
                btnKhong.setTextColor(nuthatbai);
                btnKhong.setBackgroundColor(nut);
                btnKhong.setTypeface(settype());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thongthao.deleteEvent(event);
                        Toast.makeText(ViewEventActivity.this,"Success", Toast.LENGTH_LONG).show();
                        eventList.remove(event);
                        dialogtrash.cancel();
                        dialogtrash.show();
                        viewAdapter.notifyDataSetChanged();
                        onBackPressed();
                    }
                });
                btnKhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogtrash.cancel();
                    }
                });
                dialogtrash.setCancelable(false);
                dialogtrash.show();
            }
        });

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle !=null)
        {
            getIDDD=bundle.getInt("ID");
        }
        event=thongthao.getEventByID(getIDDD);
        tieudenhacnho.setText(event.getTitle());
        noidungnhacnho.setText(event.getContent());
        ngaythangnam.setText(event.getDateCreate());
        giohientai.setText(event.getTimeCreate());
        giohen.setText(event.getTimeEvent());
        ngayhen.setText(event.getDateEvent());
        hinhbieucam.setImageResource(event.getPhoto());
    }

    public Typeface settype()
    {
        SharedPreferences settingfont=getSharedPreferences("FONT",0);
        int font=settingfont.getInt(getString(R.string.font),0);
        if(font>0)
        {
            Typeface tf= ResourcesCompat.getFont(this.getApplicationContext(),font);
            return tf;
        }
        else
        {
            return null;
        }
    }
    public Float sizeTitle()
    {
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(getString(R.string.sizetieude),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(getString(R.string.sizetieude),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeContent()
    {
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(getString(R.string.SizeNoidung),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(getString(R.string.SizeNoidung),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeButton()
    {
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(getString(R.string.SizeNut),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(getString(R.string.SizeNut),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeNgay()
    {
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(getString(R.string.SizeNgay),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(getString(R.string.SizeNgay),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeGio()
    {
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(getString(R.string.SizeGio),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(getString(R.string.SizeGio),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }

}