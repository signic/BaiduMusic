package lanou.baidumusic.music;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/10/24.
 */
public class MusicAdapter extends FragmentPagerAdapter {

    Context mContext;
    ArrayList<Fragment> fragmentArrayList;
    String[] titles = {"推荐", "歌单", "榜单", "视频", "k歌"};

    public MusicAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    public void setFragmentArrayList(ArrayList<Fragment> fragmentArrayList) {
        this.fragmentArrayList = fragmentArrayList;
    }

    public MusicAdapter(FragmentManager fm) {
        super(fm);
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
