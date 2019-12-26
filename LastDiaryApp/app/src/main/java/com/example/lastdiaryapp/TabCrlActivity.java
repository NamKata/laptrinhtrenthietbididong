package com.example.lastdiaryapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.lastdiaryapp.FragmentCrl.FragmentDiary;
import com.example.lastdiaryapp.FragmentCrl.FragmentImage;
import com.example.lastdiaryapp.FragmentCrl.FragmentSearch;
import com.example.lastdiaryapp.FragmentCrl.FragmentSetting;
import com.example.lastdiaryapp.FragmentCrl.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabCrlActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_crl);


        tabLayout=(TabLayout) findViewById(R.id.tab_layout_id);
        viewPager=(ViewPager)findViewById(R.id.view_pager_id);

        adapter=new ViewPagerAdapter(getSupportFragmentManager());

        //Add Fragment Here
        adapter.AddFragment(new FragmentDiary(),"");
        adapter.AddFragment(new FragmentImage(),"");
        adapter.AddFragment(new FragmentSearch(),"");
        adapter.AddFragment(new FragmentSetting(),"");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.diary_24px);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_access_alarm_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.search_24px);
        tabLayout.getTabAt(3).setIcon(R.drawable.settings_3_24px);

        ActionBar actionBar=getSupportActionBar();
//        actionBar.setElevation(0);
    }
}
