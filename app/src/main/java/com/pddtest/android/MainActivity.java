package com.pddtest.android;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import adapter.ViewPagerAdapter;
import fragment.CommunicateFrament;
import fragment.HomeFragment;
import fragment.PersonalFragemnt;
import fragment.QueryFragemnt;
import fragment.RecommendFragment;
import myview.CustomViewPager;
import utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private CustomViewPager viewPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        navigation=(BottomNavigationView)findViewById(R.id.bv);
        viewPager=findViewById(R.id.bottom_vp);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new RecommendFragment());
        adapter.addFragment(new QueryFragemnt());
        adapter.addFragment(new CommunicateFrament());
        adapter.addFragment(new PersonalFragemnt());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(0);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_recommend:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_query:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.nav_communicate:
                        viewPager.setCurrentItem(3);
                        return true;
                    case R.id.nav_personal:
                        viewPager.setCurrentItem(4);
                        return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(menuItem!=null){
                    menuItem.setChecked(false);
                }else{
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem=navigation.getMenu().getItem(i);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        /*viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });*/
        viewPager.setSlidingEnable(false);//禁止滑动

    }
}
