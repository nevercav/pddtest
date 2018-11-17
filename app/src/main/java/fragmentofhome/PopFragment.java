package fragmentofhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.pddtest.android.R;
import com.pddtest.android.ShowLunboActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import adapter.ChannelAdapter;
import adapter.GoodsAdapter;
import bean.Channel;
import bean.Goods;

public class PopFragment extends Fragment{

    private ViewPager viewPager;
    private RecyclerView channelRecycler;
    private List<Integer> imageResIds=new ArrayList<>();

    private List<Channel> mChannelList=new ArrayList<>();
    private List<ImageView> imageViewList;
    private LinearLayout pointContainer;
    boolean isRunning=false;
    private int prePosition=0;
    private int flag=0;
    private Thread T1;

    private RecyclerView goodsRecycler;
    private List<Goods> mGoodsList=new ArrayList<>();
    private List<String> mGoodsTitle=new ArrayList<>();
    private List<String> mGoodsPrice=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        imageResIds.add(R.drawable.lunbo1);
        imageResIds.add(R.drawable.lunbo2);
        imageResIds.add(R.drawable.lunbo3);
        imageResIds.add(R.drawable.lunbo4);
        imageResIds.add(R.drawable.lunbo5);
        imageResIds.add(R.drawable.lunbo6);
        imageResIds.add(R.drawable.lunbo7);
        imageResIds.add(R.drawable.lunbo8);

        Channel channel1=new Channel(R.drawable.r11,"限时秒杀",R.drawable.r12,"充值中心");
        Channel channel2=new Channel(R.drawable.r21,"每日清仓",R.drawable.r22,"爱逛街");
        Channel channel3=new Channel(R.drawable.r31,"品牌馆",R.drawable.r32,"现金签到");
        Channel channel4=new Channel(R.drawable.r41,"多多果园",R.drawable.r42,"食品超市");
        Channel channel5=new Channel(R.drawable.r51,"9块9特卖",R.drawable.r52,"电器城");
        Channel channel6=new Channel(R.drawable.r61,"助力享免单",R.drawable.r62,"时尚穿搭");
        Channel channel7=new Channel(R.drawable.r71,"天天半价购",R.drawable.r72,"砍价免费拿");
        Channel channel8=new Channel(R.drawable.r81,"1分抽大奖",R.drawable.r82,"帮帮免费团");
        mChannelList.add(channel1);
        mChannelList.add(channel2);
        mChannelList.add(channel3);
        mChannelList.add(channel4);
        mChannelList.add(channel5);
        mChannelList.add(channel6);
        mChannelList.add(channel7);
        mChannelList.add(channel8);
        T1=new Thread(runnable);

//        new Thread(){
//            @Override
//            public void run() {
              readFromTxt();
//            }
//        }.start();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fg_pop,container,false);
        viewPager=(ViewPager)view.findViewById(R.id.vp_lunbo);
        channelRecycler=view.findViewById(R.id.recycler_channel);
        pointContainer=(LinearLayout)view.findViewById(R.id.point_container);
        imageViewList=new ArrayList<>();

        goodsRecycler=view.findViewById(R.id.recycler_goods);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initLunboData();
        //System.out.println("???");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPosition;
                newPosition = position % imageResIds.size();
                if(pointContainer.getChildCount()!=0&&imageResIds.size()!=0) {
                    //newPosition = position % imageResIds.size();
                    pointContainer.getChildAt(prePosition).setEnabled(false);
                    pointContainer.getChildAt(newPosition).setEnabled(true);
                    //prePosition = newPosition;
                }
                prePosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        initChannelData();

        initGoodsData();
        super.onActivityCreated(savedInstanceState);
    }
    private void initLunboData(){
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        //System.out.println(pointContainer.getChildCount()+" "+imageViewList.size());
       // if(imageViewList.size()==0)
        for(int i=0;i<imageResIds.size();i++){
            imageView=new ImageView(getContext());
            imageView.setBackgroundResource(imageResIds.get(i));

            //Glide.with(getContext()).load(imageResIds.get(i)).into(imageView);
            imageViewList.add(imageView);
            pointView=new View(getContext());
            pointView.setBackgroundResource(R.drawable.lunbopoint_selector);
            layoutParams=new LinearLayout.LayoutParams(50,50);

            pointView.setEnabled(false);
            pointView.setLayoutParams(layoutParams);
            pointContainer.addView(pointView);
        }
        /*else{
            for(int i=0;i<imageResIds.size();i++) {
                pointView = new View(getContext());
                pointView.setBackgroundResource(R.drawable.lunbopoint_selector);
                layoutParams = new LinearLayout.LayoutParams(50, 50);

                pointView.setEnabled(false);
                pointView.setLayoutParams(layoutParams);
                pointContainer.addView(pointView);
            }
        }*/

        viewPager.setAdapter(new LunboAdapter());
        if(flag==0) {
            pointContainer.getChildAt(0).setEnabled(true);
            viewPager.setCurrentItem(888);
            T1.start();
            flag=1;
        }
    }
    private void initChannelData(){

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        channelRecycler.setLayoutManager(layoutManager);
        ChannelAdapter adapter=new ChannelAdapter(mChannelList);
        channelRecycler.setAdapter(adapter);
    }


    private void initGoodsData(){
        if(mGoodsList.size()==0)randomGeneration();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        goodsRecycler.setLayoutManager(layoutManager);
        GoodsAdapter adapter=new GoodsAdapter(mGoodsList);
        goodsRecycler.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }
    private void randomGeneration(){
        mGoodsList.clear();
        List<Integer> mlist=new ArrayList<>();
        for(int i=0;i<mGoodsTitle.size();i++)mlist.add(i);
        Collections.shuffle(mlist);
        for(int i=0;i<mlist.size();i++) {
            mGoodsList.add(new Goods(mlist.get(i)+1,mGoodsTitle.get(mlist.get(i)),mGoodsPrice.get(mlist.get(i))));
        }
    }
    private void readFromTxt(){
        InputStream path=null;
        Scanner in=null;
        try{
            path=getContext().getAssets().open("goods.txt");
            in=new Scanner(path,"gbk");
            while(in.hasNextLine()){
                String []lines=in.nextLine().split("&");
                if(lines.length==3){
                    mGoodsTitle.add(lines[0]);
                    mGoodsPrice.add(lines[1]);
                    //System.out.println(lines[0]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) in.close();
                if (path != null) path.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            isRunning=true;
            while(isRunning){
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(getActivity()!=null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }
                });
            }
        }
    };



    private class LunboAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            int newPosition=position%imageViewList.size();
            ImageView imageView=imageViewList.get(newPosition);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),ShowLunboActivity.class);
                    intent.putExtra("imageurl",imageResIds.get(position%imageResIds.size()));
                    startActivity(intent);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }

    @Override
    public void onDestroyView() {
        imageViewList=null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        isRunning=false;
        mChannelList.clear();
        imageResIds.clear();
        super.onDestroy();
    }
}
