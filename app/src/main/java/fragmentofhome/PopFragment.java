package fragmentofhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.pddtest.android.R;
import com.pddtest.android.ShowLunboActivity;

import java.util.ArrayList;
import java.util.List;

public class PopFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private List<Integer> imageResIds;
    private List<ImageView> imageViewList;
    private LinearLayout pointContainer;
    boolean isRunning=false;
    private int prePosition=0;
    private int cur;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_pop,container,false);
        viewPager=(ViewPager)view.findViewById(R.id.vp_lunbo);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pointContainer=(LinearLayout)view.findViewById(R.id.point_container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();
        new Thread(runnable).start();
        super.onActivityCreated(savedInstanceState);
    }
    private void initData(){
        imageResIds=new ArrayList<>();
        imageViewList=new ArrayList<>();
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;

        imageResIds.add(R.drawable.lunbo1);
        imageResIds.add(R.drawable.lunbo2);
        imageResIds.add(R.drawable.lunbo3);
        imageResIds.add(R.drawable.lunbo4);
        imageResIds.add(R.drawable.lunbo5);
        imageResIds.add(R.drawable.lunbo6);
        imageResIds.add(R.drawable.lunbo7);
        imageResIds.add(R.drawable.lunbo8);
        for(int i=0;i<imageResIds.size();i++){
            cur=i;
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

        pointContainer.getChildAt(0).setEnabled(true);
        viewPager.setAdapter(new LunboAdapter());
        viewPager.setCurrentItem(0);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }
                });
            }
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition=position%imageViewList.size();
        pointContainer.getChildAt(prePosition).setEnabled(false);
        pointContainer.getChildAt(newPosition).setEnabled(true);
        prePosition=newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

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
        super.onDestroyView();
        isRunning=false;
    }
}
