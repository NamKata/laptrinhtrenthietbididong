package com.example.lastdiaryapp.ClassCrl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastdiaryapp.AddOrEditCrl.EditActivity;
import com.example.lastdiaryapp.AddOrEditEventCrl.EditEventActivity;
import com.example.lastdiaryapp.AddOrEditEventCrl.ViewEventActivity;
import com.example.lastdiaryapp.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RecyclerEventViewAdapter extends RecyclerView.Adapter<RecyclerEventViewAdapter.MyViewHolder> {

    Context mcontext;
    List<Event> mData;
    Dialog mDialog;
    Dialog mDialogTrash;
    DatabaseEventHelper thongthao;

    public RecyclerEventViewAdapter(Context mcontext,List<Event> mData)
    {
        this.mcontext=mcontext;
        this.mData=mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontext).inflate(R.layout.item_event_recycler,parent,false);
        final MyViewHolder vHolder=new MyViewHolder(v);
        thongthao=new DatabaseEventHelper(mcontext);
        mDialog=new Dialog(mcontext);
        mDialog.setContentView(R.layout.dialog_event);
        mDialogTrash=new Dialog(mcontext);
        mDialogTrash.setContentView(R.layout.dialog_trash);
        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView TitleEvent=(TextView)mDialog.findViewById(R.id.TitleEvent);
                TitleEvent.setTypeface(settype());
                TitleEvent.setTextSize(sizeTitle());
                TextView TimeCreate=(TextView)mDialog.findViewById(R.id.tvTimeCreate);
                TimeCreate.setTypeface(settype());
                TimeCreate.setTextSize(sizeGio());
                TextView DateCreate=(TextView)mDialog.findViewById(R.id.tvDateCreate);
                DateCreate.setTypeface(settype());
                DateCreate.setTextSize(sizeNgay());
                TextView DateEvent=(TextView)mDialog.findViewById(R.id.tvDateEvent);
                DateEvent.setTypeface(settype());
                DateEvent.setTextSize(sizeNgay());
                TextView TimeEvent=(TextView)mDialog.findViewById(R.id.tvTimeEvent);
                TimeEvent.setTypeface(settype());
                TimeEvent.setTextSize(sizeGio());
                TextView NhacNho=(TextView)mDialog.findViewById(R.id.tvNhacNho);
                NhacNho.setTypeface(settype());
                NhacNho.setTextSize(sizeTitle());
                ImageView dialog_img=(ImageView)mDialog.findViewById(R.id.ivMMM);
                ImageButton btnEdit=(ImageButton)mDialog.findViewById(R.id.btnED);
                ImageButton btnTrash=(ImageButton)mDialog.findViewById(R.id.btnTR);
                ImageButton btnC=(ImageButton)mDialog.findViewById(R.id.btnClose);
                ImageButton btnView=(ImageButton)mDialog.findViewById(R.id.btnView);
                final ImageView thongbaotin=(ImageView)mDialog.findViewById(R.id.thongbao);
                String m=mData.get(vHolder.getAdapterPosition()).getTitle();
                if(m.length() > 40)
                {
                    m=m.substring(0,39)+"...";
                    TitleEvent.setText(m);
                }
                else
                {
                    TimeEvent.setText(m);
                }
                if (mData.get(vHolder.getAdapterPosition()).getNotifical().equals("on"))
                {
                    thongbaotin.setImageResource(R.drawable.ic_notifications_active_black_24dp);
                }
                else
                {
                    thongbaotin.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                }
                NhacNho.setText("Nhắc nhở "+String.valueOf(vHolder.getAdapterPosition())+":");
                dialog_img.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());
                TimeCreate.setText(mData.get(vHolder.getAdapterPosition()).getTimeCreate());
                DateCreate.setText(mData.get(vHolder.getAdapterPosition()).getDateCreate());
                TimeEvent.setText(mData.get(vHolder.getAdapterPosition()).getTimeEvent());
                DateEvent.setText(mData.get(vHolder.getAdapterPosition()).getDateEvent());
                TitleEvent.setText(mData.get(vHolder.getAdapterPosition()).getTitle());
                mDialog.setCancelable(false);
                mDialog.show();
                btnC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.cancel();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mcontext, EditEventActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                        intent.putExtras(bundle);
                        mcontext.startActivity(intent);
                        mDialog.cancel();
                        notifyDataSetChanged();

                    }
                });
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mcontext, ViewEventActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("ID",mData.get(vHolder.getAdapterPosition()).getID());
                        intent.putExtras(bundle);
                        mcontext.startActivity(intent);
                        mDialog.cancel();

                    }
                });
                final Event event=mData.get(vHolder.getAdapterPosition());
                btnTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btnOk=(Button)mDialogTrash.findViewById(R.id.co);
                        btnOk.setTypeface(settype());
                        btnOk.setTextSize(sizeButton());
                        Button btnKhong=(Button)mDialogTrash.findViewById(R.id.khong);
                        btnKhong.setTypeface(settype());
                        btnKhong.setTextSize(sizeButton());
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                thongthao.deleteEvent(event);
                                Toast.makeText(mcontext,"Success",Toast.LENGTH_LONG).show();
                                mData.remove(mData.get(vHolder.getAdapterPosition()));
                                mDialogTrash.cancel();
                                mDialog.cancel();
                                notifyDataSetChanged();
                            }
                        });
                        btnKhong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialogTrash.cancel();
                            }
                        });
                        mDialogTrash.setCancelable(false);
                        mDialogTrash.show();
                        mDialog.cancel();

                    }
                });


            }
        });

        return vHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNhacNho.setText("Nhắc nhở "+String.valueOf(position)+":");
        holder.tvNhacNho.setTypeface(settype());
        holder.tvNhacNho.setTextSize(sizeTitle());
        holder.tvTimeEvnet.setText(mData.get(position).getTimeEvent());
        holder.tvTimeEvnet.setTypeface(settype());
        holder.tvTimeEvnet.setTextSize(sizeGio());
        holder.tvTimeCreate.setText(mData.get(position).getTimeCreate());
        holder.tvTimeCreate.setTypeface(settype());
        holder.tvTimeCreate.setTextSize(sizeGio());
        holder.tvTitleEvent.setText(mData.get(position).getTitle());
        holder.tvTitleEvent.setTypeface(settype());
        holder.tvTitleEvent.setTextSize(sizeTitle());
        holder.tvDateEvent.setText(mData.get(position).getDateEvent());
        holder.tvDateEvent.setTypeface(settype());
        holder.tvDateEvent.setTextSize(sizeNgay());
        holder.tvDateCreate.setText(mData.get(position).getDateCreate());
        holder.tvDateCreate.setTypeface(settype());
        holder.tvTimeCreate.setTextSize(sizeGio());
        holder.ivIMG.setImageResource(mData.get(position).getPhoto());
        if(mData.get(position).getNotifical().equals("on") )
        {
            holder.thongbao1.setImageResource(R.drawable.ic_notifications_active_black_12dp);
        }
        else
        {
            holder.thongbao1.setImageResource(R.drawable.ic_notifications_off_black_12dp);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNhacNho;
        private TextView tvTitleEvent;
        private TextView tvDateCreate;
        private TextView tvDateEvent;
        private TextView tvTimeCreate;
        private TextView tvTimeEvnet;
        private ImageView ivIMG,thongbao1;



        private LinearLayout item_contact;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvNhacNho=(TextView)itemView.findViewById(R.id.itemNhacNho);
            tvTitleEvent=(TextView)itemView.findViewById(R.id.itemTitleEvent);
            tvDateCreate=(TextView)itemView.findViewById(R.id.itemCreatedate);
            tvDateEvent=(TextView)itemView.findViewById(R.id.itemDateEvent);
            tvTimeCreate=(TextView)itemView.findViewById(R.id.itemTimeCreate);
            tvTimeEvnet=(TextView)itemView.findViewById(R.id.itemTimeEvent);
            ivIMG=(ImageView)itemView.findViewById(R.id.itemIMG);
            thongbao1=(ImageView)itemView.findViewById(R.id.thongbaoiconring);
            item_contact=(LinearLayout)itemView.findViewById(R.id.diary_info_item);
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
