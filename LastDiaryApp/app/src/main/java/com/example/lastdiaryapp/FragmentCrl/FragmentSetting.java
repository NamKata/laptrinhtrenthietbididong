package com.example.lastdiaryapp.FragmentCrl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.lastdiaryapp.Setting.ChangeLockActivity;
import com.example.lastdiaryapp.Setting.LockScreenActivity;
import com.example.lastdiaryapp.R;
import com.example.lastdiaryapp.Setting.Utility;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.content.Context.MODE_PRIVATE;

public class FragmentSetting extends Fragment {

    View v;
    private  SharedPreferences.Editor editor;
    private  SharedPreferences sharedPreferences;
    private Dialog dialogColor,dialogMore,dialog_size;
    private LinearLayout mk,nn,ms,khac,gt,size,font;
    private String Khoamanhhinh,password,chucnangnoi;
   private int BackgroudNen,BackgroudChu,BackgroudNut,BackgroudNutSucess,BackgroudNutError,BackgroudNenTrong;
    private int index1=0;
   private int chu,nen,nut,nutthanhcong,nuthatbai,nentrong;
    public FragmentSetting(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.setting_fragment,container,false);
        mk=(LinearLayout)v.findViewById(R.id.matkhau);
        nn=(LinearLayout)v.findViewById(R.id.ngonngu);
        ms=(LinearLayout)v.findViewById(R.id.mausac);
        khac=(LinearLayout)v.findViewById(R.id.khac);
        gt=(LinearLayout)v.findViewById(R.id.gioithieu);
        size=(LinearLayout)v.findViewById(R.id.size);
        font=(LinearLayout)v.findViewById(R.id.font);
//        setLocale("vi");
        LoadLocale();
//        sharedPreferences=this.getActivity().getSharedPreferences("Settings",MODE_PRIVATE);
//        editor=sharedPreferences.edit();
      //  LoadLocale();
        nn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLanguage();
            }
        });
        dialogColor=new Dialog(getContext());
        dialogColor.setContentView(R.layout.dialog_changecolor);
        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings= getActivity().getSharedPreferences("COLOR", 0);

                if(settings.getInt(getString(R.string.Mauchu),0)==0 && settings.getInt(getString(R.string.background),0)==0 && settings.getInt(getString(R.string.Maunut),0)==0 &&settings.getInt(getString(R.string.Maunutthanhcong),0)==0 && settings.getInt(getString(R.string.Maunutthatbai),0)==0 && settings.getInt(getString(R.string.Maunentrong),0)==0)
                {

                }
                else {
                     chu=settings.getInt(getString(R.string.Mauchu),0);
                     nen=settings.getInt(getString(R.string.background),0);
                     nut=settings.getInt(getString(R.string.Maunut),0);
                     nutthanhcong=settings.getInt(getString(R.string.Maunutthanhcong),0);
                     nuthatbai=settings.getInt(getString(R.string.Maunutthatbai),0);
                     nentrong=settings.getInt(getString(R.string.Maunentrong),0);
                }
                final RelativeLayout background=(RelativeLayout)dialogColor.findViewById(R.id.dialog_color);
                BackgroudChu= getContext().getResources().getColor(R.color.colorText);
                BackgroudNen= getContext().getResources().getColor(R.color.colorBackground);
                BackgroudNut= getContext().getResources().getColor(R.color.colorButton);
                BackgroudNutSucess= getContext().getResources().getColor(R.color.colorButton);
                BackgroudNutError= getContext().getResources().getColor(R.color.colorButton);
                BackgroudNenTrong= getContext().getResources().getColor(R.color.colorTint);
                ImageButton cl=(ImageButton)dialogColor.findViewById(R.id.cl);
                Button mauchu=(Button)dialogColor.findViewById(R.id.mauchu);
                Button maunen=(Button)dialogColor.findViewById(R.id.maunen);
                Button maunut=(Button)dialogColor.findViewById(R.id.nut);
                Button maunutthanhcong=(Button)dialogColor.findViewById(R.id.nuthanhcong);
                Button maunutthatbai=(Button)dialogColor.findViewById(R.id.nutthatbai);
                Button maunentrong=(Button)dialogColor.findViewById(R.id.maunentrong);
                Button xacnhanchangecolor=(Button)dialogColor.findViewById(R.id.submitcolor);
                mauchu.setBackgroundColor(chu);
                maunen.setBackgroundColor(nen);
                maunentrong.setBackgroundColor(nentrong);
                maunut.setBackgroundColor(nut);
                maunutthanhcong.setBackgroundColor(nutthanhcong);
                maunutthatbai.setBackgroundColor(nuthatbai);
                cl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogColor.cancel();
                    }
                });
                mauchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudChu, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudChu=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                maunen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudNen, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudNen=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                maunut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudNut, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudNut=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                maunutthanhcong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudNutSucess, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudNutSucess=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                maunutthatbai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudNutError, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudNutError=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                maunentrong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AmbilWarnaDialog colorclick=new AmbilWarnaDialog(getContext(), BackgroudNenTrong, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                            @Override
                            public void onCancel(AmbilWarnaDialog dialog) {

                            }

                            @Override
                            public void onOk(AmbilWarnaDialog dialog, int color) {
                                BackgroudNenTrong=color;
                            }
                        });
                        colorclick.show();
                    }
                });
                xacnhanchangecolor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings= getActivity().getSharedPreferences("COLOR", 0);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putInt(getString(R.string.background),BackgroudNen);
                        editor.putInt(getString(R.string.Mauchu),BackgroudChu);
                        editor.putInt(getString(R.string.Maunut),BackgroudNut);
                        editor.putInt(getString(R.string.Maunutthatbai),BackgroudNutError);
                        editor.putInt(getString(R.string.Maunutthanhcong),BackgroudNutSucess);
                        editor.putInt(getString(R.string.Maunentrong),BackgroudNenTrong);
                        editor.apply();
                        Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        dialogColor.cancel();
                    }
                });
                dialogColor.setCancelable(false);
                dialogColor.show();
            }
        });
        SharedPreferences ss= getActivity().getSharedPreferences("PREFS", 0);
        password=ss.getString(getString(R.string.Password),"");
        mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.equals(""))
                {
                    Intent intent=new Intent(getContext(), LockScreenActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(getContext(), ChangeLockActivity.class);
                    startActivity(intent);
                }
            }
        });
        dialogMore=new Dialog(getContext());
        dialogMore.setContentView(R.layout.setting_more);
        final SharedPreferences settings= getActivity().getSharedPreferences("ONOF", 0);
        final SharedPreferences settingf= getActivity().getSharedPreferences("ONOFNOI", 0);
        khac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton xxx=dialogMore.findViewById(R.id.closeSetMore);
                Switch ttt=dialogMore.findViewById(R.id.onOffpass);
                Switch ggg=dialogMore.findViewById(R.id.khoachucnangnoi);
                xxx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogMore.cancel();
                    }
                });
                Khoamanhhinh=settings.getString(getString(R.string.Khoamatkhau),"");
                chucnangnoi=settingf.getString(getString(R.string.Noi),"");
                if(Khoamanhhinh.equals("ON"))
                {
                    ttt.setChecked(true);
                }
                else
                {
                    ttt.setChecked(false);
                }
                if(chucnangnoi.equals("ON"))
                {
                    ggg.setChecked(true);
                }
                else
                {
                    ggg.setChecked(false);
                }
                ggg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(chucnangnoi.equals(""))
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOFNOI", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Noi),"ON");
                            editor.apply();
                            Toast.makeText(getContext(),"Open",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }
                        else if(chucnangnoi.equals("ON"))
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOFNOI", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Noi),"OFF");
                            editor.apply();
                            Toast.makeText(getContext(),"Update OFF",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }
                        else
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOFNOI", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Noi),"ON");
                            editor.apply();
                            Toast.makeText(getContext(),"Update ON",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }
                    }
                });
                ttt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Khoamanhhinh.equals(""))
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOF", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Khoamatkhau),"ON");
                            editor.apply();
                            Toast.makeText(getContext(),"Create",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }
                        else if(Khoamanhhinh.equals("ON"))
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOF", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Khoamatkhau),"OFF");
                            editor.apply();
                            Toast.makeText(getContext(),"Update OFF",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }
                        else
                        {
                            SharedPreferences settings= getActivity().getSharedPreferences("ONOF", 0);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString(getString(R.string.Khoamatkhau),"ON");
                            editor.apply();
                            Toast.makeText(getContext(),"Update ON",Toast.LENGTH_LONG).show();
                            dialogMore.cancel();
                        }

                    }
                });

                dialogMore.setCancelable(false);
                dialogMore.show();
            }
        });
        dialog_size=new Dialog(getContext());
        dialog_size.setContentView(R.layout.dialog_size);
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settingsss=getActivity().getSharedPreferences("SIZE",0);
                final String tieudezize;
                final String noidungsize;
                final String nutsize;
                final String ngaysize;
                final String giosize;
                if(settingsss.getString(getString(R.string.sizetieude),"").equals("")&& settingsss.getString(getString(R.string.SizeNoidung),"").equals("") && settingsss.getString(getString(R.string.SizeNut),"").equals("") && settingsss.getString(getString(R.string.SizeNgay),"").equals("") && settingsss.getString(getString(R.string.SizeGio),"").equals(""))
                {
                    tieudezize="13";
                    noidungsize="13";
                    nutsize="13";
                    ngaysize="13";
                    giosize="13";
                }
                else
                {
                    tieudezize=settingsss.getString(getString(R.string.sizetieude),"");
                    noidungsize=settingsss.getString(getString(R.string.SizeNoidung),"");
                    nutsize=settingsss.getString(getString(R.string.SizeNut),"");
                    ngaysize=settingsss.getString(getString(R.string.SizeNgay),"");
                    giosize=settingsss.getString(getString(R.string.SizeGio),"");
                }

                final SeekBar setSizeTieude=(SeekBar)dialog_size.findViewById(R.id.sizetieude);
                final SeekBar setSizeNoidung=(SeekBar)dialog_size.findViewById(R.id.sizenoidung);
                final SeekBar setSizeNut=(SeekBar)dialog_size.findViewById(R.id.sizenut);
                final SeekBar setSizeGio=(SeekBar)dialog_size.findViewById(R.id.sizegio);
                final SeekBar setSizeNgay=(SeekBar)dialog_size.findViewById(R.id.sizengay);

                final TextView texttieude=(TextView)dialog_size.findViewById(R.id.texttieude);
                final TextView textnoidung=(TextView)dialog_size.findViewById(R.id.textnoidung);
                final TextView textnut=(TextView)dialog_size.findViewById(R.id.textkichthuoc);
                final TextView textgio=(TextView)dialog_size.findViewById(R.id.texttime);
                final TextView textngay=(TextView)dialog_size.findViewById(R.id.textngay);

                texttieude.setText(tieudezize);
                textnoidung.setText(noidungsize);
                textnut.setText(nutsize);
                textgio.setText(ngaysize);
                textngay.setText(giosize);

                setSizeTieude.setProgress(Integer.parseInt(tieudezize));
                setSizeNoidung.setProgress(Integer.parseInt(noidungsize));
                setSizeNut.setProgress(Integer.parseInt(nutsize));
                setSizeGio.setProgress(Integer.parseInt(giosize));
                setSizeNgay.setProgress(Integer.parseInt(ngaysize));

                ImageButton closeSettingsize=(ImageButton) dialog_size.findViewById(R.id.closesizesetting);
                Button xacnhanyeucau=(Button)dialog_size.findViewById(R.id.submitsizesetting);
                xacnhanyeucau.setTypeface(settype());
                xacnhanyeucau.setTextSize(Integer.parseInt(nutsize));
                setSizeTieude.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int pral=13;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        pral=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        texttieude.setText(Integer.toString(pral));
                    }
                });
                setSizeNoidung.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int pral1=13;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        pral1=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textnoidung.setText(Integer.toString(pral1));
                    }
                });
                setSizeNut.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int pral2=13;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        pral2=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textnut.setText(Integer.toString(pral2));
                    }
                });
                setSizeGio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int pral3=13;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        pral3=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textgio.setText(Integer.toString(pral3));
                    }
                });
                setSizeNgay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int pral4=13;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        pral4=progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textngay.setText(Integer.toString(pral4));
                    }
                });
                closeSettingsize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_size.cancel();
                    }
                });
                xacnhanyeucau.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sizetieude=Integer.toString(setSizeTieude.getProgress());
                        String sizenoidung=Integer.toString(setSizeNoidung.getProgress());
                        String sizenut=Integer.toString(setSizeNut.getProgress());
                        String sizegio=Integer.toString(setSizeGio.getProgress());
                        String sizengay=Integer.toString(setSizeNgay.getProgress());
                        SharedPreferences settings=getActivity().getSharedPreferences("SIZE",0);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putString(getString(R.string.sizetieude),sizetieude);
                        editor.putString(getString(R.string.SizeNoidung),sizenoidung);
                        editor.putString(getString(R.string.SizeNut),sizenut);
                        editor.putString(getString(R.string.SizeGio),sizegio);
                        editor.putString(getString(R.string.SizeNgay),sizengay);
                        editor.commit();
                        dialog_size.cancel();
                    }
                });
                dialog_size.setCancelable(false);
                dialog_size.show();
            }
        });
        final Dialog dialog_font=new Dialog(getContext());
        dialog_font.setContentView(R.layout.dialog_changefontfamily);
        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerfont=(Spinner)dialog_font.findViewById(R.id.spinner1);
                ImageButton closefont=(ImageButton)dialog_font.findViewById(R.id.closefontfamily);
                Button buttonfont=(Button)dialog_font.findViewById(R.id.xacnhanfont);
                buttonfont.setTypeface(settype());
                ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(),R.array.fonttext,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerfont.setAdapter(adapter);
                spinnerfont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        String text=parent.getItemAtPosition(position).toString();
