package com.example.lastdiaryapp.Custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Event> events;
    List<Diary>diaries;
    LayoutInflater inflater;
    DatabaseEventHelper eventHelper;
    DatabaseDiaryHelper diaryHelper;

    public MyGridAdapter(@NonNull Context context,List<Date>dates,Calendar currentDate, List<Event> events,List<Diary>diaries) {
        super(context, R.layout.single_cell_layout);

        this.dates=dates;
        this.currentDate=currentDate;
        this.events=events;
        this.diaries=diaries;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        diaryHelper=new DatabaseDiaryHelper(getContext());
        eventHelper=new DatabaseEventHelper(getContext());
        events=eventHelper.listAll();
        diaries=diaryHelper.listAll();
        Date monthDate=dates.get(position);
        Calendar dateCalendar=Calendar.getInstance();
        dateCalendar.setTime(monthDate);
//        String kieu=new SimpleDateFormat("dd-MM-YYYY").format(dateCalendar.getTime());
        int DayNo=dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth=dateCalendar.get(Calendar.MONTH)+1;
        int displayYear=dateCalendar.get(Calendar.YEAR);
        int currentMonth=currentDate.get(Calendar.MONTH)+1;
        int currentYear=currentDate.get(Calendar.YEAR);
//        String kieu2=new SimpleDateFormat("dd-MM-YYYY").format(currentDate.getTime());
        int today=monthDate.getDay();
        View view=convertView;
        if(view==null)
        {
            view=inflater.inflate(R.layout.single_cell_layout,parent,false);
        }


        if(displayMonth==currentMonth && displayYear==currentYear){
//            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Number=view.findViewById(R.id.calendar_date_id);
        TextView EventNumber=(TextView) view.findViewById(R.id.event_id);
        TextView DateNumber=(TextView) view.findViewById(R.id.diary_id);
        Day_Number.setText(String.valueOf(DayNo));
        Calendar eventCalendar=Calendar.getInstance();
        Calendar dayCalendar=Calendar.getInstance();
        ArrayList<String> arrayList=new ArrayList<>();
        ArrayList<String>arrayList1=new ArrayList<>();
        for (int i=0 ; i < events.size() ; i++)
        {
            eventCalendar.setTime(CovertStringToDate1(events.get(i).getDateEvent()));
            if(DayNo==eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                    && displayYear == eventCalendar.get(Calendar.YEAR))
            {
                arrayList.add(events.get(i).getTitle());
                EventNumber.setText(arrayList.size()+" E");
            }
        }
        for (int j=0 ; j < diaries.size() ; j++)
        {
            dayCalendar.setTime(CovertStringToDate1(date(diaries.get(j).getDateCreate(),diaries.get(j).getDayCreate(),diaries.get(j).getMonthCreate(),diaries.get(j).getYearCreate())));
//            eventCalendar.setTime(CovertStringToDate1(ngayhen));
            if(DayNo==dayCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == dayCalendar.get(Calendar.MONTH)+1
                    && displayYear == dayCalendar.get(Calendar.YEAR))
            {
                arrayList1.add(diaries.get(j).getTitle());
//                EventNumber.setText(arrayList.size()+"Events");
                DateNumber.setText(arrayList1.size()+" V");
            }
        }

        return view;
    }

    private Date CovertStringToDate1(String ngay)
    {
        SimpleDateFormat format=new SimpleDateFormat("dd-MMMM-yyyy");
        Date date=null;
        try {
            date=format.parse(ngay);
        }catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
    private String date(String thu,String ngay,String thang,String nam)
    {
        String string1=thu+",";
        String string2=ngay+"-";
        String string3=thang+"-";
        String string4=nam;
//        String thungay=string1.concat(string2);
//        String thangnam=string3.concat(string4);
//        String ngaythangnam=string2.concat(thangnam);
        String ngaythangnam=string2+string3+string4;
        return ngaythangnam;
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
