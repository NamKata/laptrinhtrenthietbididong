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
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lastdiaryapp.AddOrEditCrl.EditActivity;
import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.ClassCrl.RecyclerEventViewAdapter;
import com.example.lastdiaryapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class EditEventActivity extends AppCompatActivity {
    private TextView ngaythangnam,giohientai,ngayhen,giohen;
    private EditText tieudenhacnho,noidungnhacnho;
    private ImageView hinhbieucam;
    private ImageButton chonngay,chongio,chonbieucam,chonthongbao,quaylai,xoaevent;
    private Button btnSave,btnHuy;
    private Dialog dialogalert,dialogicon,dialogtrash;
    private Calendar calendar;
    private int dayOfmonth,month,year,gio,phut;
    private DatePickerDialog datePickerDialog;
    private RecyclerEventViewAdapter viewAdapter;
    private TimePickerDialog timePickerDialog;
    private static int valIcon;
    private static int IconTB;
    private boolean TT=true;
    private int getIDDD;
    private DatabaseEventHelper thongthao;
    private Event event;
    private List<Event> eventList=new ArrayList<Event>();
    private int chu,nen,nut,nutthanhcong,nuthatbai,nentrong;
    private TextToSpeech mTTs;
    private String chucnangnoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        thongthao=new DatabaseEventHelper(EditEventActivity.this);
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
        RelativeLayout bag=(RelativeLayout)findViewById(R.id.background);
        bag.setBackgroundColor(nen);
        LinearLayout ll4=(LinearLayout)findViewById(R.id.ll4);
        ll4.setBackgroundColor(nentrong);
        LinearLayout lTime=(LinearLayout)findViewById(R.id.llTime);
        lTime.setBackgroundColor(nentrong);
        ngaythangnam=(TextView)findViewById(R.id.ngaythangnam);
        ngaythangnam.setTypeface(settype());
        ngaythangnam.setTextSize(sizeNgay());
        giohientai=(TextView)findViewById(R.id.giohientai);
        giohientai.setTypeface(settype());
        giohientai.setTextSize(sizeGio());
        ngayhen=(TextView)findViewById(R.id.ngayhen);
        ngayhen.setTypeface(settype());
        ngayhen.setTextSize(sizeNgay());
        giohen=(TextView)findViewById(R.id.giohen);
        giohen.setTypeface(settype());
        giohen.setTextSize(sizeGio());
        tieudenhacnho=(EditText)findViewById(R.id.tieudenhacnho);
        tieudenhacnho.setTypeface(settype());
        tieudenhacnho.setTextSize(sizeTitle());
        noidungnhacnho=(EditText)findViewById(R.id.noidungnhacnho);
        noidungnhacnho.setTypeface(settype());
        noidungnhacnho.setTextSize(sizeContent());
        hinhbieucam=(ImageView)findViewById(R.id.bieucam);
        chonngay=(ImageButton)findViewById(R.id.btnCalendarEdit);
        chongio=(ImageButton)findViewById(R.id.btnTimePicker);
        chonbieucam=(ImageButton)findViewById(R.id.btnIconEdit);
        chonthongbao=(ImageButton)findViewById(R.id.btnThongbao);
        quaylai=(ImageButton)findViewById(R.id.quaylai);
        btnSave=(Button)findViewById(R.id.saveEditevent);
        btnSave.setTypeface(settype());
        btnSave.setTextSize(sizeButton());
        btnHuy=(Button)findViewById(R.id.dongEditevent);
        btnHuy.setTypeface(settype());
        btnHuy.setTextSize(sizeButton());
        xoaevent=(ImageButton) findViewById(R.id.btnTrashEdit);
        final SharedPreferences settingOn= getSharedPreferences("ONOFNOI", 0);
        chucnangnoi=settingOn.getString(getString(R.string.Noi),"");
        if(chucnangnoi.equals("ON"))
        {
            mTTs=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status!=TextToSpeech.ERROR)
                    {
                        mTTs.setLanguage(Locale.ENGLISH);
                    }
                    else
                    {
                        Toast.makeText(EditEventActivity.this,"Erroe",Toast.LENGTH_LONG).show();
                    }
                }
            });
            tieudenhacnho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toSpeak=tieudenhacnho.getText().toString().trim();
                    mTTs.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(EditEventActivity.this,toSpeak,Toast.LENGTH_LONG).show();
                }
            });
            noidungnhacnho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toSpeak=noidungnhacnho.getText().toString().trim();
                    mTTs.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(EditEventActivity.this,toSpeak,Toast.LENGTH_LONG).show();
                }
            });
        }

        dialogtrash=new Dialog(EditEventActivity.this);
        dialogtrash.setContentView(R.layout.dialog_trash);
        xoaevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)dialogtrash.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                btnOk.setTextSize(sizeButton());
                Button btnKhong=(Button)dialogtrash.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
                btnKhong.setTextSize(sizeButton());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thongthao.deleteEvent(event);
                        Toast.makeText(EditEventActivity.this,"Success",Toast.LENGTH_LONG).show();
                        eventList.remove(event);
                        dialogtrash.cancel();
                        dialogtrash.cancel();
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
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialogalert=new Dialog(EditEventActivity.this);
        dialogalert.setContentView(R.layout.dialog);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogalert.show();
                dialogalert.setCancelable(false);
                Button btnOk=(Button)dialogalert.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                Button btnKhong=(Button)dialogalert.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
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

        chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ngayhen.setText(dayOfMonth+"-"+month+"-"+year);

                    }
                },year,month,dayOfmonth);
                datePickerDialog.show();
            }
        });

        chongio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                gio=calendar.get(Calendar.HOUR_OF_DAY);
                phut=calendar.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(EditEventActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hformate=new SimpleDateFormat("K:mm a");
                        String event_Time=hformate.format(c.getTime());
                        giohen.setText(event_Time);
                    }
                },gio,phut,false);
                timePickerDialog.show();
            }
        });

        dialogicon=new Dialog(EditEventActivity.this);
        dialogicon.setContentView(R.layout.dialog_icon_event);
        chonbieucam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton clo=(ImageButton)dialogicon.findViewById(R.id.xxx);
                LinearLayout happy=(LinearLayout)dialogicon.findViewById(R.id.happyicon);
                LinearLayout unhappy=(LinearLayout)dialogicon.findViewById(R.id.buonicon);
                LinearLayout work=(LinearLayout)dialogicon.findViewById(R.id.congviecicon);
                LinearLayout eventGet=(LinearLayout)dialogicon.findViewById(R.id.henicon);
                happy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valIcon=R.drawable.happy;
                        hinhbieucam.setImageResource(valIcon);
                        dialogicon.cancel();
                        return;
                    }
                });
                unhappy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valIcon=R.drawable.unhappy;
                        hinhbieucam.setImageResource(valIcon);
                        dialogicon.cancel();
                        return;
                    }
                });
                work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valIcon=R.drawable.work;
                        hinhbieucam.setImageResource(valIcon);
                        dialogicon.cancel();
                        return;
                    }
                });
                eventGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        valIcon=R.drawable.event;
                        hinhbieucam.setImageResource(valIcon);
                        dialogicon.cancel();
                        return;
                    }
                });
                clo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogicon.cancel();
                    }
                });
                dialogicon.setCancelable(false);
                dialogicon.show();
            }
        });
        if(hinhbieucam.getResources()==null)
        {
            hinhbieucam.setVisibility(View.GONE);
        }
        else
        {
            hinhbieucam.setVisibility(View.VISIBLE);

        }

        IconTB=R.drawable.ic_notifications_active_black_24dp;
        chonthongbao.setImageResource(IconTB);
        chonthongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IconTB==R.drawable.ic_notifications_active_black_24dp)
                {
                    TT=true;
                    IconTB=R.drawable.ic_notifications_off_black_24dp;
                    chonthongbao.setImageResource(IconTB);
                    return;
                }
                else
                {
                    TT=false;
                    IconTB=R.drawable.ic_notifications_active_black_24dp;
                    chonthongbao.setImageResource(IconTB);
                    return;
                }
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
        if(event.getNotifical().equals("on"))
        {
            chonthongbao.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        }
        else
        {
            chonthongbao.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setTitle(tieudenhacnho.getText().toString());
                event.setContent(noidungnhacnho.getText().toString());
                event.setTimeEvent(giohen.getText().toString());
                event.setDateEvent(ngayhen.getText().toString());
                event.setPhoto(valIcon);
                if( IconTB==R.drawable.ic_notifications_active_black_24dp)
                {
                    event.setNotifical("on");
                }
                else
                {
                    event.setNotifical("off");
                }
                thongthao.updateDiary(event);
                Toast.makeText(EditEventActivity.this,"Success",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
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
