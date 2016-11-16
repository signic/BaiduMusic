package lanou.baidumusic.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.main.dynamic.DynamicFragment;
import lanou.baidumusic.main.live.LiveFragment;
import lanou.baidumusic.main.mine.MineFragment;
import lanou.baidumusic.main.music.MusicFragment;
import lanou.baidumusic.main.music.poplist.ListAdapter;
import lanou.baidumusic.more.MoreFragment;
import lanou.baidumusic.play.PlayActivity;
import lanou.baidumusic.search.SearchFragment;
import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.AtyToSerEvent;
import lanou.baidumusic.tool.state.SerToAtyEvent;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.widget.PlayerService;

public class MainActivity extends BaseActivity {

    private DBTools dbTools;
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
    private ArrayList<ListBean> listBeanArrayList;
    private ListAdapter listAdapter;
    private ListView lvList;
    private TextView tvPopDelete;
    private ImageButton ibNext;
    private int pos;
    private int state;
    private String pic;
    private String title;
    private String author;
    private String albumTitle;
    private int duration;
    private PlayerService.PlayerBinder binder;
    private ServiceConnection connection;

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
        ivMainPlay = bindView(R.id.iv_main_play);
        tvTitle = bindView(R.id.tv_main_title);
        tvAuthor = bindView(R.id.tv_main_author);
        ibTwins = bindView(R.id.ib_main_twins);
        ibNext = bindView(R.id.ib_main_next);
        ibList = (ImageButton) findViewById(R.id.ib_main_list);

        dbTools = new DBTools(this);
        fragments = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        listBeanArrayList = new ArrayList<>();

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

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (PlayerService.PlayerBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

        EventBus.getDefault().register(this);

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

        // 点击进入更多界面
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

        // 点击进入搜索界面
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

        // 播放/暂停按钮
        ibTwins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binder.getPlayerIsPlaying()) {
                    state = 0;
                    ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                } else {
                    state = 1;
                    ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                }
            }
        });

        // 下一首按钮
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 2;//表示下一首
                EventBus.getDefault().post(new AtyToSerEvent(state, pos));
            }
        });

        // 点击跳转到播放详情界面
        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, PlayActivity.class);
                intent1.putExtra("state", state);
                intent1.putExtra("pos", pos);
                intent1.putExtra("pic", pic);
                intent1.putExtra("title", title);
                intent1.putExtra("albumTitle", albumTitle);
                intent1.putExtra("author", author);
                intent1.putExtra("duration", duration);
                startActivity(intent1);
            }
        });

        // 菜单按钮,点击弹出菜单
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing()) {
                    popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                            .LayoutParams.WRAP_CONTENT);
                    View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop, null);
                    lvList = (ListView) view.findViewById(R.id.lv_main_list);
                    tvPopDelete = (TextView) view.findViewById(R.id.tv_pop_delete);
                    popupWindow.setContentView(view);
                    popupWindow.showAsDropDown(ibList, 0, -1550);

                    // 点击一条信息
                    lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            state = 3;
                            ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                            EventBus.getDefault().post(new AtyToSerEvent(state, position));
                        }
                    });

                    // 弹出的popup,点击清空列表
                    tvPopDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listBeanArrayList.clear();
                            listAdapter.setBeanArrayList(listBeanArrayList);
                            lvList.setAdapter(listAdapter);
                        }
                    });

                    // 将选中歌曲所在的列表加入播放列表
                    listBeanArrayList = dbTools.QueryAllSong();
                    listAdapter.setBeanArrayList(listBeanArrayList);
                    lvList.setAdapter(listAdapter);
                } else {
                    popupWindow.dismiss();
                }
            }
        });
    }

    // 接收从PlayerService传来的数据
    @Subscribe
    public void getServiceEvent(SerToAtyEvent serToAtyEvent) {
        pic = serToAtyEvent.getPic();
        title = serToAtyEvent.getTitle();
        albumTitle = serToAtyEvent.getAlbumTitle();
        author = serToAtyEvent.getAuthor();

        VolleySingleton.getInstance().getImage(pic, ivMainPlay);
        tvTitle.setText(title);
        tvAuthor.setText(author);
        // 按钮图片变成播放图片
        ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService(connection);
    }

    // 使popupwindow消失
    public void dissMissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
