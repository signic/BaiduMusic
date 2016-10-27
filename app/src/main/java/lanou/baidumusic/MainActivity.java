package lanou.baidumusic;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.dynamic.DynamicFragment;
import lanou.baidumusic.live.LiveFragment;
import lanou.baidumusic.mine.MineFragment;
import lanou.baidumusic.more.MoreFragment;
import lanou.baidumusic.music.MusicFragment;
import lanou.baidumusic.search.SearchFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        TabLayout tbMain = bindView(R.id.tb_main);
        ViewPager vpMain = bindView(R.id.vp_main);
        ImageButton ibGengduo = bindView(R.id.ib_gengduo);
        ImageButton ibSearch = bindView(R.id.ib_search);
        LinearLayout llMain = bindView(R.id.ll_main);

        ArrayList<Fragment> fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new LiveFragment());

        adapter.setFragments(fragments);
        vpMain.setAdapter(adapter);
        tbMain.setupWithViewPager(vpMain);
        tbMain.setTabTextColors(Color.GRAY, Color.WHITE);

        ibGengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new MoreFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
