package com.pddtest.android;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.RecommendAdapter;
import bean.Goods;
import listener.SearchTagListener;
import utils.ReadLocalData;

public class SearchResultActivity extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private ImageView backImage;
    private List<Goods> mSearchResultList;
    private RecommendAdapter adapter;
    private String searchTag;
    private SearchTagListener listener=new SearchTagListener() {
        @Override
        public void onFinish(List<String> mTagList) {

        }

        @Override
        public void onSearchResult(final List<Goods> mResultList) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSearchResultList.clear();
                    mSearchResultList.addAll(mResultList);
                    //System.out.println(mSearchResultList.size()+"result");
                    adapter.notifyDataSetChanged();
                }
            });

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        searchTag=getIntent().getStringExtra("searchtag");
        initView();
    }

    private void initView() {
        textView=findViewById(R.id.search_result_text);
        recyclerView=findViewById(R.id.search_result_recycler);
        backImage=findViewById(R.id.search_result_back);
        Drawable drawable = getResources().getDrawable(R.mipmap.sousuo);
        drawable.setBounds(0, 0, 40, 40);
        drawable= DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,getResources().getColor(R.color.systemColor));
        textView.setCompoundDrawables(drawable, null, null, null);
        mSearchResultList=new ArrayList<>();
        textView.setText(searchTag);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecommendAdapter(mSearchResultList);
        recyclerView.setAdapter(adapter);
        ReadLocalData.searchResult(searchTag,listener);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
