package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pddtest.android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.GoodsAdapter;
import adapter.RecommendAdapter;
import bean.Goods;
import fragmentofhome.PopFragment;
import utils.ReadLocalData;

public class RecommendFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recomRecycler;
    private RecommendAdapter recommendAdapter;
    private List<String> mRecomTitle = new ArrayList<>();
    ;
    private List<String> mRecomPrice = new ArrayList<>();
    ;
    private List<Goods> mRecommendList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromTxt();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_recommend, container, false);
        swipeRefresh = view.findViewById(R.id.recom_swipe_refresh);
        recomRecycler = view.findViewById(R.id.recycler_recommend);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        recommendAdapter = new RecommendAdapter(mRecommendList);
        if (mRecommendList.size() == 0) randomGeneration();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recomRecycler.setLayoutManager(gridLayoutManager);
        recomRecycler.setAdapter(recommendAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                randomGeneration();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void randomGeneration() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRecommendList.clear();
                List<Integer> mlist = new ArrayList<>();
                for (int i = 0; i < mRecomTitle.size(); i++) mlist.add(i);
                Collections.shuffle(mlist);
                for (int i = 0; i < mlist.size(); i++) {
                    mRecommendList.add(new Goods(mlist.get(i) + 1, mRecomTitle.get(mlist.get(i)), mRecomPrice.get(mlist.get(i))));
                }
                mlist.clear();
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefresh.setRefreshing(false);
                            recommendAdapter.notifyDataSetChanged();
                        }
                    });
            }
        }).start();

    }

    private void readFromTxt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> mTxtList = ReadLocalData.readFromTxt();
                for (String lines : mTxtList) {
                    String[] ss = lines.split("&");
                    if (ss.length == 3) {
                        mRecomTitle.add(ss[0]);
                        mRecomPrice.add(ss[1]);
                    }
                }
            }
        }).start();

    }

}
