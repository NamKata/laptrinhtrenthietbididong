package com.example.lastdiaryapp.Custom;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalendarView  extends LinearLayout {

    ImageView NextButton,PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS=42;
    Calendar calendar=Calendar.getInstance();
    Context context;
    List<Date> dates=new ArrayList<>();
    AlertDialog alertDialog;
    List<Diary> diaryList=new ArrayList<Diary>();
    List<Event> eventsList=new ArrayList<Event>();
    SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM yyyy");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MMMM");
    SimpleDateFormat yearFormate=new SimpleDateFormat("yyyy");
    SimpleDateFormat eventDateFormate=new SimpleDateFormat("dd-MMMM-yyyy");
    DatabaseDiaryHelper dbDiaryOpen;
    DatabaseEventHelper dbEventOpen;
    MyGridAdapter myGridAdapter;

    public CustomCalendarView(Context context) {
        super(context);
    }
    public CustomCalendarView(final Context context, @NonNull AttributeSet attrs) {
        super(context,attrs);
        this.context=context;
        IntializeLayout();
        SetUpCalendar();
        dbDiaryOpen=new DatabaseDiaryHelper(context);
        dbEventOpen=new DatabaseEventHelper(context);

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String date=eventDateFormate.format(dates.get(position));
//                List<Event> eventList=dbEventOpen.ListEventCovertBy(date);
//                String ngay=new SimpleDateFormat("dd").format(dates.get(position));
//                String thang=new SimpleDateFormat("MMMM",Locale.ENGLISH).format(dates.get(position));
//                String nam=new SimpleDateFormat("yyyy").format(dates.get(position));
//                List<Diary>diaryList=dbDiaryOpen.listDiaryByDMY(ngay,thang,nam);
//                String sizeEvent=""+eventList.size();
//                String sizeDiary=""+diaryList.size();
//                Toast.makeText(context,sizeEvent+"Event"+sizeDiary,Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private void IntializeLayout()
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.calendar_custom,this);
        NextButton=(ImageView) view.findViewById(R.id.nextBtn);
        PreviousButton=(ImageView)view.findViewById(R.id.prevBtn);
        CurrentDate=(TextView) view.findViewById(R.id.display_current_date);
        gridView=(GridView)view.findViewById(R.id.calendar_grid);
    }
    public void SetUpCalendar()
    {
        String currwntDate=dateFormat.format(calendar.getTime());
        CurrentDate.setText(currwntDate);
        dates.clear();
        Calendar monthCalendar=(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayofMonth);
//        String ngayhen=new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
////        CollectEventPerDMY(ngayhen);
        dbEventOpen=new DatabaseEventHelper(context);
        eventsList=dbEventOpen.ListEventCovertBy(eventDateFormate.format(calendar.getTime()));
        dbDiaryOpen=new DatabaseDiaryHelper(context);
        diaryList=dbDiaryOpen.listAll();
//        CollectDiaryPerMY(monthFormat.format(calendar.getTime()),yearFormate.format(calendar.getTime()));
        while (dates.size() < MAX_CALENDAR_DAYS)
        {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        myGridAdapter=new MyGridAdapter(context,dates,calendar,eventsList,diaryList);
        myGridAdapter.notifyDataSetChanged();
        gridView.setAdapter(myGridAdapter);
    }
    private void CollectDiaryPerMY(String Month,String year)
    {
        DatabaseDiaryHelper db=new DatabaseDiaryHelper(context);
        diaryList= db.listDiaryByMonthandYear(Month,year);
        db.close();
    }
    private void CollectEventPerDMY(String ngayhen)
    {
        DatabaseEventHelper db=new DatabaseEventHelper(context);
        eventsList=db.ListEventCovertBy(ngayhen);
        db.close();
    }
}
