package com.example.lastdiaryapp.AddOrEditCrl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lastdiaryapp.Application.SizeApp;
import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.DbBitmapUtility;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.RecyclerViewAdapter;
import com.example.lastdiaryapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
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
    private ImageButton changepass;
    private ImageButton trash;
    private Dialog dialog;
    private Dialog dialogTrash;
    private Dialog dialogIMG;
    private ImageButton next;
    private ImageButton prev;
    private TextView tvTitleT;
    private int getIDDD;
    private DatabaseDiaryHelper thongthao;
    private Diary diary;
    private DbBitmapUtility covert;
    private Bitmap bitmap;
    private List<Diary> diaryList=new ArrayList<Diary>();
    private RecyclerViewAdapter viewAdapter;
    private LinearLayout ll3;
    private String statusLock;
    private Dialog dialogOutput,dialogError,dialogSuccess,dialog_Lock;
    private  EditText mkmoi,mkcu,nhaplaimk,newpass,againpass;
    private String matkhauthaythe;
    private int chu,nen,nentrong,nut,nutthanhcong,nuthatbai;
    private SizeApp sizeApp;
    private TextToSpeech mTTs;
    private String chucnangnoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        thongthao=new DatabaseDiaryHelper(EditActivity.this);
        covert=new DbBitmapUtility();
        viewAdapter=new RecyclerViewAdapter(EditActivity.this,diaryList);
        tvEDM=(TextView)findViewById(R.id.tvEDMEdit);
        tvY=(TextView)findViewById(R.id.tvYEdit);
        tvTitle=(EditText) findViewById(R.id.tvTitleEdit);
        tvContent=(EditText)findViewById(R.id.tvContentEdit);
        tvimg=(ImageView)findViewById(R.id.imgADD);
        back=(ImageButton)findViewById(R.id.btnClose1Edit);
        close=(Button)findViewById(R.id.btnCloseEdit);
        save=(Button)findViewById(R.id.btnSaveEdit);
        choose=(ImageButton)findViewById(R.id.btnChooseEdit);
        icons=(ImageButton)findViewById(R.id.btnIconEdit);
        changepass=(ImageButton)findViewById(R.id.btnChangePass);
        trash=(ImageButton)findViewById(R.id.btnTrashEdit);
//        next=(ImageButton)findViewById(R.id.Next);
//        prev=(ImageButton)findViewById(R.id.Prev);
//        tvTitleT=(TextView)findViewById(R.id.tvTitleList);
        ll3=(LinearLayout)findViewById(R.id.ll3);
        sizeApp=new SizeApp(this);
        LinearLayout ll4=(LinearLayout)findViewById(R.id.ll4);
        RelativeLayout backgroundt=(RelativeLayout)findViewById(R.id.background);
        LinearLayout ll5=(LinearLayout)findViewById(R.id.ll5);
        LinearLayout ll6=(LinearLayout)findViewById(R.id.ll6);

        tvEDM.setTypeface(settype());
        tvY.setTypeface(settype());
        tvTitle.setTypeface(settype());
        tvContent.setTypeface(settype());
        close.setTypeface(settype());
        save.setTypeface(settype());
        final SharedPreferences settingOn= getSharedPreferences("ONOFNOI", 0);
        chucnangnoi=settingOn.getString(getString(R.string.Noi),"");
        if(chucnangnoi.equals("ON")) {
            mTTs = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        mTTs.setLanguage(Locale.ENGLISH);
                    } else {
                        Toast.makeText(EditActivity.this, "Erroe", Toast.LENGTH_LONG).show();
                    }
                }
            });
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toSpeak = tvTitle.getText().toString().trim();
                    mTTs.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(EditActivity.this, toSpeak, Toast.LENGTH_LONG).show();
                }
            });
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String toSpeak = tvContent.getText().toString().trim();
                    mTTs.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(EditActivity.this, toSpeak, Toast.LENGTH_LONG).show();
                }
            });
        }
//      dung thu

//        tvTitleT.setTypeface(settype());
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
        backgroundt.setBackgroundColor(nen);
        ll4.setBackgroundColor(nentrong);
      //  ll5.setBackgroundColor(nentrong);
        ll6.setBackgroundColor(nentrong);
//        tvTitleT.setTextColor(chu);
//        tvTitleT.setTextSize(sizeTitle());
        tvTitle.setTextColor(chu);
        tvTitle.setTextSize(sizeTitle());
        tvContent.setTextColor(chu);
//        tvContent.setTextSize(sizeContent());
        tvY.setTextColor(chu);
        tvY.setTextSize(sizeNgay());
        tvEDM.setTextColor(chu);
//        tvEDM.setTextSize(sizeNgay());
        save.setBackgroundColor(nut);
//        save.setTextSize(sizeButton());
        save.setTextColor(chu);
        close.setTextColor(chu);
