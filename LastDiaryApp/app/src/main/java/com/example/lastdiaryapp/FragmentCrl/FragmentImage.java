package com.example.lastdiaryapp.FragmentCrl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lastdiaryapp.AddOrEditEventCrl.AddEventActivity;
import com.example.lastdiaryapp.ClassCrl.DatabaseEventHelper;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.ClassCrl.RecyclerEventViewAdapter;
import com.example.lastdiaryapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentImage extends Fragment {


    View v;
    private RecyclerView mrecyclerView1,mrecyclerView2;
    private List<Event> lstEvent1,lstEvent2;
    private FloatingActionButton addEventbtn;
    private DatabaseEventHelper thongthao;
    private RecyclerEventViewAdapter eventViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public FragmentImage(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.image_fragment,container,false);
        mrecyclerView1=(RecyclerView)v.findViewById(R.id.event_recyclerviewToday);
        eventViewAdapter=new RecyclerEventViewAdapter(getContext(),lstEvent1);
        mrecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerView1.setAdapter(eventViewAdapter);


        addEventbtn=(FloatingActionButton)v.findViewById(R.id.btnADD);
        addEventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AddEventActivity.class);
                startActivity(intent);
                eventViewAdapter.notifyDataSetChanged();
            }
        });
        eventViewAdapter.notifyDataSetChanged();
       return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thongthao=new DatabaseEventHelper(getContext());
        lstEvent1=thongthao.listAll();
        eventViewAdapter=new RecyclerEventViewAdapter(getContext(),lstEvent1);
        eventViewAdapter.notifyDataSetChanged();
    }
}
