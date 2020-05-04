package com.akashcp.windchimeslab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import  androidx.appcompat.widget.Toolbar;

import com.anshulthakur.networkstatechecker.InternetStateChecker;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar  toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getText(R.string.Wind_Chimes_Lab));
        setSupportActionBar(toolbar);
        InternetStateChecker internetStateChecker = new InternetStateChecker.Builder(HomeActivity.this)
                .setDialogTitle("No Internet")
                .setCancelable(false)
                .setDialogBgColor(R.color.black)
                .setDialogTextColor(R.color.colorWhite)
                .setDialogIcon(R.drawable.mood)
                .setDialogMessage("Internet connection lost please turn on Internet")
                .build();
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.myviewpager);
        setupViewpager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewpager(ViewPager viewPager)
    {
        ViewpagerAdapter viewpagerAdapter=new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(new RegisterFragment(),"Register");
        viewpagerAdapter.addFragment(new ListFragment(),"List");
        viewpagerAdapter.addFragment(new StasticsFragment(),"Statistics");
        viewPager.setAdapter(viewpagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
