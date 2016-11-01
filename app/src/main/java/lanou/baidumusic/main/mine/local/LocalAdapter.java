package lanou.baidumusic.main.mine.local;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/11/1.
 */
public class LocalAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList;
    String[] titles = {"歌曲", "文件夹", "歌手", "专辑"};

    public LocalAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList == null ? 0 : fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
