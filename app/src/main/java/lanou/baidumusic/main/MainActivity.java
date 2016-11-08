package lanou.baidumusic.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.main.dynamic.DynamicFragment;
import lanou.baidumusic.main.live.LiveFragment;
import lanou.baidumusic.main.mine.MineFragment;
import lanou.baidumusic.main.music.MusicFragment;
import lanou.baidumusic.main.music.poplist.ListAdapter;
import lanou.baidumusic.main.music.state.StateChangeListener;
import lanou.baidumusic.main.music.state.StateEvent;
import lanou.baidumusic.more.MoreFragment;
import lanou.baidumusic.play.PlayActivity;
import lanou.baidumusic.search.SearchFragment;
import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.widget.PlayerService;

public class MainActivity extends BaseActivity {

    private StateChangeListener stateChangeListener;
    private DBTools dbTools;

    public void setStateChangeListener(StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    private boolean isPlaying = false;
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
    private ImageView ivMainPlay;
    private TextView tvTitle;
    private TextView tvAuthor;
    private MainBroadCastReceiver cast;
    private String pic;
    private String title;
    private String author;
    private String albumTitle;
    private ArrayList<ListBean> listBeanArrayList;
    private ListBean listBean;
    private ListAdapter listAdapter;
    private ListView lvList;
    private View view;
    private TextView tvPopDelete;
    private ImageButton ibNext;
    private int position;

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
        ivMainPlay = bindView(R.id.iv_main_play);
        ibNext = bindView(R.id.ib_main_next);
        tvTitle = bindView(R.id.tv_main_title);
        tvAuthor = bindView(R.id.tv_main_author);

        dbTools = new DBTools(this);
        fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        listBeanArrayList = new ArrayList<>();

        view = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop, null);
        lvList = (ListView) view.findViewById(R.id.lv_main_list);
        tvPopDelete = (TextView) view.findViewById(R.id.tv_pop_delete);
        listAdapter = new ListAdapter(MainActivity.this);

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
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);

        cast = new MainBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("sendInfo");
        registerReceiver(cast, filter);

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

        ibGengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
                popupWindow.dismiss();
                manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new SearchFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ibTwins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                if (isPlaying) {
                    ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                    EventBus.getDefault().post(new StateEvent(0));
                } else {
                    ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
                    EventBus.getDefault().post(new StateEvent(1));
                }
            }
        });

        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent1);
            }
        });

        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing()) {
                    popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                            .LayoutParams.WRAP_CONTENT);

                    popupWindow.setContentView(view);
                    popupWindow.showAsDropDown(ibList, 0, -1550);
                } else {
                    popupWindow.dismiss();
                }
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new StateEvent(0));
            }
        });

        tvPopDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBeanArrayList.clear();
                listAdapter.setBeanArrayList(listBeanArrayList);
                lvList.setAdapter(listAdapter);
            }
        });

    }

    public void dissMissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cast);
    }

    class MainBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            pic = intent.getStringExtra("pic");
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            albumTitle = intent.getStringExtra("albumTitle");
            position = intent.getIntExtra("position", -1);
            isPlaying = intent.getBooleanExtra("isPlaying", false);

            // 将选中歌曲所在的列表加入播放列表
            listBeanArrayList = dbTools.QueryAllSong();
            listAdapter.setBeanArrayList(listBeanArrayList);
            lvList.setAdapter(listAdapter);

            VolleySingleton.getInstance().getImage(pic, ivMainPlay);
            tvTitle.setText(title);
            tvAuthor.setText(author);

//            // 将选中的歌加入播放列表,实现不重复添加
//            for (int i = 0; i < listBeanArrayList.size(); i++) {
//                if (listBeanArrayList.get(i).getAuthor().equals(author) &&
//                        listBeanArrayList.get(i).getTitle().equals(title)) {
//                    listBeanArrayList.remove(i);
//                }
//            }
//            listBean = new ListBean();
//            listBean.setTitle(title);
//            listBean.setAuthor(author);
//            listBeanArrayList.add(0, listBean);
//            listAdapter.setBeanArrayList(listBeanArrayList);
//            lvList.setAdapter(listAdapter);

            // 判断状态,若正在播放,则将按钮图片变成播放图片,反之为暂停按钮.
            if (isPlaying) {
                ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
            } else {
                ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
            }

            // 点击跳转到播放详情页面,同时传值
            llPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivity.this, PlayActivity.class);
                    intent1.putExtra("pic", pic);
                    intent1.putExtra("title", title);
                    intent1.putExtra("author", author);
                    intent1.putExtra("albumTitle", albumTitle);
                    startActivity(intent1);
                }
            });

            // 点击播放下一首
            ibNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent("sendPosition");
                    intent1.putExtra("position", position);
                    sendBroadcast(intent1);
                }
            });

        }
    }
}
