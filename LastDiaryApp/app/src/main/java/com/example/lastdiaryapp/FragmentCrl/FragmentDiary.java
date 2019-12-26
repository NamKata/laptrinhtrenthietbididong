package com.example.lastdiaryapp.FragmentCrl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastdiaryapp.AddOrEditCrl.AddActivity;
import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.RecyclerViewAdapter;
import com.example.lastdiaryapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentDiary extends Fragment {

    View v;
    private RecyclerView mrecyclerView;
    private List<Diary> lstDiary;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnTim;
    boolean isOpen=true;
    private EditText searchlbl;
    private ImageButton btnSearchAll;
    private CardView f;
    private DatabaseDiaryHelper thongthao;

    public FragmentDiary(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.diary_fragment,container,false);
        thongthao=new DatabaseDiaryHelper(getContext());
        lstDiary=thongthao.listAll();
        mrecyclerView=(RecyclerView)v.findViewById(R.id.diary_recyclerview);
        btnAdd=(FloatingActionButton)v.findViewById(R.id.btnADD);
        btnTim=(FloatingActionButton)v.findViewById(R.id.btnTim);
        searchlbl=(EditText)v.findViewById(R.id.SearchS);
        btnSearchAll=(ImageButton)v.findViewById(R.id.btnSearch);
        f=(CardView)v.findViewById(R.id.cardAll);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(getContext(),lstDiary);

        mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView.setAdapter(recyclerViewAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FragmentDiary.this.getContext(), AddActivity.class);
                startActivity(intent);
            }
        });
        if(isOpen==true)
        {
            searchlbl.setVisibility(View.GONE);
            btnSearchAll.setVisibility(View.GONE);
            f.setVisibility(View.GONE);
        }
        else
        {
            searchlbl.setVisibility(View.VISIBLE);
            btnSearchAll.setVisibility(View.VISIBLE);
            f.setVisibility(View.VISIBLE);
        }
       btnTim.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(isOpen==true)
                {
                    isOpen=false;
                    searchlbl.setVisibility(View.VISIBLE);
                    btnSearchAll.setVisibility(View.VISIBLE);
                    f.setVisibility(View.VISIBLE);
                }
                else
                {
                    isOpen=true;
                    searchlbl.setVisibility(View.GONE);
                    btnSearchAll.setVisibility(View.GONE);
                    f.setVisibility(View.GONE);
                }
           }
       });
        btnSearchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timkiem=searchlbl.getText().toString();
                if(timkiem.equals(""))
                {
                    lstDiary=thongthao.listAll();
                    RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(getContext(),lstDiary);

                    mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mrecyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                else
                {
                    lstDiary=thongthao.listSearch(timkiem);
                    if(lstDiary.size()==0)
                    {
                        searchlbl.setText("");
                    }
                    RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(getContext(),lstDiary);

                    mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mrecyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    searchlbl.setText("");
                }

            }
        });
        recyclerViewAdapter.notifyDataSetChanged();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thongthao=new DatabaseDiaryHelper(getContext());
        lstDiary =thongthao.listAll();

    }
}