//        close.setTextSize(sizeButton());
        close.setBackgroundColor(nut);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle !=null)
        {
            getIDDD=bundle.getInt("ID");
        }
        diary=thongthao.getDiaryByID(getIDDD);
        tvEDM.setText(diary.getDayCreate()+", "+diary.getDateCreate()+" "+diary.getMonthCreate());
        tvY.setText(diary.getYearCreate());
        tvTitle.setText(diary.getTitle());
        tvContent.setText(diary.getContent());
        statusLock=diary.getStatusLock();

        if(diary.getStatusLock().equals("true"))
        {
            icons.setImageResource(R.drawable.ic_lock_outline_black_36dp);
//            changepass.setVisibility(View.VISIBLE);
        }
        else
        {
            icons.setImageResource(R.drawable.ic_lock_open_black_36dp);
//            changepass.setVisibility(View.GONE);
        }
        icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diary.getStatusLock().equals("true"))
                {
                    statusLock="false";
                    icons.setImageResource(R.drawable.ic_lock_open_black_36dp);
                }
                else
                {
                    statusLock="true";
                    icons.setImageResource(R.drawable.ic_lock_outline_black_36dp);
                }
            }
        });
        dialogOutput=new Dialog(EditActivity.this);
        dialogOutput.setContentView(R.layout.dialog_outputdialog);
        dialogError=new Dialog(EditActivity.this);
        dialogError.setContentView(R.layout.dialog_error);
        dialogSuccess=new Dialog(EditActivity.this);
        dialogSuccess.setContentView(R.layout.dialog_success);
        dialog_Lock=new Dialog(EditActivity.this);
        dialog_Lock.setContentView(R.layout.dialog_lock);
        final String giatri="";
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diary.getPassword()==null)
                {
                    ImageButton closexxx=dialog_Lock.findViewById(R.id.closeLock);
                    newpass=dialog_Lock.findViewById(R.id.nhapkhoa);
                    againpass=dialog_Lock.findViewById(R.id.nhapkhoa1);
                    newpass.setTypeface(settype());
                    againpass.setTypeface(settype());
                    Button create=dialog_Lock.findViewById(R.id.create);
                    create.setTypeface(settype());
                    create.setBackgroundColor(nut);
                    create.setTypeface(settype());
                    closexxx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_Lock.cancel();
                        }
                    });
                    create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(againpass.getText().toString().equals(newpass.getText().toString()))
                            {
                                Toast.makeText(EditActivity.this,"Tạo mật khẩu thành công",Toast.LENGTH_SHORT).show();
                                dialog_Lock.cancel();
                            }
                            else
                            {
                                Toast.makeText(EditActivity.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
//                                newpass.setText("");
//                                againpass.setText("");
                                dialog_Lock.cancel();
                            }
                        }
                    });
                    matkhauthaythe=newpass.getText().toString();
                    dialog_Lock.setCancelable(false);
                    dialog_Lock.show();
                }
                else
                {
                    mkcu=dialogOutput.findViewById(R.id.nhaplai);
                    mkmoi=dialogOutput.findViewById(R.id.nhapkhoa);
                    nhaplaimk=dialogOutput.findViewById(R.id.nhapkhoa1);
                    mkmoi.setTypeface(settype());
                    mkcu.setTypeface(settype());
                    nhaplaimk.setTypeface(settype());
                    ImageButton btnlock=dialogOutput.findViewById(R.id.closeLock);
                    Button change=dialogOutput.findViewById(R.id.change);
                    change.setTypeface(settype());
                    change.setBackgroundColor(nut);
                    change.setTextColor(chu);
                    btnlock.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogOutput.cancel();
                        }
                    });
                    change.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mkcu.getText().toString().contains(diary.getPassword()))
                            {
                                if(nhaplaimk.getText().toString().trim().equals(mkmoi.getText().toString().trim()))
                                {
                                    Toast.makeText(EditActivity.this,"Thay đổi thành công",Toast.LENGTH_SHORT).show();
                                    dialogOutput.cancel();
                                }
                                else
                                {
                                    Button xacnhan=dialogError.findViewById(R.id.xacnhanerror);
                                    xacnhan.setTypeface(settype());
                                    xacnhan.setBackgroundColor(nut);
                                    xacnhan.setTextColor(chu);
                                    xacnhan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogError.cancel();
//                                            nhaplaimk.setText("");
//                                            mkmoi.setText("");
//                                            mkcu.setText("");
                                        }
                                    });
                                    dialogError.setCancelable(false);
                                    dialogError.show();
                                }

                            }
                            else
                            {
                                Button xacnhan=dialogError.findViewById(R.id.xacnhanerror);
                                xacnhan.setTypeface(settype());
                                xacnhan.setTextColor(chu);
                                xacnhan.setBackgroundColor(nen);
                                xacnhan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogError.cancel();
//                                        nhaplaimk.setText("");
//                                        mkmoi.setText("");
//                                        mkcu.setText("");
                                    }
                                });
                                dialogError.setCancelable(false);
                                dialogError.show();
                            }
                        }
                    });
                    matkhauthaythe=mkmoi.getText().toString();
                    dialogOutput.setCancelable(false);
                    dialogOutput.show();
                }
            }
        });
        if(diary.getPhoto()==null)
        {
            ll3.setVisibility(View.GONE);
        }
        else
        {
            tvimg.setImageBitmap(covert.getImage(diary.getPhoto()));
        }
        if(bitmap==null)
        {
            if(diary.getPhoto()==null)
            {
                ll3.setVisibility(View.GONE);
            }
            else
            {
                bitmap=covert.getImage(diary.getPhoto());
            }
        }
        else
        {
            tvimg.setImageBitmap(bitmap);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialog=new Dialog(EditActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setContentView(R.layout.dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)dialog.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                Button btnKhong=(Button)dialog.findViewById(R.id.khong);
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
                        dialog.cancel();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        dialogTrash=new Dialog(EditActivity.this);
        dialogTrash.setContentView(R.layout.dialog_trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)dialogTrash.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                Button btnKhong=(Button)dialogTrash.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog_sucess=new Dialog(EditActivity.this);
                        dialog_sucess.setContentView(R.layout.dialog_success);
                        final Dialog dialog_error=new Dialog(EditActivity.this);
                        dialog_error.setContentView(R.layout.dialog_error);
                        int t= thongthao.deleteDiary(diary);
                       if(t>0)
                       {
                           Button xacnhanthanhcong=(Button)dialog_sucess.findViewById(R.id.xacnhan);
                           xacnhanthanhcong.setTypeface(settype());
                           xacnhanthanhcong.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   diaryList.remove(diary);
                                   dialogTrash.cancel();
                                   viewAdapter.notifyDataSetChanged();
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
                });
                btnKhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTrash.cancel();
                    }
                });
                dialogTrash.setCancelable(false);
                dialogTrash.show();
            }
        });
        dialogIMG=new Dialog(EditActivity.this);
        dialogIMG.setContentView(R.layout.dialog_img);
        tvimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton closeX=(ImageButton) dialogIMG.findViewById(R.id.btnClose1111);
                ImageView imageView=(ImageView)dialogIMG.findViewById(R.id.ivDia);
                closeX.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogIMG.cancel();
                    }
                });
                imageView.setImageBitmap(bitmap);
                dialogIMG.setCancelable(false);
                dialogIMG.show();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //startActivityForResult(intent,REQUEST_CODE_GALLERY);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(Intent.createChooser(intent,"Choọn Ảnh"),1);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                diary.setTitle(tvTitle.getText().toString());
                diary.setContent(tvContent.getText().toString());
                diary.setTimeCreate(diary.getTimeCreate());
                diary.setDayCreate(diary.getDayCreate());
                diary.setDateCreate(diary.getDateCreate());
                diary.setMonthCreate(diary.getMonthCreate());
                diary.setYearCreate(diary.getYearCreate());
                if(bitmap==null)
                {
                    diary.setPhoto(diary.getPhoto());
                }
                else
                {
                    diary.setPhoto(covert.getBytes(bitmap));
                }
                //diary.setStatusLock(diary.getStatusLock());
