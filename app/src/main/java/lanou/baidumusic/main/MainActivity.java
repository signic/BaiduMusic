package lanou.baidumusic.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;

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
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.AtyToSerEvent;
import lanou.baidumusic.tool.state.SerToAtyEvent;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.widget.PlayerService;

public class MainActivity extends BaseActivity {

    private DBTools dbTools;
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
    private ArrayList<ListBean> listBeanArrayList;
    private ListAdapter listAdapter;
    private ListView lvList;
    private View view;
    private TextView tvPopDelete;
    private ImageButton ibNext;
    private int pos;
    private int state;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;
    private String songId;

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
        final Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
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
                if (isPlaying) {
                    state = 0;//表示播放
                    ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos, pic, title, albumTitle, author));
                } else {
                    state = 1;//表示暂停
                    ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos, pic, title, albumTitle, author));
                }
                isPlaying = !isPlaying;
            }
        });

        // 下一首按钮
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = pos + 1;
                state = 2;//表示下一首
                ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                // 播放的图片,名字...相应改变
                GsonData(pos);
                title = listBeanArrayList.get(pos).getTitle();
                albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                author = listBeanArrayList.get(pos).getAuthor();
                EventBus.getDefault().post(new AtyToSerEvent(state, pos, pic, title, albumTitle, author));
            }
        });

        // 点击跳转到播放详情界面
        llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = listBeanArrayList.get(pos).getTitle();
                albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                author = listBeanArrayList.get(pos).getAuthor();
                Intent intent1 = new Intent(MainActivity.this, PlayActivity.class);
                intent1.putExtra("state", state);
                Log.d("MainActivity", "state:" + state);
                intent1.putExtra("pos", pos);
                intent1.putExtra("pic", pic);
                intent1.putExtra("title", title);
                intent1.putExtra("albumTitle", albumTitle);
                intent1.putExtra("author", author);
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

                    popupWindow.setContentView(view);
                    popupWindow.showAsDropDown(ibList, 0, -1550);
                } else {
                    popupWindow.dismiss();
                }
            }
        });

        // 点击弹出菜单的菜单项,作出相应的动作
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                state = 3;
                ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
                // 播放的图片,名字...相应改变
                GsonData(position);
                title = listBeanArrayList.get(pos).getTitle();
                albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                author = listBeanArrayList.get(pos).getAuthor();
                songId = listBeanArrayList.get(position).getSongId();
                GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                        .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
                    @Override
                    public void onResponse(PlayListSongInfoBean response) {
                        // 请求成功的方法
                        pic = response.getSonginfo().getPic_huge();
                        EventBus.getDefault().post(new AtyToSerEvent(state, position, pic, title, albumTitle, author));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequest);
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

    }

    // 使popupwindow消失
    public void dissMissPop() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 解析图片,歌名,歌手
    private void GsonData(final int position) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + listBeanArrayList.get(position).getSongId(),
                new Response.Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                String pics = response.getSonginfo().getPic_small();
                VolleySingleton.getInstance().getImage(pics, ivMainPlay);
                tvTitle.setText(listBeanArrayList.get(position).getTitle());
                tvAuthor.setText(listBeanArrayList.get(position).getAuthor());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    // 接收从PlayerService传来的数据
    @Subscribe
    public void getServiceEvent(SerToAtyEvent serToAtyEvent) {
        pos = serToAtyEvent.getPosition();
        pic = serToAtyEvent.getPic();
        title = serToAtyEvent.getTitle();
        albumTitle = serToAtyEvent.getAlbumTitle();
        author = serToAtyEvent.getAuthor();
        VolleySingleton.getInstance().getImage(pic, ivMainPlay);
        tvTitle.setText(title);
        tvAuthor.setText(author);
        // 按钮图片变成播放图片
        ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);

        // 将选中歌曲所在的列表加入播放列表
        listBeanArrayList = dbTools.QueryAllSong();
        listAdapter.setBeanArrayList(listBeanArrayList);
        lvList.setAdapter(listAdapter);
    }

//    class MainBroadCastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, final Intent intent) {
//            pic = intent.getStringExtra("pic");
//            title = intent.getStringExtra("title");
//            author = intent.getStringExtra("author");
//            albumTitle = intent.getStringExtra("albumTitle");
//            pos = intent.getIntExtra("position", -1);
//            isPlaying = intent.getBooleanExtra("isPlaying", false);
//
//            // 将选中歌曲所在的列表加入播放列表
//            listBeanArrayList = dbTools.QueryAllSong();
//            listAdapter.setBeanArrayList(listBeanArrayList);
//            lvList.setAdapter(listAdapter);
//
//            VolleySingleton.getInstance().getImage(pic, ivMainPlay);
//            tvTitle.setText(title);
//            tvAuthor.setText(author);
//
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
//
//            // 判断状态,若正在播放,则将按钮图片变成播放图片,反之为暂停按钮.
//            if (isPlaying) {
//                ibTwins.setImageResource(R.mipmap.bt_minibar_pause_normal);
//            } else {
//                ibTwins.setImageResource(R.mipmap.bt_minibar_play_normal);
//            }
//        }
//    }
}
