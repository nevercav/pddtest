package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fragment.testFragment;
import fragmentofhome.PopFragment;

public class HomeTabAdapter extends FragmentPagerAdapter {
    private final String[] mTitles;
    private List<Fragment> mlist=new ArrayList<>();
    private Fragment f1=null;
    private FragmentManager fm;
    public HomeTabAdapter(FragmentManager fm,String[] titles){
        super(fm);
        this.mTitles=titles;
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {
//       if(position>0)return new testFragment();
//       if(f1==null)f1=new PopFragment();
//       return f1;
        Fragment fragment;
        if(mlist.size()<=position){
            if(position==0){
                //fragment=PopFragment.getInstance();
                fragment=new PopFragment();
            }else {
                fragment=new testFragment();
            }
            mlist.add(fragment);
        }else{
            fragment=mlist.get(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fm.beginTransaction().hide(mlist.get(position)).commit();
    }
}
