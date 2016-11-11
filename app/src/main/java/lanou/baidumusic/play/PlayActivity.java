package lanou.baidumusic.play;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.PlayerAtyToSerEvent;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/26.
 */
public class PlayActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton ibReturn;
    private ImageButton ibTwins;
    private ImageButton ibPrevious;
    private ImageButton ibNext;
    private MainFragment mainFragment;
    private InfoFragment infoFragment;
    private LyricsFragment lyricsFragment;
    private boolean isPlaying = false;
    private DBTools tools;
    private ArrayList<ListBean> listBeanArrayList;
    private int state;
    private int pos;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;

    @Override
    protected int getLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        TabLayout tbPlay = bindView(R.id.tb_play);
        ViewPager vpPlay = bindView(R.id.vp_play);
        ibReturn = bindView(R.id.ib_play_return);
        ibTwins = bindView(R.id.ib_playpage_twins);
        ibPrevious = bindView(R.id.ib_playage_previous);
        ibNext = bindView(R.id.ib_playpage_next);

        ibReturn.setOnClickListener(this);
        ibTwins.setOnClickListener(this);
        ibPrevious.setOnClickListener(this);
        ibNext.setOnClickListener(this);

        PlayAdapter adapter = new PlayAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();

        mainFragment = new MainFragment();
        infoFragment = new InfoFragment();
        lyricsFragment = new LyricsFragment();
        fragments.add(infoFragment);
        fragments.add(mainFragment);
        fragments.add(lyricsFragment);
        adapter.setFragments(fragments);
        vpPlay.setAdapter(adapter);
        tbPlay.setupWithViewPager(vpPlay);
        tbPlay.setTabTextColors(Color.GRAY, Color.WHITE);
    }

    @Override
    protected void initData() {
        tools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
        listBeanArrayList = tools.QueryAllSong();

        Intent intent = getIntent();
        pos = intent.getIntExtra("pos", -1);
        pic = intent.getStringExtra("pic");
        title = intent.getStringExtra("title");
        albumTitle = intent.getStringExtra("albumTitle");
        author = intent.getStringExtra("author");
//        if (state == 1) {
//            ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
//        } else {
//            ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
//        }
        ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);

        Log.d("PlayActivityll", pic);
        Log.d("PlayActivityll", title);
        Log.d("PlayActivityll", albumTitle);
        Bundle bundle = new Bundle();
        bundle.putString("pic", pic);
        bundle.putString("title", title);
        bundle.putString("albumTitle", albumTitle);
        bundle.putString("author", author);
        mainFragment.setArguments(bundle);
        infoFragment.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_playpage_twins:
                if (isPlaying) {
                    state = 0;//表示播放
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                    EventBus.getDefault().post(new PlayerAtyToSerEvent(state, pos, pic, title, albumTitle, author));
                } else {
                    state = 1;//表示暂停
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                    EventBus.getDefault().post(new PlayerAtyToSerEvent(state, pos, pic, title, albumTitle, author));
                }
                isPlaying = !isPlaying;
                break;
            case R.id.ib_playpage_next:
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                state = 2;//表示下一首
                pos = pos + 1;
                if (pos < listBeanArrayList.size()) {
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                } else {
                    pos = 0;
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                }
                EventBus.getDefault().post(new PlayerAtyToSerEvent(state, pos, pic, title, albumTitle, author));
                break;
            case R.id.ib_playage_previous:
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                state = 4;//表示上一首
                pos = pos - 1;
                if (pos >= 0) {
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                } else {
                    pos = listBeanArrayList.size() - 1;
                    title = listBeanArrayList.get(pos).getTitle();
                    albumTitle = listBeanArrayList.get(pos).getAlbumTitle();
                    author = listBeanArrayList.get(pos).getAuthor();
                }
                EventBus.getDefault().post(new PlayerAtyToSerEvent(state, pos, pic, title, albumTitle, author));
                break;
            case R.id.ib_play_return:
                onBackPressed();
                break;
        }
    }

    public void GsonPic(String songId) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                pic = response.getSonginfo().getPic_huge();
                EventBus.getDefault().post(new PlayerAtyToSerEvent(state, pos, pic, title, albumTitle, author));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

}
