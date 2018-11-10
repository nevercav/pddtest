package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pddtest.android.R;

import adapter.HomeTabAdapter;

public class HomeFragment extends Fragment {
    private TabLayout mTablayout;
    private ViewPager mVpHomeTab;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_home,container,false);
        mTablayout=(TabLayout)view.findViewById(R.id.tablayout_home);
        mVpHomeTab=(ViewPager)view.findViewById(R.id.vp_home_tab);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        String []mTitles=getResources().getStringArray(R.array.home_top_titles);
        mVpHomeTab.setAdapter(new HomeTabAdapter(getChildFragmentManager(),mTitles));
        mTablayout.setupWithViewPager(mVpHomeTab);
        for(int i=0;i<mTitles.length;i++)mTablayout.getTabAt(i).setText(mTitles[i]);
        super.onActivityCreated(savedInstanceState);
    }
}
