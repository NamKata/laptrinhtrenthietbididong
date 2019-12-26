package com.example.lastdiaryapp.ClassCrl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseEventHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION=1;
    //Database Name
    private static final String DATABASE_NAME = "ManagerEvent";

    //TABLE NAME
    private static final String TABLE_EVENT = "Event";

    //Column name
    private static final String EVENT_ID = "ID";
    private static final int EVENT_ID_COL=0;
    private static final String EVENT_TITLE = "TIEUDE";
    private static final int EVENT_TITLE_COL=1;
    private static final String EVENT_CONTENT = "NOIDUNG";
    private static final int EVENT_CONTENT_COL=2;
    private static final String EVENT_TIMECREATE = "GIOTAO";
    private static final int EVENT_TIMECREATE_COL=3;
    private static final String EVENT_DAYCREATE = "NGAYTAO";
    private static final int EVENT_DAYCREATE_COL=4;
    private static final String EVENT_DATEHEN = "NGAYHEN";
    private static final int EVENT_DATEHEN_COL=5;
    private static final String EVENT_GIOHEN = "GIOHEN";
    private static final int EVENT_GIOHEN_COL=6;
    private static final String EVENT_IMAGE = "ICON";
    private static final int EVENT_IMAGE_COL=7;
    private static final String EVENT_NOTIFICAL="THONGBAO";
    private static final int EVENT_NOTIFICAL_COL=8;
    private Context mContent;
    //CREATE TABLE

    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_EVENT + " ( " + EVENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+ EVENT_TITLE+ " TEXT ," + EVENT_CONTENT +" TEXT ," + EVENT_TIMECREATE +" TEXT ," + EVENT_DAYCREATE +" TEXT ," + EVENT_DATEHEN + " TEXT ,"+EVENT_GIOHEN +" TEXT," +EVENT_IMAGE+" INTERGER ," + EVENT_NOTIFICAL +" NUMERIC);";

    //DROP TABLE IF EXISTS
    private static final String DROP_TABLE =" DROP TABLE IF EXISTS " +TABLE_EVENT;

    public DatabaseEventHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContent=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addEvent(Event event)
    {
        try
        {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(EVENT_TITLE,event.getTitle());
            cv.put(EVENT_CONTENT,event.getContent());
            cv.put(EVENT_TIMECREATE,event.getTimeCreate());
            cv.put(EVENT_DAYCREATE,event.getDateCreate());
            cv.put(EVENT_DATEHEN,event.getDateEvent());
            cv.put(EVENT_GIOHEN,event.getTimeEvent());
            cv.put(EVENT_IMAGE,event.getPhoto());
            cv.put(EVENT_NOTIFICAL,event.getNotifical());
            db.insert(TABLE_EVENT,null,cv);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Event getEventByID(int ID)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_EVENT,new String[]{EVENT_ID,EVENT_TITLE,EVENT_CONTENT,EVENT_TIMECREATE,EVENT_DAYCREATE,EVENT_DATEHEN,EVENT_GIOHEN,EVENT_IMAGE,EVENT_NOTIFICAL},EVENT_ID +" =?",new String[]{String.valueOf(ID)},null,null,null);
        if(cursor != null)cursor.moveToFirst();
        Event event=new Event(Integer.parseInt(cursor.getString(EVENT_ID_COL)),
                cursor.getString(EVENT_TITLE_COL), cursor.getString(EVENT_CONTENT_COL), cursor.getString(EVENT_TIMECREATE_COL),
                cursor.getString(EVENT_DAYCREATE_COL), cursor.getString(EVENT_DATEHEN_COL), cursor.getString(EVENT_GIOHEN_COL),Integer.parseInt(cursor.getString(EVENT_IMAGE_COL)), cursor.getString(EVENT_NOTIFICAL_COL));
        return event;
    }

    public List<Event> listAll()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Event> lst=new ArrayList<>();
        //Select All
        Cursor cursor=db.query(TABLE_EVENT,new String[]{EVENT_ID,EVENT_TITLE,EVENT_CONTENT,EVENT_TIMECREATE,EVENT_DAYCREATE,EVENT_DATEHEN,EVENT_GIOHEN,EVENT_IMAGE,EVENT_NOTIFICAL},null,null,null,null,EVENT_DAYCREATE,null);
        if(cursor.moveToFirst())
        {
            do
            {
                Event event=new Event();
                event.setID(Integer.parseInt(cursor.getString(EVENT_ID_COL)));
                event.setTitle(cursor.getString(EVENT_TITLE_COL));
                event.setContent(cursor.getString(EVENT_CONTENT_COL));
                event.setTimeCreate(cursor.getString(EVENT_TIMECREATE_COL));
                event.setDateCreate(cursor.getString(EVENT_DAYCREATE_COL));
                event.setDateEvent(cursor.getString(EVENT_DATEHEN_COL));
                event.setTimeEvent(cursor.getString(EVENT_GIOHEN_COL));
                event.setPhoto(Integer.parseInt(cursor.getString(EVENT_IMAGE_COL)));
                event.setNotifical(cursor.getString(EVENT_NOTIFICAL_COL));
                lst.add(event);
            }while (cursor.moveToNext());
        }
        cursor.requery();
        return lst;
    }

    public void deleteEvent(Event event)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_EVENT,EVENT_ID+ " = ?", new String[]{String.valueOf(event.getID())});
        db.close();
    }

    public int updateDiary(Event event)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(EVENT_TITLE,event.getTitle());
        values.put(EVENT_CONTENT,event.getContent());
        values.put(EVENT_IMAGE,event.getPhoto());
        values.put(EVENT_DATEHEN,event.getDateEvent());
        values.put(EVENT_GIOHEN,event.getTimeEvent());
        values.put(EVENT_NOTIFICAL,event.getNotifical());
        return db.update(TABLE_EVENT, values, EVENT_ID+" = ? AND "+ EVENT_TIMECREATE+ "= ? AND "+ EVENT_DAYCREATE+" = ? ",new String[]{String.valueOf(event.getID()),String.valueOf(event.getTimeCreate()),String.valueOf(event.getDateCreate())});
    }

    public List<Event> listSearch(String search)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Event> lst=new ArrayList<>();
        //Select All