//                diary.setPassword(diary.getPassword());
                diary.setStatusLock(statusLock);
                if(mkmoi.getText().toString().equals("")&& newpass.getText().toString().equals(""))
                {
                    diary.setPassword(diary.getPassword());
                }
                else
                {
                    if(diary.getPassword()==null)
                    {
                        diary.setPassword(newpass.getText().toString());
                    }
                    else
                    {
                        diary.setPassword(mkmoi.getText().toString());
                    }
                }

//                final String giatri1="";
//                if(mkmoi.getText().toString().equals(giatri1)||newpass.getText().toString().equals(giatri1))
//                {
//                    diary.setPassword(diary.getPassword());
//                }
//                else
//                {
//                    if(diary.getPassword().equals(""))
//                    {
//                        diary.setPassword(mkmoi.getText().toString());
//                    }
//                    else
//                    {
//                        diary.setPassword(newpass.getText().toString());
//                    }
//                }

              int t=  thongthao.updateDiary(diary);
                if(t>0)
                {
                    Button xacnhan =dialogSuccess.findViewById(R.id.xacnhan);
                    xacnhan.setTypeface(settype());
                    xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                            dialogSuccess.cancel();
                        }
                    });
                    dialogSuccess.setCancelable(false);
                    dialogSuccess.show();
                }
                else
                {
                    Button xacnhan=dialogError.findViewById(R.id.xacnhanerror);
                    xacnhan.setTypeface(settype());
                    xacnhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogError.cancel();
                        }
                    });
                    dialogError.setCancelable(false);
                    dialogError.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                bitmap = BitmapFactory.decodeStream(inputStream);
                tvimg.setImageBitmap(bitmap);
                ll3.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
