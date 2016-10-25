package lanou.baidumusic;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import lanou.baidumusic.base.BaseActivity;
import lanou.baidumusic.dynamic.DynamicFragment;
import lanou.baidumusic.live.LiveFragment;
import lanou.baidumusic.mine.MineFragment;
import lanou.baidumusic.more.MoreFragment;
import lanou.baidumusic.music.MusicFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        TabLayout tb_main = bindView(R.id.tb_main);
        ViewPager vp_main = bindView(R.id.vp_main);
        ImageButton ib_gengduo = bindView(R.id.ib_gengduo);

        ArrayList<Fragment> fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new LiveFragment());

        adapter.setFragments(fragments);
        vp_main.setAdapter(adapter);
        tb_main.setupWithViewPager(vp_main);
        tb_main.setTabTextColors(Color.GRAY, Color.WHITE);

        ib_gengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new MoreFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
