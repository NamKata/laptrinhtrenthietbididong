package com.example.lastdiaryapp.AddOrEditEventCrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.ClassCrl.RecyclerEventViewAdapter;
import com.example.lastdiaryapp.MainActivity;
import com.example.lastdiaryapp.R;
import com.example.lastdiaryapp.TabCrlActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AddEventActivity extends AppCompatActivity {
    private TextView ngaythangnam,giohientai,ngayhen,giohen;
    private EditText tieudenhacnho,noidungnhacnho;
    private ImageView hinhbieucam;
    private ImageButton chonngay,chongio,chonbieucam,chonthongbao,quaylai;
    private Button btnSave,btnHuy;
    private Dialog dialogalert,dialogicon,dialogsucces;
    private  Calendar calendar;
    private int dayOfmonth,month,year,gio,phut;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private static int valIcon;
    private static int IconTB;
    private boolean TT=true;
    private Event event;
    private DatabaseEventHelper thongthao;
    int alarmYear,alarmMonth,alarmDay,alarmHour,alarmMinuit;
    private RecyclerEventViewAdapter eventViewAdapter;
    private List<Event> eventList=new ArrayList<Event>();
    private int chu,nentrong,nen,nut,nutthanhcong,nuthatbai;
//    private ProgressBar progressBar;
//    private  int i=0;
//    private CheckBox Nhacnho;
//    private DatePicker datepicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        thongthao=new DatabaseEventHelper(AddEventActivity.this);
        ngaythangnam=(TextView)findViewById(R.id.ngaythangnam);
        giohientai=(TextView)findViewById(R.id.giohientai);
        ngayhen=(TextView)findViewById(R.id.ngayhen);
        giohen=(TextView)findViewById(R.id.giohen);
        tieudenhacnho=(EditText)findViewById(R.id.tieudenhacnho);
        noidungnhacnho=(EditText)findViewById(R.id.noidungnhacnho);
        hinhbieucam=(ImageView)findViewById(R.id.bieucam);
        chonngay=(ImageButton)findViewById(R.id.btnCalendarEdit);
        chongio=(ImageButton)findViewById(R.id.btnTimePicker);
        chonbieucam=(ImageButton)findViewById(R.id.btnIconEdit);
        chonthongbao=(ImageButton)findViewById(R.id.btnThongbao);
//        Nhacnho=(CheckBox)findViewById(R.id.alarmCkb);
        quaylai=(ImageButton)findViewById(R.id.btnClose1Edit);
        btnSave=(Button)findViewById(R.id.btnSaveEvent);
        btnHuy=(Button)findViewById(R.id.btnCloseEvent);



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

        LinearLayout ll4=(LinearLayout)findViewById(R.id.ll4);
        ll4.setBackgroundColor(nentrong);
        LinearLayout llTime=(LinearLayout)findViewById(R.id.llTime);
        llTime.setBackgroundColor(nentrong);
        RelativeLayout ba=(RelativeLayout)findViewById(R.id.background);
        ba.setBackgroundColor(nen);
        ngaythangnam.setTypeface(settype());
        ngaythangnam.setTextColor(chu);
        ngaythangnam.setTextSize(sizeNgay());
        giohientai.setTypeface(settype());
        giohientai.setTextColor(gio);
        giohientai.setTextSize(sizeGio());
        ngayhen.setTypeface(settype());
        ngayhen.setTextColor(chu);
        ngayhen.setTextSize(sizeNgay());
        giohen.setTypeface(settype());
        giohen.setTextColor(chu);
        giohen.setTextSize(sizeGio());
        tieudenhacnho.setTypeface(settype());
        tieudenhacnho.setTextColor(chu);
        tieudenhacnho.setTextSize(sizeTitle());
        noidungnhacnho.setTypeface(settype());
        noidungnhacnho.setTextColor(chu);
        noidungnhacnho.setTextSize(sizeContent());
        btnSave.setTypeface(settype());
        btnSave.setTextColor(nut);
        btnSave.setTextSize(sizeButton());
        btnHuy.setTypeface(settype());
        btnHuy.setTextColor(nut);
        btnHuy.setTextSize(sizeButton());

//        progressBar=(ProgressBar)findViewById(R.id.pBar);
        eventViewAdapter=new RecyclerEventViewAdapter(AddEventActivity.this,eventList);

        final String giohientai1=new SimpleDateFormat("K:mm a").format(new Date());
        final String ngaythangnam1=new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
        ngaythangnam.setText(ngaythangnam1);
        giohientai.setText(giohientai1);
        ngayhen.setText(ngaythangnam1);
        giohen.setText(giohientai1);

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialogalert=new Dialog(AddEventActivity.this);
        dialogalert.setContentView(R.layout.dialog);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                dialogalert.show();
            }
        });

        chonngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayOfmonth=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog=new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                          ngayhen.setText(dayOfMonth+"-"+month+"-"+year);

                          Calendar c=Calendar.getInstance();
                          c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                          c.set(Calendar.MONTH,month);
                          c.set(Calendar.YEAR,year);
                          SimpleDateFormat format=new SimpleDateFormat("dd-MMMM-yyyy");
                          String t=format.format(c.getTime());
                          ngayhen.setText(t);
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
                timePickerDialog=new TimePickerDialog(AddEventActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar=Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hformate=new SimpleDateFormat("K:mm a");
                        String event_Time=hformate.format(calendar.getTime());
                        giohen.setText(event_Time);
                    }
                },gio,phut,false);
                timePickerDialog.show();
            }
        });

        dialogicon=new Dialog(AddEventActivity.this);
        dialogicon.setContentView(R.layout.dialog_icon_event);
        chonbieucam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton clo=(ImageButton)dialogicon.findViewById(R.id.xxx);
                LinearLayout happy=(LinearLayout)dialogicon.findViewById(R.id.happyicon);
                LinearLayout unhappy=(LinearLayout)dialogicon.findViewById(R.id.buonicon);
                LinearLayout work=(LinearLayout)dialogicon.findViewById(R.id.congviecicon);
                LinearLayout eventGet=(LinearLayout)dialogicon.findViewById(R.id.henicon);
                TextView vuive=(TextView)dialogicon.findViewById(R.id.vuive);
                TextView buonba=(TextView)dialogicon.findViewById(R.id.buonba);
                TextView dilam=(TextView)dialogicon.findViewById(R.id.dilam);
                TextView dihenho=(TextView)dialogicon.findViewById(R.id.dihenho);
                vuive.setTypeface(settype());
                buonba.setTypeface(settype());
                dilam.setTypeface(settype());
                dihenho.setTypeface(settype());
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
                    TT=false;
                    IconTB=R.drawable.ic_notifications_off_black_24dp;
                    chonthongbao.setImageResource(IconTB);

                }
                else
                {
                    TT=true;
                    IconTB=R.drawable.ic_notifications_active_black_24dp;
                    chonthongbao.setImageResource(IconTB);

                }
            }
        });

        dialogsucces=new Dialog(AddEventActivity.this);
        dialogsucces.setTitle("Thông báo");
        dialogsucces.setContentView(R.layout.dialog_success);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TT==true)
                {
                    event=new Event(tieudenhacnho.getText().toString(),noidungnhacnho.getText().toString(),giohientai.getText().toString(),ngaythangnam.getText().toString(),ngayhen.getText().toString(),giohen.getText().toString(),valIcon,"on");
                    thongthao.addEvent(event);
//                    calendar=Calendar.getInstance();
                    GetNotificalbyEvent(calendar,tieudenhacnho.getText().toString(),noidungnhacnho.getText().toString(),getRequestCode(tieudenhacnho.getText().toString(),noidungnhacnho.getText().toString(),giohen.getText().toString(),ngayhen.getText().toString()));

                    Toast.makeText(AddEventActivity.this,"Success1",Toast.LENGTH_LONG).show();
                    eventViewAdapter.notifyDataSetChanged();
                    Button ok=dialogsucces.findViewById(R.id.xacnhan);
                    ok.setTypeface(settype());
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogsucces.cancel();
                            onBackPressed();
                        }
                    });
                    dialogsucces.setCancelable(false);
                    dialogsucces.show();
                   // onBackPressed();
                }
                else
                {
                    event=new Event(tieudenhacnho.getText().toString(),noidungnhacnho.getText().toString(),giohientai.getText().toString(),ngaythangnam.getText().toString(),ngayhen.getText().toString(),giohen.getText().toString(),valIcon,"off");
                    thongthao.addEvent(event);
                    eventViewAdapter.notifyDataSetChanged();
//                Intent intent=new Intent();
//                PendingIntent pIntent=PendingIntent.getActivity(AddEventActivity.this,0,intent,0);
//                Notification notification=new Notification.Builder(AddEventActivity.this)
//                        .setTicker("Thông báo")
//                        .setContentTitle(tieudenhacnho.getText())
//                        .setContentText(noidungnhacnho.getText())
//                        .setSmallIcon(R.mipmap.ic_logo).setContentIntent(pIntent).getNotification();
//                notification.flags=Notification.FLAG_AUTO_CANCEL;
//                NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                nm.notify(0,notification);

                    Toast.makeText(AddEventActivity.this,"Success",Toast.LENGTH_LONG).show();
                    Button ok=dialogsucces.findViewById(R.id.xacnhan);
                    ok.setTypeface(settype());
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogsucces.cancel();
                            onBackPressed();
                        }
                    });
                    dialogsucces.setCancelable(false);
                    dialogsucces.show();
                }

            }
        });
    }

    private int getRequestCode(String title,String content,String gio,String ngay)
    {
        int tim=0;
        thongthao=new DatabaseEventHelper(AddEventActivity.this);
        tim=thongthao.getNotifiByToDateTime(title,content,gio,ngay);
        thongthao.close();
        return tim;
    }

    private void SetAlarm(Calendar calendar,String title,String content, int RrquestCode)
    {
        Intent intent=new Intent(AddEventActivity.this.getApplicationContext(), GetNotificalinEvent.class);
        intent.putExtra("title",title);
        intent.putExtra("time",content);
        intent.putExtra("id",RrquestCode);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(AddEventActivity.this,RrquestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager=(AlarmManager) AddEventActivity.this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }

    //
    private void GetNotificalbyEvent(Calendar calendar1,String title,String noidung1,int RequestCode)
    {
        Intent intent=new Intent(AddEventActivity.this.getApplicationContext(),AlarmReceiver.class);
        intent.putExtra("thongbao",title);
        intent.putExtra("noidung",noidung1);
        intent.putExtra("DataEvent",RequestCode);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(AddEventActivity.this,RequestCode,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager=(AlarmManager)AddEventActivity.this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent);
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
