package com.example.lastdiaryapp.FragmentCrl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lastdiaryapp.ClassCrl.DatabaseDiaryHelper;
import com.example.lastdiaryapp.ClassCrl.Diary;
import com.example.lastdiaryapp.ClassCrl.Event;
import com.example.lastdiaryapp.ClassCrl.RecyclerViewAdapter;
import com.example.lastdiaryapp.Custom.CustomCalendarView;
import com.example.lastdiaryapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment {

    View v;
    private RecyclerView mrecyclerView;
    private List<Diary> lstDiary;
    private TextView tvSearch;
    private ImageButton imgBtn;
    private DatabaseDiaryHelper thong;
    public FragmentSearch(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.search_fragment,container,false);
        CustomCalendarView customCalendarView=(CustomCalendarView)v.findViewById(R.id.custom);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
