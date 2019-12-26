package com.example.lastdiaryapp.AddOrEditCrl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastdiaryapp.AddOrEditEventCrl.AddEventActivity;
import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.DbBitmapUtility;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private TextView tvEDM;
    private TextView tvY;
    private EditText tvTitle;
    private EditText tvContent;
    private ImageView tvimg;
    private ImageButton back;
    private Button close;
    private Button save;
    private ImageButton choose;
    private ImageButton icons;
    private ImageButton calendar;
    private Dialog dialog;
    private Dialog dialog_img;
    private  Bitmap bitmap;
    final int REQUEST_CODE_GALLERY=999;
    LinearLayout ll3;
    Uri uri_image;
    final int REQUEST_CODE=99;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar1;
    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    private ImageButton btnKhoa;
    private Boolean setstt=false;
    private int nen,chu,nentrong,nut,nuthatbai,nutthanhcong;

/////
    private EditText mk1,mk2;
    private Dialog dialog_lock,dialog_error,dialog_success,dialog_loading;
    //Database
    DatabaseDiaryHelper db;

    //Convert Image to Bitmap from byte
    DbBitmapUtility covert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db=new DatabaseDiaryHelper(AddActivity.this);
        covert=new DbBitmapUtility();
        tvEDM=(TextView)findViewById(R.id.tvEDM);
        tvY=(TextView)findViewById(R.id.tvY);
        tvTitle=(EditText) findViewById(R.id.tvTitleAdd);
        tvContent=(EditText)findViewById(R.id.tvContentAdd);
        tvimg=(ImageView)findViewById(R.id.imgADD);
        back=(ImageButton)findViewById(R.id.btnClose1);//Nút mũi tên
        close=(Button) findViewById(R.id.btnClose);//Nút đóng
        save=(Button) findViewById(R.id.btnSaveAdd);
        choose=(ImageButton)findViewById(R.id.btnChoose);
        btnKhoa=(ImageButton)findViewById(R.id.btnLock);
        icons=(ImageButton)findViewById(R.id.btnicon);
        ll3=(LinearLayout)findViewById(R.id.ll3);
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.addLayout);
        LinearLayout goc=(LinearLayout)findViewById(R.id.goc);
        final SharedPreferences setcolor= getSharedPreferences("COLOR", 0);

        if(setcolor.getInt(getString(R.string.Mauchu),0)==0 && setcolor.getInt(getString(R.string.background),0)==0 && setcolor.getInt(getString(R.string.Maunut),0)==0 &&setcolor.getInt(getString(R.string.Maunutthanhcong),0)==0 && setcolor.getInt(getString(R.string.Maunutthatbai),0)==0 && setcolor.getInt(getString(R.string.Maunentrong),0)==0)
        {
//            chu=Color.rgb(255,255,255);
            chu=Color.rgb(255,96,96);
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
        goc.setBackgroundColor(nentrong);
        relativeLayout.setBackgroundColor(nen);
        tvY.setTextColor(chu);
        tvEDM.setTextColor(chu);
        tvContent.setTextColor(chu);
        tvTitle.setTextColor(chu);
        close.setBackgroundColor(nut);
        close.setTextColor(chu);
        save.setBackgroundColor(nut);
        save.setTextColor(chu);
        SharedPreferences settings=getSharedPreferences("SIZE",0);
        String sizeTitle=settings.getString(getString(R.string.sizetieude),"");
        String sizeContent=settings.getString(getString(R.string.SizeNoidung),"");
        String sizeNgay=settings.getString(getString(R.string.SizeNgay),"");
        String sizeGio=settings.getString(getString(R.string.SizeGio),"");
        String sizeNut=settings.getString(getString(R.string.SizeNut),"");
        if(sizeTitle.equals("")&&sizeContent.equals("") && sizeNgay.equals("") && sizeNut.equals("") && sizeGio.equals(""))
        {

        }
        else
        {
            tvTitle.setTextSize(Float.valueOf(sizeTitle));
            tvContent.setTextSize(Float.valueOf(sizeContent));
            tvEDM.setTextSize(Float.valueOf(sizeNgay));
            tvY.setTextSize(Float.valueOf(sizeNgay));
            save.setTextSize(Float.valueOf(sizeNut));
            close.setTextSize(Float.valueOf(sizeNut));
        }
        tvTitle.setTypeface(settype());
        tvContent.setTypeface(settype());
        tvEDM.setTypeface(settype());
        tvY.setTypeface(settype());
        save.setTypeface(settype());
        close.setTypeface(settype());


        final String gio=new SimpleDateFormat("K:mm a").format(new Date());
        final String thu=new SimpleDateFormat("EEEE").format(new Date());
        final String ngay=new SimpleDateFormat("dd").format(new Date());
//        final String thang=new SimpleDateFormat("MMMM").format(new Date());
        final String thang=new SimpleDateFormat("MMMM").format(new Date());
        final String nam=new SimpleDateFormat("YYYY").format(new Date());


        dialog_lock=new Dialog(AddActivity.this);
        dialog_lock.setContentView(R.layout.dialog_lock);
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton close=dialog_lock.findViewById(R.id.closeLock);
                mk1=dialog_lock.findViewById(R.id.nhapkhoa);
                mk2=dialog_lock.findViewById(R.id.nhapkhoa1);
                Button oke=dialog_lock.findViewById(R.id.create);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_lock.cancel();
                    }
                });
                oke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mk2.getText().toString().equals(mk1.getText().toString()))
                        {
                            Toast.makeText(AddActivity.this,"Tạo khóa thành công",Toast.LENGTH_SHORT).show();
                            dialog_lock.cancel();
                            setstt=true;
                        }
                        else
                        {
                            Toast.makeText(AddActivity.this,"Mật khẩu ko giống nhau",Toast.LENGTH_SHORT).show();
                            mk2.setText("");
                            mk1.setText("");
                        }
                    }
                });
                dialog_lock.setCancelable(false);
                dialog_lock.show();
            }
        });

