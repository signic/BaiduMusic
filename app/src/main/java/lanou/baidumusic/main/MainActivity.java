package lanou.baidumusic.main;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.main.dynamic.DynamicFragment;
import lanou.baidumusic.main.live.LiveFragment;
import lanou.baidumusic.main.mine.MineFragment;
import lanou.baidumusic.main.music.MusicFragment;
import lanou.baidumusic.more.MoreFragment;
import lanou.baidumusic.play.PlayActivity;
import lanou.baidumusic.search.SearchFragment;
import lanou.baidumusic.tool.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private boolean isPlay = false;
    private MediaPlayer player;
    private ImageButton ibTwins;
    private TabLayout tbMain;
    private ViewPager vpMain;
    private ImageButton ibGengduo;
    private ImageButton ibSearch;
    private LinearLayout llPlay;
    private FragmentManager manager;
    private ImageButton ibList;
    private PopupWindow popupWindow;
    private ArrayList<Fragment> fragments;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tbMain = bindView(R.id.tb_main);
        vpMain = bindView(R.id.vp_main);
        ibGengduo = bindView(R.id.ib_gengduo);
        ibSearch = bindView(R.id.ib_search);
        llPlay = bindView(R.id.ll_main_play);
        ibTwins = bindView(R.id.ib_main_twins);
        ibList = (ImageButton) findViewById(R.id.ib_main_list);

        player = new MediaPlayer();

        fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());

        fragments.add(new MineFragment());
        fragments.add(new MusicFragment());
        fragments.add(new DynamicFragment());
        fragments.add(new LiveFragment());

        adapter.setFragments(fragments);
        vpMain.setAdapter(adapter);
        tbMain.setupWithViewPager(vpMain);
        tbMain.setTabTextColors(Color.GRAY, Color.WHITE);

    }

    @Override
    protected void initData() {

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dissMissPop();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        try {
            player.setDataSource(this, Uri.parse("http://zhangmenshiting.baidu.com/data2/music/124469076/124469076.mp3?xcode=44388abf5c8780fca0d51cfc739c1004"));
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ibGengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new MoreFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        ibTwins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlay = !isPlay;
                if (isPlay) {
                    player.start();
                    ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                } else {
                    player.pause();
                    ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
                }
            }
        });

        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing()) {
                    popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                            .LayoutParams.WRAP_CONTENT);

                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop,
                            null);
                    ListAdapter listAdapter = new ListAdapter(MainActivity.this);
                    ListView lvList = (ListView) view.findViewById(R.id.lv_main_list);
                    ArrayList<ListBean> listBeanArrayList = new ArrayList<>();
                    for (int i = 0; i < 50; i++) {
                        ListBean listBean = new ListBean();
                        listBean.setTitle("title" + i);
                        listBean.setAuthor("author" + i);
                        listBeanArrayList.add(listBean);
                    }
                    listAdapter.setBeanArrayList(listBeanArrayList);
                    lvList.setAdapter(listAdapter);
                    popupWindow.setContentView(view);
                    popupWindow.showAsDropDown(ibList, 0, -1550);

                } else {
                    popupWindow.dismiss();
                }
            }
        });
    }

    public void dissMissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
