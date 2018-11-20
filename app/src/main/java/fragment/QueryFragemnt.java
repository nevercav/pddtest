package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pddtest.android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.LeftTypeAdapter;
import adapter.RightOutAdapter;
import bean.Type;
import listener.OnLeftRecyclerviewItemClickListener;
import utils.ReadLocalData;

public class QueryFragemnt extends Fragment {
    private List<String> mLeftTypeList;
    private RecyclerView leftRecycler;
    private LeftTypeAdapter leftAdapter;
    private RecyclerView rightRecycler;
    private RightOutAdapter rightAdapter;
    private List<List<Type>> mSrcList;
    private int lastItem=0;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_query,container,false);
        leftRecycler=view.findViewById(R.id.left_recycler);
        rightRecycler=view.findViewById(R.id.right_out_recycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String []mTitles=getResources().getStringArray(R.array.query_type);
        mLeftTypeList= Arrays.asList(mTitles);
        mSrcList= ReadLocalData.readSrc();
//        for(List<Type> lt:mSrcList){
//            for (Type t:lt){
//                System.out.print(t.getImageName()+" ");
//            }
//            System.out.println();
//        }
        initLeftAdapter();
        initRightAdapter();


        super.onActivityCreated(savedInstanceState);
    }

    private void initLeftAdapter() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        leftRecycler.setLayoutManager(layoutManager);
        leftAdapter=new LeftTypeAdapter(mLeftTypeList, new OnLeftRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                leftAdapter.setSelection(position);
                rightRecycler.smoothScrollToPosition(position);
            }
        });

        leftRecycler.setAdapter(leftAdapter);
    }

    private void initRightAdapter() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        rightRecycler.setLayoutManager(layoutManager);
        rightAdapter=new RightOutAdapter(mSrcList,mLeftTypeList);
        rightRecycler.setAdapter(rightAdapter);
        rightRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //if(newState!=RecyclerView.SCROLL_STATE_IDLE){
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if(firstVisibleItem!=lastItem&&lastVisibleItem!=totalItemCount-1) {
                        lastItem=firstVisibleItem;
                        final int cur=firstVisibleItem;
                       // leftRecycler.post(new Runnable() {
                       //     @Override
                       //     public void run() {
                                leftAdapter.setSelection(cur);
                                leftRecycler.scrollToPosition(lastItem);
                       //     }
                       // });
                    }
                    else if(lastVisibleItem==totalItemCount-1){
                        lastItem=lastVisibleItem;
                        final int cur=lastVisibleItem;
                       // leftRecycler.post(new Runnable() {
                       //     @Override
                        //    public void run() {
                                leftAdapter.setSelection(cur);
                                leftRecycler.scrollToPosition(lastItem);
                        //    }
                       // });

                    }
               // }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView,dx,dy);

            }
        });
    }

}