//        if(tvTitle.getText().toString().length()>30)
//        {
//            tvTitle.setError(getString(R.string.quasodong));
//        }
//
//        if(tvContent.getText().toString().length()>500)
//        {
//            tvContent.setError(getString(R.string.quasodong));
//        }
        tvY.setText(nam);
        tvEDM.setText(thu+","+ngay +""+ thang);
        if(tvimg.getDrawable()==null)
        {
            ll3.setVisibility(View.GONE);
        }

        dialog=new Dialog(AddActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setContentView(R.layout.dialog);

        //Quay lại trang trước đó
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Hủy có hiện thông báo
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();// từ từ
                dialog.show();
                dialog.setCancelable(false);
                Button btnOk=(Button)dialog.findViewById(R.id.co);
                Button btnKhong=(Button)dialog.findViewById(R.id.khong);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                btnKhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });

        //Lưu
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!validate())
//                {
//                    return;
//                }
                if(bitmap!=null)
                {
                    long tao;
                    byte[] image=covert.getBytes(bitmap);
                    if(tvTitle.getText().toString().equals("")||tvContent.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),
                                "Please enter title & content", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(setstt==true)
                    {
                        Diary diary=new Diary(tvTitle.getText().toString(),tvContent.getText().toString(),gio,thu,ngay,thang,nam,image,"true",mk1.getText().toString());
                        tao= db.addDiary(diary);
                    }
                    else
                    {
                        Diary diary=new Diary(tvTitle.getText().toString(),tvContent.getText().toString(),gio,thu,ngay,thang,nam,image,"false","");
                       tao= db.addDiary(diary);
                    }
                    final Dialog dialog_sucess=new Dialog(AddActivity.this);
                    dialog_sucess.setContentView(R.layout.dialog_success);
                    final Dialog dialog_error=new Dialog(AddActivity.this);
                    dialog_error.setContentView(R.layout.dialog_error);
                    if(tao > 0)
                    {
                        Button xacnhanthanhcong=(Button)dialog_sucess.findViewById(R.id.xacnhan);
                        xacnhanthanhcong.setTypeface(settype());
                        xacnhanthanhcong.setTextColor(chu);
                        xacnhanthanhcong.setBackgroundColor(nutthanhcong);
                        xacnhanthanhcong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_sucess.cancel();
                                onBackPressed();
                            }
                        });
                        dialog_sucess.setCancelable(false);
                        dialog_sucess.show();
                    }
                    else
                    {
                        Button xacnhanthatbai=(Button)dialog_sucess.findViewById(R.id.xacnhanerror);
                        xacnhanthatbai.setTypeface(settype());
                        xacnhanthatbai.setTextColor(chu);
                        xacnhanthatbai.setBackgroundColor(nuthatbai);
                        xacnhanthatbai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_error.cancel();
                                onBackPressed();
                            }
                        });
                        dialog_error.setCancelable(false);
                        dialog_error.show();
                    }
                }
                else
                {
                    long tao;
                    if(tvTitle.getText().toString().equals("")||tvContent.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),
                                "Please enter title & content", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(setstt==true)
                    {
                        Diary diary=new Diary(tvTitle.getText().toString(),tvContent.getText().toString(),gio,thu,ngay,thang,nam,null,"true",mk1.getText().toString());
                       tao= db.addDiary(diary);
                    }
                    else
                    {
                        Diary diary=new Diary(tvTitle.getText().toString(),tvContent.getText().toString(),gio,thu,ngay,thang,nam,null,"false",null);
                       tao= db.addDiary(diary);
                    }
                    final Dialog dialog_sucess=new Dialog(AddActivity.this);
                    dialog_sucess.setContentView(R.layout.dialog_success);
                    final Dialog dialog_error=new Dialog(AddActivity.this);
                    dialog_error.setContentView(R.layout.dialog_error);
                    if(tao > 0)
                    {
                        Button xacnhanthanhcong=(Button)dialog_sucess.findViewById(R.id.xacnhan);
                        xacnhanthanhcong.setTypeface(settype());
                        xacnhanthanhcong.setTextColor(chu);
                        xacnhanthanhcong.setBackgroundColor(nutthanhcong);
                        xacnhanthanhcong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_sucess.cancel();
                                onBackPressed();
                            }
                        });
                        dialog_sucess.setCancelable(false);
                        dialog_sucess.show();
                    }
                    else
                    {
                        Button xacnhanthatbai=(Button)dialog_sucess.findViewById(R.id.xacnhanerror);
                        xacnhanthatbai.setTypeface(settype());
                        xacnhanthatbai.setTextColor(chu);
                        xacnhanthatbai.setBackgroundColor(nuthatbai);
                        xacnhanthatbai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_error.cancel();
                                onBackPressed();
                            }
                        });
                        dialog_error.setCancelable(false);
                        dialog_error.show();
                    }
                }
            }
        });


        //Chọn ảnh
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(ActivityCompat.checkSelfPermission(AddActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(AddActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
//                    return;
//                }
//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//                intent.setType("image/*");
////                startActivityForResult(Intent.createChooser(intent,"Chọn Ảnh"),REQUEST_CODE_GALLERY);
//                startActivityForResult(intent,REQUEST_CODE_GALLERY);
               // selectImage();

//                if(ActivityCompat.checkSelfPermission(AddActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(AddActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
//                    return;
//                }

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //startActivityForResult(intent,REQUEST_CODE_GALLERY);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(Intent.createChooser(intent,"Choọn Ảnh"),REQUEST_CODE_GALLERY);
            }
        });


        //Chon icon
        icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        //Chọn ngày
