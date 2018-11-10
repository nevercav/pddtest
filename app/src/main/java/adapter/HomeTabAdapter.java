package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.testFragment;
import fragmentofhome.PopFragment;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private final String[] mTitles;
    public HomeTabAdapter(FragmentManager fm,String[] titles){
        super(fm);
        this.mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new PopFragment();
        }
        else{
            return new testFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
