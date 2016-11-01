package lanou.baidumusic.main.mine.download;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/11/1.
 */
public class DownloadAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentArrayList;
    String[] titles = {"已下载(0)", "正在下载(0)"};

    public DownloadAdapter(FragmentManager fm) {
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