//        calendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar1=Calendar.getInstance();
//                year=calendar1.get(Calendar.YEAR);
//                month=calendar1.get(Calendar.MONTH);
//                dayOfMonth=calendar1.get(Calendar.DAY_OF_MONTH);
//                 datePickerDialog=new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view,int year, int month, int dayOfMonth) {
//                        tvEDM.setText(dayOfMonth+"/"+month+"/"+year);
//
//                    }
//                },year,month,dayOfMonth);
//                datePickerDialog.show();
//            }
//        });

        dialog_img=new Dialog(AddActivity.this);
        dialog_img.setContentView(R.layout.dialog_img);
        tvimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton closeX=(ImageButton) dialog_img.findViewById(R.id.btnClose1111);
                ImageView imageView=(ImageView)dialog_img.findViewById(R.id.ivDia);
                imageView.setImageBitmap(bitmap);
                closeX.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_img.cancel();
                    }
                });
                dialog_img.setCancelable(false);
                dialog_img.show();
            }
        });
    }

    public void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.noi));
        try {
            if(intent.resolveActivity(getPackageManager())!=null)
            {
                startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
            }
            else
            {
                Toast.makeText(this,"Your Device Don't Support Speech Input",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data != null) {
//            Uri uri=data.getData() ;
//            try {
//                InputStream inputStream=getContentResolver().openInputStream(uri);
//
//                bitmap= BitmapFactory.decodeStream(inputStream);
//                tvimg.setImageBitmap(bitmap);
//                ll3.setVisibility(View.VISIBLE);
//            }
//            catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//            }
////            final List<Bitmap> bitmaps = new ArrayList<>();
////            ClipData clipData = data.getClipData();
////            if (clipData != null) {
////                for (int i = 0; i < clipData.getItemCount(); i++) {
////                    Uri imagUr = clipData.getItemAt(i).getUri();
////                    try {
////                        InputStream is = getContentResolver().openInputStream(imagUr);
////                        bitmap  = BitmapFactory.decodeStream(is);
////                        bitmaps.add(bitmap);
////                        tvimg.setImageBitmap(bitmap);
////                    } catch (FileNotFoundException e) {
////                        e.printStackTrace();
////                    }
////                }
////            } else {
////                Uri imgUri = data.getData();
////                try{
////                    InputStream is = getContentResolver().openInputStream(imgUri);
////                    bitmap=BitmapFactory.decodeStream(is);
////                    bitmaps.add(bitmap);
////                }
////                catch (FileNotFoundException e)
////                {
////                    e.printStackTrace();
////                }
////
////            }
////
////            new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    for  (final Bitmap b : bitmaps) {
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                tvimg.setImageBitmap(b);
////                                ll3.setVisibility(View.VISIBLE);
////                            }
////                        });
////                        try {
////                            Thread.sleep(1000);
////                        }catch (InterruptedException e)
////                        {
////                            e.printStackTrace();
////                        }
////                    }
////                }
////            }).start();
//
//
//        }
//        else if(requestCode==REQUEST_CODE && requestCode==RESULT_OK  )
//        {
//             bitmap=(Bitmap)data.getExtras().get("data");
//             tvimg.setImageBitmap(bitmap);
//             ll3.setVisibility(View.VISIBLE);
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SPEECH_INPUT && resultCode==RESULT_OK && data!=null)
        {
            ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(tvTitle.getText().toString().equals(""))
            {
                tvTitle.setText(result.get(0));
            }
            else
            {
                tvContent.setText(result.get(0));
            }

        }else if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData() ;
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);

                bitmap= BitmapFactory.decodeStream(inputStream);
                tvimg.setImageBitmap(bitmap);
                ll3.setVisibility(View.VISIBLE);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
//        switch (requestCode)
//        {
//            case REQUEST_CODE_SPEECH_INPUT:
//               break;
//            case REQUEST_CODE_GALLERY:
//                if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null)
//                {
//                    Uri uri=data.getData() ;
//                    try {
//                        InputStream inputStream=getContentResolver().openInputStream(uri);
//
//                        bitmap= BitmapFactory.decodeStream(inputStream);
//                        tvimg.setImageBitmap(bitmap);
//                        ll3.setVisibility(View.VISIBLE);
//                    }
//                    catch (FileNotFoundException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }break;
//                default:
//        }

    }
    private String getEncodedString(Bitmap bitmap){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, os);

      /* or use below if you want 32 bit images

       bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode==REQUEST_CODE_GALLERY)
//        {
//            if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
//            {
//                Intent intent=new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,REQUEST_CODE_GALLERY);
//             //   selectImage();
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(),"ban chua chon anh",Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    private boolean validate()
    {
        String titless=tvTitle.getText().toString().trim();
        String contentss=tvContent.getText().toString().trim();
        if(titless.length() > 150 && contentss.length()>500)
        {
            tvTitle.setError(getString(R.string.quasodong));
            tvContent.setError(getString(R.string.quasodong));
            return true;
        }
        else if(titless.isEmpty())
        {
            tvTitle.setError(getString(R.string.trong));
            tvContent.setError(getString(R.string.trong));
            return true;
        }
        else
        {
            return false;
        }
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
}
