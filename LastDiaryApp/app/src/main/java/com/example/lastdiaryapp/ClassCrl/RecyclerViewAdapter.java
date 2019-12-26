package com.example.lastdiaryapp.ClassCrl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastdiaryapp.AddOrEditCrl.EditActivity;
import com.example.lastdiaryapp.AddOrEditCrl.ViewActivity;
import com.example.lastdiaryapp.AddOrEditEventCrl.ViewEventActivity;
import com.example.lastdiaryapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {

    Context mcontext;
    List<Diary> mData;
    Dialog mDialog;
    Dialog mDialogTrash,dialog_lock,dialog_error,dialog_success;
    DbBitmapUtility convert;
    DatabaseDiaryHelper thongbao;

    public RecyclerViewAdapter(Context mcontext,List<Diary> mData)
    {
        this.mcontext=mcontext;
        this.mData=mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        thongbao=new DatabaseDiaryHelper(mcontext);
        convert=new DbBitmapUtility();
        v= LayoutInflater.from(mcontext).inflate(R.layout.item_diary_recycler,parent,false);
        final MyViewHolder vHolder=new MyViewHolder(v);

        mDialog=new Dialog(mcontext);
        mDialog.setContentView(R.layout.dialog_diary);

        mDialogTrash=new Dialog(mcontext);
        mDialogTrash.setContentView(R.layout.dialog_trash);

        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_title=(TextView)mDialog.findViewById(R.id.Title);
                dialog_title.setTypeface(settype());
                dialog_title.setTextSize(sizeTitle());
                final TextView dialog_content=(TextView)mDialog.findViewById(R.id.tvContent);
                dialog_content.setTypeface(settype());
                dialog_content.setTextSize(sizeContent());
                TextView dialog_time=(TextView)mDialog.findViewById(R.id.tvHH);
                dialog_time.setTypeface(settype());
                dialog_time.setTextSize(sizeGio());
                TextView dialog_dd_mm_yy_e=(TextView)mDialog.findViewById(R.id.tvDDMMYY);
                dialog_dd_mm_yy_e.setTypeface(settype());
                dialog_dd_mm_yy_e.setTextSize(sizeNgay());
                ImageView dialog_img=(ImageView)mDialog.findViewById(R.id.ivMMM);
                ImageButton btnE=(ImageButton)mDialog.findViewById(R.id.btnED);
                ImageButton btnT=(ImageButton)mDialog.findViewById(R.id.btnTR);
                ImageButton btnV=(ImageButton)mDialog.findViewById(R.id.btnView);
                final ImageButton btnC=(ImageButton)mDialog.findViewById(R.id.btnClose);
                ImageView icondialog=(ImageView)mDialog.findViewById(R.id.iconlockdialog);
                String m=mData.get(vHolder.getAdapterPosition()).getTitle();
                if(m.length()>150)
                {
                    m=m.substring(0,149)+"...";
                    dialog_title.setText(m);
                }
                else
                {
                    dialog_title.setText(m);
                }

                dialog_content.setText(mData.get(vHolder.getAdapterPosition()).getContent());
                dialog_time.setText(mData.get(vHolder.getAdapterPosition()).getTimeCreate());
                dialog_dd_mm_yy_e.setText(mData.get(vHolder.getAdapterPosition()).getDateCreate()+","+mData.get(vHolder.getAdapterPosition()).getDayCreate()+"/"+mData.get(vHolder.getAdapterPosition()).getMonthCreate()+"/"+mData.get(vHolder.getAdapterPosition()).getYearCreate());

                if(mData.get(vHolder.getAdapterPosition()).getPhoto()==null)
                {
                    dialog_img.setVisibility(View.GONE);
                }
                else
                {
                    dialog_img.setVisibility(View.VISIBLE);
                    dialog_img.setImageBitmap(convert.getImage(mData.get(vHolder.getAdapterPosition()).getPhoto()));
                }
                if(mData.get(vHolder.getAdapterPosition()).getStatusLock().equals("true"))
                {
                    icondialog.setImageResource(R.drawable.ic_lock_outline_black_12dp);
                }
                else
                {
                    icondialog.setImageResource(R.drawable.ic_lock_open_black_12dp);
                }

                mDialog.setCancelable(false);
//                Toast.makeText(mcontext,"Test Click"+String.valueOf(vHolder.getAdapterPosition()),Toast.LENGTH_LONG).show();
                mDialog.show();
                btnC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.cancel();
                    }
                });
                final Diary diary=mData.get(vHolder.getAdapterPosition());
                dialog_lock=new Dialog(mcontext);
                dialog_lock.setContentView(R.layout.dialog_inputlock);
                dialog_error=new Dialog(mcontext);
                dialog_error.setContentView(R.layout.dialog_error);
                dialog_success=new Dialog(mcontext);
                dialog_success.setContentView(R.layout.dialog_success);

                btnT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mData.get(vHolder.getAdapterPosition()).getStatusLock().equals("true"))
                        {
                            ImageButton cc124=dialog_lock.findViewById(R.id.closeInput123);
                            final EditText nhapkhoa=dialog_lock.findViewById(R.id.nhapkhoa);
                            nhapkhoa.setTypeface(settype());
                            nhapkhoa.setTextSize(sizeButton());
                            final Button xacnhan=dialog_lock.findViewById(R.id.open);
                            xacnhan.setTextSize(sizeButton());
                            xacnhan.setTypeface(settype());
                            cc124.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_lock.show();
                                }
                            });
                            xacnhan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(nhapkhoa.getText().toString().equals(mData.get(vHolder.getAdapterPosition()).getPassword()))
                                    {
                                        Button btnOk=(Button)mDialogTrash.findViewById(R.id.co);
                                        btnOk.setTypeface(settype());
                                        btnOk.setTextSize(sizeButton());
                                        Button btnKhong=(Button)mDialogTrash.findViewById(R.id.khong);
                                        btnKhong.setTypeface(settype());
                                        btnKhong.setTextSize(sizeButton());
                                        btnOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final Dialog dialog_sucess=new Dialog(mcontext);
                                                dialog_sucess.setContentView(R.layout.dialog_success);
                                                final Dialog dialog_error=new Dialog(mcontext);
                                                dialog_error.setContentView(R.layout.dialog_error);
                                               int t= thongbao.deleteDiary(diary);
                                                if(t>0)
                                                {
                                                    Button xacnhanthanhcong=(Button)dialog_sucess.findViewById(R.id.xacnhan);
                                                    xacnhanthanhcong.setTypeface(settype());
                                                    xacnhanthanhcong.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            mData.remove(mData.get(vHolder.getAdapterPosition()));
                                                            mDialogTrash.cancel();
                                                            mDialog.cancel();
                                                            notifyDataSetChanged();
                                                            dialog_lock.cancel();
                                                            dialog_sucess.cancel();
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
                                                        }
                                                    });
                                                    dialog_error.setCancelable(false);
                                                    dialog_error.show();
                                                }