//        String[] selectionTable={KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TIME,KEY_DAY,KEY_DATE,KEY_MONTH,KEY_YEAR,KEY_IMAGE};
//        String selection=KEY_TITLE+ "LIKE '%' ? AND "+ KEY_CONTENT+" LIKE '%' ?";
//        String[] selectionArgs={search+"'%'"};
        String selection=" SELECT * FROM "+ TABLE_EVENT+ " WHERE "+ EVENT_TITLE +" LIKE '%"+search +"__%' OR " +EVENT_CONTENT+" LIKE '%"+ search+"__%'";

        Cursor cursor=db.rawQuery(selection,null);
        if(cursor.getCount() > 0)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                    Event event=new Event();
                    event.setID(Integer.parseInt(cursor.getString(0)));
                    event.setID(Integer.parseInt(cursor.getString(EVENT_ID_COL)));
                    event.setTitle(cursor.getString(EVENT_TITLE_COL));
                    event.setContent(cursor.getString(EVENT_CONTENT_COL));
                    event.setTimeCreate(cursor.getString(EVENT_TIMECREATE_COL));
                    event.setDateCreate(cursor.getString(EVENT_DAYCREATE_COL));
                    event.setDateEvent(cursor.getString(EVENT_DATEHEN_COL));
                    event.setTimeEvent(cursor.getString(EVENT_GIOHEN_COL));
                    event.setPhoto(Integer.parseInt(cursor.getString(EVENT_IMAGE_COL)));
                    event.setNotifical(cursor.getString(EVENT_NOTIFICAL_COL));
                    lst.add(event);
                }while (cursor.moveToNext());
            }
        }
        else {
            Toast.makeText(mContent,"Khong co",Toast.LENGTH_LONG).show();
        }
        cursor.requery();

        return lst;
    }

    public int getNotifiByToDateTime(String title,String content,String gio, String ngay)
    {
        int tim=0;
        ArrayList<Event> eventArrayList=new ArrayList<>();
        String[] cot={EVENT_ID,EVENT_NOTIFICAL};
        String selection=EVENT_TITLE+" = ? and "+ EVENT_CONTENT+"= ? and " + EVENT_GIOHEN +"= ? and " + EVENT_DATEHEN +" = ? ";
        String[] dk={title,content,gio,ngay};
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(TABLE_EVENT,cot,selection,dk,null,null,null);
        while (cursor.moveToNext())
        {
            tim=cursor.getInt(cursor.getColumnIndex(EVENT_ID));
        }
        cursor.close();
        db.close();
        return tim;
    }
    public List<Event> ListEventCovertBy(String ngayhen)
    {
        Log.i(DATABASE_NAME, "MyDatabaseHelper.getAllDiary ... ");

        List<Event> eventList = new ArrayList<Event>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + TABLE_EVENT+ " WHERE "+EVENT_DATEHEN+" = ? ";
        String[] selecttionArg={ngayhen};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,selecttionArg);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Event event=new Event();
                event.setID(Integer.parseInt(cursor.getString(EVENT_ID_COL)));
                event.setTitle(cursor.getString(EVENT_TITLE_COL));
                event.setContent(cursor.getString(EVENT_CONTENT_COL));
                event.setTimeCreate(cursor.getString(EVENT_TIMECREATE_COL));
                event.setDateCreate(cursor.getString(EVENT_DAYCREATE_COL));
                event.setDateEvent(cursor.getString(EVENT_DATEHEN_COL));
                event.setTimeEvent(cursor.getString(EVENT_GIOHEN_COL));
                event.setPhoto(Integer.parseInt(cursor.getString(EVENT_IMAGE_COL)));
                event.setNotifical(cursor.getString(EVENT_NOTIFICAL_COL));
                // Thêm vào danh sách.
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        cursor.requery();
        // return note list
        return eventList;
    }
}

