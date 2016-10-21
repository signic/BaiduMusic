package lanou.baidumusic;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import lanou.baidumusic.base.BaseActivity;
import lanou.baidumusic.dynamic.dynamicFragment;
import lanou.baidumusic.live.liveFragment;
import lanou.baidumusic.mine.mineFragment;
import lanou.baidumusic.music.musicFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        TabLayout tb_main = (TabLayout) findViewById(R.id.tb_main);
        ViewPager vp_main = (ViewPager) findViewById(R.id.vp_main);

        ArrayList<Fragment> fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        fragments.add(new mineFragment());
        fragments.add(new musicFragment());
        fragments.add(new dynamicFragment());
        fragments.add(new liveFragment());

        adapter.setFragments(fragments);
        vp_main.setAdapter(adapter);
        tb_main.setupWithViewPager(vp_main);

    }

    @Override
    protected void initData() {

    }
}
