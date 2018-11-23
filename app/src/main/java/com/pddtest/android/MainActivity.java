package com.pddtest.android;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.HistorySearchAdapter;
import adapter.SearchAdapter;
import adapter.ViewPagerAdapter;
import bean.Goods;
import fragment.CommunicateFrament;
import fragment.HomeFragment;
import fragment.PersonalFragemnt;
import fragment.QueryFragemnt;
import fragment.RecommendFragment;
import listener.OnLeftRecyclerviewItemClickListener;
import listener.SearchTagListener;
import myview.CustomViewPager;
import utils.BottomNavigationViewHelper;
import utils.ReadLocalData;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private CustomViewPager viewPager;
    private MenuItem menuItem;

    private EditText mSearchEdit;
    private List<String> searchTagList;
    private TextView backText;
    private LinearLayout searchLayout;
    private LinearLayout mainLayout;
    private RecyclerView mSearchRecycler;
    private SearchAdapter searchAdapter;

    private RecyclerView historyRecycler;
    private LinearLayout historyLayout;
    private List<String> historySearchList;
    private HistorySearchAdapter historyAdapter;
    private ImageView clearImage;

    private int width;
    private float density;
    private float screenWidth;
    private TextView paintView;
    private TextPaint paint;

    private Handler handler = new Handler();

    private SearchTagListener searchTagListener = new SearchTagListener() {
        @Override
        public void onFinish(final List<String> mTagList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchTagList.clear();
                    searchTagList.addAll(mTagList);
                    searchAdapter.notifyDataSetChanged();
                    //System.out.println("searchSize"+searchTagList.size());
                }
            });

        }

        @Override
        public void onSearchResult(List<Goods> mResultList) {

        }
    };

    private OnLeftRecyclerviewItemClickListener clickListener=new OnLeftRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v,int position,int type) {
            Intent intent=new Intent(v.getContext(),SearchResultActivity.class);
            if(type==1){
                String tag=historySearchList.get(position);
                intent.putExtra("searchtag",tag);
                historySearchList.remove(tag);
                historySearchList.add(0,tag);
            }else if(type==2){
                String tag=searchTagList.get(position);
                intent.putExtra("searchtag",tag);
                if(historySearchList.contains(tag)){
                    historySearchList.remove(tag);
                }
                historySearchList.add(0,tag);
            }
            historyAdapter.notifyDataSetChanged();
            startActivity(intent);
        }
    };

    private Runnable delaySearch = new Runnable() {
        @Override
        public void run() {
            if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                mSearchRecycler.setVisibility(View.INVISIBLE);
                historyLayout.setVisibility(View.VISIBLE);
            } else {
                mSearchRecycler.setVisibility(View.VISIBLE);
                historyLayout.setVisibility(View.INVISIBLE);
            }
            //System.out.println(mSearchEdit.getText().toString());
            ReadLocalData.searchTag((mSearchEdit.getText().toString().trim()), searchTagListener);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;         // 屏幕宽度（像素）
        density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        screenWidth = width / density;  // 屏幕宽度(dp)
        paintView=new TextView(this);
        paint=paintView.getPaint();
       // Log.d("MainActivity.java", "width:"+width+" density:"+density+" screenW:"+screenWidth);

        initView();

        initSearchView();
    }



    private int setSpanSize(int postion,List<String> mlist){
        float strWidth = paint.measureText(mlist.get(postion))/density;
        //Log.d("Main", "calculate: s="+mlist.get(postion)+" "+strWidth);
        return (int)((strWidth+20)/(screenWidth/30))+1;

    }
    private void initView() {
        mainLayout = findViewById(R.id.activity_main_layout);
        navigation = findViewById(R.id.bv);
        viewPager = findViewById(R.id.bottom_vp);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
                switch (menuItem.getItemId()) {
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
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(i);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        viewPager.setSlidingEnable(false);//禁止滑动
    }

    private void initSearchView() {
        searchLayout = findViewById(R.id.search_layout);
        mSearchEdit = findViewById(R.id.search_input_edit);
        backText = findViewById(R.id.search_back);
        mSearchRecycler = findViewById(R.id.recycler_search);
        searchTagList = new ArrayList<>();
        historyLayout=findViewById(R.id.history_layout);
        historyRecycler=findViewById(R.id.history_recycler);
        clearImage=findViewById(R.id.history_clear);
        historySearchList=new ArrayList<>();
        Drawable drawable = getResources().getDrawable(R.mipmap.sousuo);
        drawable.setBounds(0, 0, 40, 40);
        drawable= DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,getResources().getColor(R.color.systemColor));
        mSearchEdit.setCompoundDrawables(drawable, null, null, null);

        Drawable drawable2 = getResources().getDrawable(R.mipmap.ic_recent);
        drawable2.setBounds(0, 0, 40, 40);
        drawable2= DrawableCompat.wrap(drawable2);
        DrawableCompat.setTint(drawable2,getResources().getColor(R.color.systemColor));
        ((TextView)findViewById(R.id.history_text)).setCompoundDrawables(drawable2, null, null, null);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEdit.setText("");
                mSearchEdit.setSelected(false);
                searchLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });
        mSearchRecycler.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        searchAdapter = new SearchAdapter(searchTagList,clickListener);
        mSearchRecycler.setAdapter(searchAdapter);

        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String tag=v.getText().toString();
                    if(TextUtils.isEmpty(tag))tag=v.getHint().toString();
                    else if(TextUtils.isEmpty(tag.trim())){
                        Toast.makeText(v.getContext(),"搜索内容不能为空",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(historySearchList.contains(tag))
                        historySearchList.remove(tag);
                    historySearchList.add(0,tag);
                    historyAdapter.notifyDataSetChanged();
                    mSearchEdit.setText("");
                    searchLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(v.getContext(),SearchResultActivity.class);
                    intent.putExtra("searchtag",tag);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delaySearch != null) {
                    handler.removeCallbacks(delaySearch);
                }
                handler.postDelayed(delaySearch, 800);

            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,30);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return setSpanSize(position,historySearchList);
            }
        });
        historyRecycler.setLayoutManager(gridLayoutManager);
        historyAdapter=new HistorySearchAdapter(historySearchList,clickListener);
        historyRecycler.setAdapter(historyAdapter);

        clearImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historySearchList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

    }
}
