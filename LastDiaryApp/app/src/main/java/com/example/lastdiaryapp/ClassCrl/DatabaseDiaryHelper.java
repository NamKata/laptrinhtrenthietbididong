package com.example.lastdiaryapp.ClassCrl;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDiaryHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION=1;
    //Database Name
    private static final String DATABASE_NAME = "ManagerDiary";

//    //Table
//    private static final String TABLE_NANE_MN="QLDiary";
//    //Column
//    private static final String MN_ID="ID";
//    private static final String MN_TITLE="TIEUDE";
//    private static final String MN_DATE="NGAYTAO";

    //TABLE NAME
    private static final String TABLE_NAME = "Diary";

    //Column name
    private static final String KEY_ID = "ID";
    private static final int KEY_ID_COL=0;
    private static final String KEY_TITLE = "TIEUDE";
    private static final int KEY_TITLE_COL=1;
    private static final String KEY_CONTENT = "NOIDUNG";
    private static final int KEY_CONTENT_COL=2;
    private static final String KEY_TIME = "GIOTAO";
    private static final int KEY_TIME_COL=3;
    private static final String KEY_DAY = "THU";
    private static final int KEY_DAY_COL=4;
    private static final String KEY_DATE = "NGAY";
    private static final int KEY_DATE_COL=5;
    private static final String KEY_MONTH = "THANG";
    private static final int KEY_MONTH_COL=6;
    private static final String KEY_YEAR = "NAM";
    private static final int KEY_YEAR_COL=7;
    private static final String KEY_IMAGE = "ANH";
    private static final int KEY_IMAGE_COL=8;
    private static final String KEY_STT="TINHTRANG";
    private static final int KEY_STT_COL=9;
    private static final String KEY_PASS="MATKHAU";
    private static final int KEY_PASS_COL=10;
    private Context mContent;
    //CREATE TABLE

    private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + " ( " + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+ KEY_TITLE+ " TEXT ," + KEY_CONTENT +" TEXT ," + KEY_TIME +" TEXT ," + KEY_DAY +" TEXT ," + KEY_DATE + " TEXT ,"+KEY_MONTH +" TEXT," +KEY_YEAR+" TEXT ," + KEY_IMAGE +" BLOB, "+ KEY_STT +" TEXT ," +KEY_PASS+" TEXT );" ;

    //DROP TABLE IF EXISTS
    private static final String DROP_TABLE =" DROP TABLE IF EXISTS " +TABLE_NAME;

    public DatabaseDiaryHelper(@Nullable Context context) {
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

    public long addDiary(Diary diary)
    {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put(KEY_TITLE,diary.getTitle());
            cv.put(KEY_CONTENT,diary.getContent());
            cv.put(KEY_TIME,diary.getTimeCreate());
            cv.put(KEY_DAY,diary.getDayCreate());
            cv.put(KEY_DATE,diary.getDateCreate());
            cv.put(KEY_MONTH,diary.getMonthCreate());
            cv.put(KEY_YEAR,diary.getYearCreate());
            cv.put(KEY_IMAGE,diary.getPhoto());
            cv.put(KEY_STT,diary.getStatusLock());
            cv.put(KEY_PASS,diary.getPassword());
            long tao= db.insert(TABLE_NAME,null,cv);
            return tao;
    }

    public Diary getDiaryByID(int ID)
    {
        SQLiteDatabase db=this.getReadableDatabase();
//      new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TIME,KEY_DAY,KEY_DATE,KEY_MONTH,KEY_YEAR,KEY_IMAGE,KEY_STT,KEY_PASS}
        Cursor cursor=db.query(TABLE_NAME,null,KEY_ID +" =?",new String[]{String.valueOf(ID)},null,null,null);
        if(cursor != null)cursor.moveToFirst();
        Diary diary=new Diary(Integer.parseInt(cursor.getString(KEY_ID_COL)),
                cursor.getString(KEY_TITLE_COL), cursor.getString(KEY_CONTENT_COL), cursor.getString(KEY_TIME_COL),
                cursor.getString(KEY_DAY_COL), cursor.getString(KEY_DATE_COL), cursor.getString(KEY_MONTH_COL),cursor.getString(KEY_YEAR_COL),cursor.getBlob(KEY_IMAGE_COL),cursor.getString(KEY_STT_COL),cursor.getString(KEY_PASS_COL));
        return diary;
    }

    public List<Diary> listAll()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Diary> lst=new ArrayList<>();
        //Select All
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TIME,KEY_DAY,KEY_DATE,KEY_MONTH,KEY_YEAR,KEY_IMAGE,KEY_STT,KEY_PASS},null,null,null,null,KEY_DATE,null);
        if(cursor.moveToFirst())
        {
            do
            {
                Diary diary=new Diary();
                diary.setID(Integer.parseInt(cursor.getString(KEY_ID_COL)));
                diary.setTitle(cursor.getString(KEY_TITLE_COL));
                diary.setContent(cursor.getString(KEY_CONTENT_COL));
                diary.setTimeCreate(cursor.getString(KEY_TIME_COL));
                diary.setDayCreate(cursor.getString(KEY_DAY_COL));
                diary.setDateCreate(cursor.getString(KEY_DATE_COL));
                diary.setMonthCreate(cursor.getString(KEY_MONTH_COL));
                diary.setYearCreate(cursor.getString(KEY_YEAR_COL));
                diary.setPhoto(cursor.getBlob(KEY_IMAGE_COL));
                diary.setStatusLock(cursor.getString(KEY_STT_COL));
                diary.setPassword(cursor.getString(KEY_PASS_COL));
                lst.add(diary);
            }while (cursor.moveToNext());
        }
        cursor.requery();

        return lst;
    }

    public int deleteDiary(Diary diary)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        int xoa=db.delete(TABLE_NAME,KEY_ID+ " = ?", new String[]{String.valueOf(diary.getID())});
        db.close();
        return xoa;
    }

    public int updateDiary(Diary diary)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(KEY_TITLE,diary.getTitle());
        cv.put(KEY_CONTENT,diary.getContent());
        cv.put(KEY_TIME,diary.getTimeCreate());
        cv.put(KEY_DAY,diary.getDayCreate());
        cv.put(KEY_DATE,diary.getDateCreate());
        cv.put(KEY_MONTH,diary.getMonthCreate());
        cv.put(KEY_YEAR,diary.getYearCreate());
        cv.put(KEY_IMAGE,diary.getPhoto());
        cv.put(KEY_STT,diary.getStatusLock());
        cv.put(KEY_PASS,diary.getPassword());

         int y=  db.update(TABLE_NAME,cv,KEY_ID+" = ? ",new String[]{String.valueOf(diary.getID())});


        db.close();
        return y;
    }

    public List<Diary> listSearch(String search)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<Diary> lst=new ArrayList<>();
        //Select All