//                        String text1=Integer.toString(parent.getSelectedItemPosition());
//                        Toast.makeText(getContext(),text1,Toast.LENGTH_LONG).show();
                          index1=parent.getSelectedItemPosition();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                closefont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_font.cancel();
                    }
                });
                buttonfont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences settings=getActivity().getSharedPreferences("FONT",0);
                        SharedPreferences.Editor editor=settings.edit();
                         if(index1==1)
                        {
                            editor.putInt(getString(R.string.font),R.font.aladin);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(), getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==2)
                        {
                            editor.putInt(getString(R.string.font),R.font.aguafina_script);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==3)
                        {
                            editor.putInt(getString(R.string.font),R.font.alfa_slab_one);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==4)
                        {
                            editor.putInt(getString(R.string.font),R.font.anonymous_pro);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==5)
                        {
                            editor.putInt(getString(R.string.font),R.font.architects_daughter);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==6)
                        {
                            editor.putInt(getString(R.string.font),R.font.astloch);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==7)
                        {
                            editor.putInt(getString(R.string.font),R.font.athiti_extralight);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==8)
                        {
                            editor.putInt(getString(R.string.font),R.font.boogaloo);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==9)
                        {
                            editor.putInt(getString(R.string.font),R.font.adamina);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==10)
                        {
                            editor.putInt(getString(R.string.font),R.font.finger_paint);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if(index1==11)
                        {
                            editor.putInt(getString(R.string.font),R.font.tienne);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else if (index1==12)
                        {
                            editor.putInt(getString(R.string.font),R.font.tienne);
                            editor.apply();
                            dialog_font.cancel();
                            Toast.makeText(getContext(),getResources().getString(R.string.Thanhcong),Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(), getResources().getString(R.string.Error),Toast.LENGTH_LONG).show();
                        }
//                        editor.apply();
//                        dialog_font.cancel();
//                        Toast.makeText(getContext(),"Thành công",Toast.LENGTH_LONG).show();
                    }
                });
                dialog_font.setCancelable(false);
                dialog_font.show();
            }
        });
        return v;
    }

    private void recreateActivity() {
        Intent intent=this.getActivity().getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.getActivity().finish();
        this.getActivity().overridePendingTransition(0,0);
        startActivity(intent);
        this.getActivity().overridePendingTransition(0,0);
    }

    private void ChangeLanguage() {
        final String[] listItems= {getString(R.string.TiengViet),getString(R.string.Anh),getString(R.string.NhatBan),getString(R.string.TrungQuoc)};
        AlertDialog.Builder mbuiler=new AlertDialog.Builder(FragmentSetting.this.getContext());
        mbuiler.setTitle(getResources().getString(R.string.Chonngonngu));
        SharedPreferences settings=getActivity().getSharedPreferences("NGONNGU",0);
        final SharedPreferences.Editor editor=settings.edit();
        mbuiler.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0 )
                {
                    setLocale("vi");
                    String ngonngu="vi";
                    editor.putString(getString(R.string.ngongu),ngonngu);
                    getActivity().recreate();
                }
                else if(which==1)
                {
                    setLocale("en");
                    String ngonngu="en";
                    editor.putString(getString(R.string.ngongu),ngonngu);
                    getActivity().recreate();
                }
                else if(which==2)
                {
                    setLocale("ja");
                    String ngonngu="ja";
                    editor.putString(getString(R.string.ngongu),ngonngu);
                    getActivity().recreate();
                }
                else if(which==3)
                {
                    setLocale("zh");
                    String ngonngu="zh";
                    getActivity().recreate();
                }
                else
                {
                    setLocale("vi");
                    String ngonngu="vi";
                    editor.putString(getString(R.string.ngongu),ngonngu);
                    getActivity().recreate();
                }
                dialog.dismiss();

            }
        });
        AlertDialog mDialog=mbuiler.create();
        editor.apply();
        mDialog.show();
    }

    private void setLocale(final String lang) {
        if(lang.equalsIgnoreCase(""))return;
        Locale mlocale=new Locale(lang);
        Locale.setDefault(mlocale);
        Configuration configuration=new Configuration();
        configuration.locale=mlocale;
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
        editor=this.getActivity().getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    private void LoadLocale() {
        sharedPreferences=this.getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        String lang=sharedPreferences.getString("My_Lang","");
        setLocale(lang);
    }
    public Typeface settype()
    {
        SharedPreferences settingfont=getActivity().getSharedPreferences("FONT",0);
        int font=settingfont.getInt(getString(R.string.font),0);
        if(font>0)
        {
            Typeface tf= ResourcesCompat.getFont(getContext(),font);
            return tf;
        }
        else
        {
            return null;
        }
    }


}
