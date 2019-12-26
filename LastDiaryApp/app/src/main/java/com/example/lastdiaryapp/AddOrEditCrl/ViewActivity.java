package com.example.lastdiaryapp.AddOrEditCrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
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
import android.widget.ViewAnimator;

import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.DbBitmapUtility;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.RecyclerViewAdapter;
import com.example.lastdiaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    private TextView tvEDM;
    private TextView tvY;
    private EditText tvTitle;
    private EditText tvContent;
    private ImageView tvimg;
    private ImageButton back;
    private ImageButton edit;
    private ImageButton trash;
    private Dialog dialog;
    private ImageButton reply;
    private Dialog dialog_img;
    private Diary diary;
    private Dialog diary_trash;
    private DbBitmapUtility convert;
    private int getIDDD;
    private DatabaseDiaryHelper thongthao;
    private LinearLayout ll3;
    private List<Diary> diaryList=new ArrayList<Diary>();
    private RecyclerViewAdapter viewAdapter=new RecyclerViewAdapter(ViewActivity.this,diaryList);
    private int nen,nentrong,nut,nutthanhcong,nuthatbai,chu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        thongthao=new DatabaseDiaryHelper(ViewActivity.this);
        convert=new DbBitmapUtility();
        tvEDM=(TextView)findViewById(R.id.tvEDMView);
        tvY=(TextView)findViewById(R.id.tvYView);
        tvTitle=(EditText) findViewById(R.id.tvTitleEdit);
        tvContent=(EditText)findViewById(R.id.tvContentEdit);
        tvimg=(ImageView)findViewById(R.id.imgView);
        reply=(ImageButton)findViewById(R.id.btnReplyView);
        trash=(ImageButton)findViewById(R.id.btnTrashView);
        edit=(ImageButton)findViewById(R.id.btnEditView);
        back=(ImageButton)findViewById(R.id.btnClose1Edit);
        ll3=(LinearLayout)findViewById(R.id.ll3);
        RelativeLayout background=(RelativeLayout)findViewById(R.id.background);

        dialog=new Dialog(ViewActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setContentView(R.layout.dialog);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvEDM.setTypeface(settype());
        tvY.setTypeface(settype());
        tvTitle.setTypeface(settype());
        tvContent.setTypeface(settype());

        tvTitle.setTextSize(sizeTitle());
        tvContent.setTextSize(sizeContent());
        tvEDM.setTextSize(sizeNgay());
        tvY.setTextSize(sizeGio());

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
        background.setBackgroundColor(nen);
        tvContent.setTextColor(chu);
        tvTitle.setTextColor(chu);
        tvY.setTextColor(chu);
        tvEDM.setTextColor(chu);
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        if(bundle !=null)
        {
            getIDDD=bundle.getInt("ID");
        }
        diary=thongthao.getDiaryByID(getIDDD);

        tvEDM.setText(diary.getDayCreate()+", "+diary.getDateCreate()+" "+diary.getMonthCreate());
        tvY.setText(diary.getYearCreate());
        tvTitle.setText(diary.getTitle());
        tvContent.setText(diary.getContent());
        if(diary.getPhoto()==null)
        {
          ll3.setVisibility(View.GONE);
        }
        else
        {
            tvimg.setImageBitmap(convert.getImage(diary.getPhoto()));
        }


        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)dialog.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                btnOk.setTextSize(sizeButton());
                Button btnKhong=(Button)dialog.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
                btnKhong.setTextSize(sizeButton());
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewActivity.this,EditActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putInt("ID",diary.getID());
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        dialog_img=new Dialog(ViewActivity.this);
        dialog_img.setContentView(R.layout.dialog_img);
        tvimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton closeX=(ImageButton) dialog_img.findViewById(R.id.btnClose1111);
                ImageView imageView=(ImageView)dialog_img.findViewById(R.id.ivDia);
                imageView.setImageBitmap(convert.getImage(diary.getPhoto()));
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
        diary_trash=new Dialog(ViewActivity.this);
        diary_trash.setContentView(R.layout.dialog_trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnOk=(Button)diary_trash.findViewById(R.id.co);
                btnOk.setTypeface(settype());
                btnOk.setTextSize(sizeButton());
                btnOk.setBackgroundColor(nutthanhcong);
                btnOk.setTextColor(chu);
                Button btnKhong=(Button)diary_trash.findViewById(R.id.khong);
                btnKhong.setTypeface(settype());
                btnKhong.setTextSize(sizeButton());
                btnKhong.setBackgroundColor(nut);
                btnKhong.setTextColor(nuthatbai);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        thongthao.deleteDiary(diary);
                        Toast.makeText(ViewActivity.this,"Success",Toast.LENGTH_LONG).show();
                        diaryList.remove(diary);
                        diary_trash.cancel();
                        diary_trash.cancel();
                        viewAdapter.notifyDataSetChanged();
                        onBackPressed();
                    }
                });
                btnKhong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diary_trash.cancel();
                        diary_trash.cancel();
                    }
                });
                diary_trash.setCancelable(false);
                diary_trash.show();
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