//        String[] selectionTable={KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_TIME,KEY_DAY,KEY_DATE,KEY_MONTH,KEY_YEAR,KEY_IMAGE};
//        String selection=KEY_TITLE+ "LIKE '%' ? AND "+ KEY_CONTENT+" LIKE '%' ?";
//        String[] selectionArgs={search+"'%'"};
        String selection=" SELECT * FROM "+ TABLE_NAME+ " WHERE "+ KEY_TITLE +" LIKE '%"+search +"__%' OR " +KEY_CONTENT+" LIKE '%"+ search+"__%'";

         Cursor cursor=db.rawQuery(selection,null);
         if(cursor.getCount() > 0)
         {
             if(cursor.moveToFirst())
             {
                 do
                 {
                     Diary diary=new Diary();
                     diary.setID(Integer.parseInt(cursor.getString(KEY_ID_COL)));
                     diary.setTitle(cursor.getString(KEY_TITLE_COL));
                     diary.setContent(cursor.getString(KEY_CONTENT_COL));
                     diary.setTimeCreate(cursor.getString(KEY_TIME_COL));
                     diary.setDayCreate(cursor.getString(KEY_DAY_COL));
                     diary.setDateCreate(cursor.getString(KEY_DATE_COL));
                     diary.setMonthCreate(cursor.getString(KEY_MONTH_COL));
                     diary.setYearCreate(cursor.getString(KEY_YEAR_COL));
                     diary.setPhoto(cursor.getBlob(KEY_IMAGE_COL));
                     diary.setStatusLock(cursor.getString(KEY_STT_COL));
                     diary.setPassword(cursor.getString(KEY_PASS_COL));
                     lst.add(diary);
                 }while (cursor.moveToNext());
             }
         }
         else {
             Toast.makeText(mContent,"Khong co",Toast.LENGTH_LONG).show();
         }
         cursor.requery();

        return lst;
    }
    public  List<Diary> listDiaryByMonthandYear(String month,String year)
    {
        Log.i(DATABASE_NAME, "MyDatabaseHelper.getAllDiary ... ");

        List<Diary> diaryList = new ArrayList<Diary>();
        // Select All Query
        String selectQuery = " SELECT  * FROM " + TABLE_NAME+ " WHERE "+KEY_MONTH+" = ? AND "+KEY_YEAR+ "= ?";
        String[] selecttionArg={month,year};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,selecttionArg);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Diary diary=new Diary();
                diary.setID(Integer.parseInt(cursor.getString(KEY_ID_COL)));
                diary.setTitle(cursor.getString(KEY_TITLE_COL));
                diary.setContent(cursor.getString(KEY_CONTENT_COL));
                diary.setTimeCreate(cursor.getString(KEY_TIME_COL));
                diary.setDayCreate(cursor.getString(KEY_DAY_COL));
                diary.setDateCreate(cursor.getString(KEY_DATE_COL));
                diary.setMonthCreate(cursor.getString(KEY_MONTH_COL));
                diary.setYearCreate(cursor.getString(KEY_YEAR_COL));
                diary.setPhoto(cursor.getBlob(KEY_IMAGE_COL));
                diary.setStatusLock(cursor.getString(KEY_STT_COL));
                diary.setPassword(cursor.getString(KEY_PASS_COL));
                // Thêm vào danh sách.
                diaryList.add(diary);
            } while (cursor.moveToNext());
        }
        // return note list
        return diaryList;
    }
    public  List<Diary> listDiaryByDMY(String day,String month,String year)
    {
        Log.i(DATABASE_NAME, "MyDatabaseHelper.getAllDiary ... ");

        List<Diary> diaryList = new ArrayList<Diary>();
        // Select All Query
//        String selectQuery = " SELECT  * FROM " + TABLE_NAME+ " WHERE "+KEY_DATE+" = ? AND "+KEY_MONTH+" = ? AND "+KEY_YEAR+ "= ?";
//        String[] selecttionArg={day,month,year};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,KEY_DATE+" = ? AND "+KEY_MONTH+" = ? AND "+KEY_YEAR+" = ?",new String[]{day,month,year},null,null,null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Diary diary=new Diary();
                diary.setID(Integer.parseInt(cursor.getString(KEY_ID_COL)));
                diary.setTitle(cursor.getString(KEY_TITLE_COL));
                diary.setContent(cursor.getString(KEY_CONTENT_COL));
                diary.setTimeCreate(cursor.getString(KEY_TIME_COL));
                diary.setDayCreate(cursor.getString(KEY_DAY_COL));
                diary.setDateCreate(cursor.getString(KEY_DATE_COL));
                diary.setMonthCreate(cursor.getString(KEY_MONTH_COL));
                diary.setYearCreate(cursor.getString(KEY_YEAR_COL));
                diary.setPhoto(cursor.getBlob(KEY_IMAGE_COL));
                diary.setStatusLock(cursor.getString(KEY_STT_COL));
                diary.setPassword(cursor.getString(KEY_PASS_COL));
                // Thêm vào danh sách.
                diaryList.add(diary);
            } while (cursor.moveToNext());
        }
        // return note list
        return diaryList;
    }
}