//                                                Toast.makeText(mcontext,"Success",Toast.LENGTH_LONG).show();
//                                                mData.remove(mData.get(vHolder.getAdapterPosition()));
//                                                mDialogTrash.cancel();
//                                                mDialog.cancel();
//                                                notifyDataSetChanged();
//                                                dialog_lock.cancel();
                                            }
                                        });
                                        btnKhong.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mDialogTrash.cancel();
                                                mDialog.cancel();
                                            }
                                        });
                                        mDialogTrash.setCancelable(false);
                                        mDialogTrash.show();
                                    }
                                    else
                                    {
                                        Button xacnhan=dialog_error.findViewById(R.id.xacnhanerror);
                                        xacnhan.setTypeface(settype());
                                        xacnhan.setTextSize(sizeButton());
                                        xacnhan.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog_error.cancel();
                                            }
                                        });
                                        dialog_error.setCancelable(false);
                                        dialog_error.show();
                                        nhapkhoa.setText("");
                                    }
                                }
                            });
                            dialog_lock.show();
                        }
                        else
                        {
                            Button btnOk=(Button)mDialogTrash.findViewById(R.id.co);
                            btnOk.setTypeface(settype());
                            btnOk.setTextSize(sizeButton());
                            Button btnKhong=(Button)mDialogTrash.findViewById(R.id.khong);
                            btnKhong.setTypeface(settype());
                            btnKhong.setTextSize(sizeButton());
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    final Dialog dialog_sucess=new Dialog(mcontext);
                                    dialog_sucess.setContentView(R.layout.dialog_success);
                                    final Dialog dialog_error=new Dialog(mcontext);
                                    dialog_error.setContentView(R.layout.dialog_error);
                                    int t= thongbao.deleteDiary(diary);

                                    if(t>0)
                                    {
                                        Button xacnhanthanhcong=(Button)dialog_sucess.findViewById(R.id.xacnhan);
                                        xacnhanthanhcong.setTypeface(settype());
                                        xacnhanthanhcong.setTextSize(sizeButton());
                                        xacnhanthanhcong.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mData.remove(mData.get(vHolder.getAdapterPosition()));
                                                mDialogTrash.cancel();
                                                mDialog.cancel();
                                                notifyDataSetChanged();
                                                dialog_lock.cancel();
                                                dialog_sucess.cancel();
                                            }
                                        });
                                        dialog_sucess.setCancelable(false);
                                        dialog_sucess.show();

                                    }
                                    else
                                    {
                                        Button xacnhanthatbai=(Button)dialog_sucess.findViewById(R.id.xacnhanerror);
                                        xacnhanthatbai.setTypeface(settype());
                                        xacnhanthatbai.setTextSize(sizeButton());
                                        xacnhanthatbai.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog_error.cancel();
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
                                    mDialogTrash.cancel();
                                    mDialog.cancel();
                                }
                            });
                            mDialogTrash.setCancelable(false);
                            mDialogTrash.show();
                        }

                    }
                });

                btnV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mData.get(vHolder.getAdapterPosition()).getStatusLock().equals("true"))
                        {
                            ImageButton cc124=dialog_lock.findViewById(R.id.closeInput123);
                            final EditText nhapkhoa=dialog_lock.findViewById(R.id.nhapkhoa);
                            nhapkhoa.setTypeface(settype());
                            nhapkhoa.setTextSize(sizeButton());
                            Button xacnhan=dialog_lock.findViewById(R.id.open);
                            xacnhan.setTypeface(settype());
                            xacnhan.setTextSize(sizeButton());
                            cc124.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_lock.show();
                                }
                            });
                            xacnhan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(nhapkhoa.getText().toString().equals(mData.get(vHolder.getAdapterPosition()).getPassword()))
                                    {
                                        Intent intent=new Intent(mcontext, ViewActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                                        intent.putExtras(bundle);
                                        mcontext.startActivity(intent);
                                        mDialog.cancel();
                                        dialog_lock.cancel();
                                    }
                                    else
                                    {
                                        Button xacnhan=dialog_error.findViewById(R.id.xacnhanerror);
                                        xacnhan.setTypeface(settype());
                                        xacnhan.setTextSize(sizeButton());
                                        xacnhan.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog_error.cancel();
                                            }
                                        });
                                        dialog_error.setCancelable(false);
                                        dialog_error.show();
                                        nhapkhoa.setText("");
                                    }
                                }
                            });
                            dialog_lock.show();
                        }
                        else
                        {
                            Intent intent=new Intent(mcontext, ViewActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                            intent.putExtras(bundle);
                            mcontext.startActivity(intent);
                            mDialog.cancel();
                        }

                    }
                });
                btnE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mData.get(vHolder.getAdapterPosition()).getStatusLock().equals("true"))
                        {
                            ImageButton cc124=dialog_lock.findViewById(R.id.closeInput123);
                            final EditText nhapkhoa=dialog_lock.findViewById(R.id.nhapkhoa);
                            Button xacnhan=dialog_lock.findViewById(R.id.open);
                            xacnhan.setTypeface(settype());
                            xacnhan.setTextSize(sizeButton());
                            cc124.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog_lock.cancel();
                                }
                            });
                            xacnhan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(nhapkhoa.getText().toString().equals(mData.get(vHolder.getAdapterPosition()).getPassword()))
                                    {
                                        Intent intent=new Intent(mcontext, EditActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                                        intent.putExtras(bundle);
                                        mcontext.startActivity(intent);
                                        mDialog.cancel();
                                        dialog_lock.cancel();
                                    }
                                    else
                                    {
                                        Button xacnhan=dialog_error.findViewById(R.id.xacnhanerror);
                                        xacnhan.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog_error.cancel();
                                            }
                                        });
                                        dialog_error.setCancelable(false);
                                        dialog_error.show();
                                        nhapkhoa.setText("");
                                    }
                                }
                            });
                            dialog_lock.show();
//                            Button xacnhan=dialog_error.findViewById(R.id.xacnhanerror);
//                            xacnhan.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog_error.cancel();
//                                }
//                            });
//                            dialog_error.setCancelable(false);
//                            dialog_error.show();
//                            nhapkhoa.setText("");
                        }
                        else
                        {
                            Intent intent=new Intent(mcontext,EditActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                            intent.putExtras(bundle);
                            mcontext.startActivity(intent);
                            mDialog.cancel();
                            notifyDataSetChanged();
                        }

                    }
                });

            }
        });
        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvTitle.setTypeface(settype());
        holder.tvTitle.setTextSize(sizeTitle());
        holder.tvTime2.setText(mData.get(position).getTimeCreate());
        holder.tvTime2.setTypeface(settype());
        holder.tvTime2.setTextSize(sizeGio());
        holder.tvDay.setText(mData.get(position).getDayCreate());
        holder.tvDay.setTypeface(settype());
        holder.tvDay.setTextSize(sizeNgay());
        holder.tvDate.setText(mData.get(position).getDateCreate());
        holder.tvDate.setTypeface(settype());
        holder.tvDate.setTextSize(sizeNgay());
        holder.tvMonthYear.setText(mData.get(position).getMonthCreate()+","+mData.get(position).getYearCreate());
        holder.tvMonthYear.setTypeface(settype());
        holder.tvMonthYear.setTextSize(sizeNgay());
        if(mData.get(position).getPhoto()==null)
        {
            holder.ivIMG.setVisibility(View.GONE);
        }
        else
        {
            holder.ivIMG.setVisibility(View.VISIBLE);
            holder.ivIMG.setImageBitmap(convert.getImage(mData.get(position).getPhoto()));
        }
        if(mData.get(position).getStatusLock().equals("true"))
        {
            holder.iconsRing.setImageResource(R.drawable.ic_lock_outline_black_12dp);
        }
        else
        {
            holder.iconsRing.setImageResource(R.drawable.ic_lock_open_black_12dp);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvTime2;
        private TextView tvDay;
        private TextView tvDate;
        private TextView tvMonthYear;
        private ImageView ivIMG;
        private ImageView iconsRing;
//        private ImageButton btnSh,btnEd,btnTr;

        private LinearLayout item_contact;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvTime2=(TextView)itemView.findViewById(R.id.itemTvTime2);
            tvTitle=(TextView)itemView.findViewById(R.id.itemTvTitle);
            tvDate=(TextView)itemView.findViewById(R.id.itemTvDate);
            tvDay=(TextView)itemView.findViewById(R.id.itemTvDay);
            tvMonthYear=(TextView)itemView.findViewById(R.id.itemTvMonthY);
            ivIMG=(ImageView)itemView.findViewById(R.id.itemIMG);
//            btnSh=(ImageButton)itemView.findViewById(R.id.btnShow);
//            btnEd=(ImageButton)itemView.findViewById(R.id.btnEdit);
//            btnTr=(ImageButton)itemView.findViewById(R.id.btnTrash);
            item_contact=(LinearLayout)itemView.findViewById(R.id.diary_info_item);
            iconsRing=(ImageView)itemView.findViewById(R.id.iconlock);
        }
    }
    public Typeface settype()
    {
        SharedPreferences settingfont=mcontext.getSharedPreferences("FONT",0);
        int font=settingfont.getInt(mcontext.getResources().getString(R.string.font),0);
        if(font>0)
        {
            Typeface tf= ResourcesCompat.getFont(mcontext,font);
            return tf;
        }
        else
        {
            return null;
        }
    }
    public Float sizeTitle()
    {
        SharedPreferences settings=mcontext.getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(mcontext.getString(R.string.sizetieude),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(mcontext.getString(R.string.sizetieude),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeContent()
    {
        SharedPreferences settings=mcontext.getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(mcontext.getString(R.string.SizeNoidung),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(mcontext.getString(R.string.SizeNoidung),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeButton()
    {
        SharedPreferences settings=mcontext.getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(mcontext.getString(R.string.SizeNut),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(mcontext.getString(R.string.SizeNut),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeNgay()
    {
        SharedPreferences settings=mcontext.getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(mcontext.getString(R.string.SizeNgay),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(mcontext.getString(R.string.SizeNgay),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
    public Float sizeGio()
    {
        SharedPreferences settings=mcontext.getSharedPreferences("SIZE",0);
        String sizeTitle;
        if(settings.getString(mcontext.getString(R.string.SizeGio),"").equals(""))
        {
            sizeTitle="13";
        }
        else
        {
            sizeTitle=settings.getString(mcontext.getString(R.string.SizeGio),"");
        }
        Float sizeTitle1=Float.parseFloat(sizeTitle);
        return sizeTitle1;
    }
}

