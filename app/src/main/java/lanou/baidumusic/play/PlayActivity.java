package lanou.baidumusic.play;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.main.music.state.StateEvent;
import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.tool.base.MyApp;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
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
    private static int position;
    private int state;
    private MainFragment mainFragment;
    private InfoFragment infoFragment;
    private LyricsFragment lyricsFragment;
    private boolean isPlaying = false;
    private DBTools tools;
    private ArrayList<ListBean> listBeanArrayList;

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
        position = intent.getIntExtra("position", -1);
        state = intent.getIntExtra("state", -1);
        if (state == 1) {
            ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
        } else {
            ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Log.d("PlayActivity", "position:" + position);
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
            case R.id.ib_play_return:
                onBackPressed();
                break;
            case R.id.ib_playpage_twins:
                isPlaying = !isPlaying;
                if (isPlaying) {
                    state = 0;//表示播放
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                    EventBus.getDefault().post(new StateEvent(state));
                } else {
                    state = 1;//表示暂停
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
                    EventBus.getDefault().post(new StateEvent(state));
                }
                break;
            case R.id.ib_playage_previous:
                position = position - 1;
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                break;
            case R.id.ib_playpage_next:
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                position = position + 1;
                state = 2;//表示下一首
                EventBus.getDefault().post(new StateEvent(state));
                break;
        }
    }


    // 解析图片,歌名,歌手
    private void GsonData(final int position) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + listBeanArrayList.get(position).getSongId(), new Response
                .Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                String pics = response.getSonginfo().getPic_small();

                View mainView = LayoutInflater.from(MyApp.getContext()).inflate(R.layout
                        .fragment_play_main, null);
                ImageButton ibPlayPage = (ImageButton) mainView.findViewById(R.id.ib_playpage_big);
                TextView tvMainTitle = (TextView) mainView.findViewById(R.id.tv_playpage_title);
                TextView tvMainAuthor = (TextView) mainView.findViewById(R.id.tv_playpage_author);
                VolleySingleton.getInstance().getImage(pics, ibPlayPage);
                tvMainTitle.setText(listBeanArrayList.get(position).getTitle());
                tvMainAuthor.setText(listBeanArrayList.get(position).getAuthor());

                View infoView = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.fragment_play_info, null);
                ImageButton ibSmall = (ImageButton) infoView.findViewById(R.id.ib_playpage_small);
                TextView tvInfoTitle = (TextView) infoView.findViewById(R.id
                        .tv_playpage_info_title);
                TextView tvInfoAuthor = (TextView) infoView.findViewById(R.id
                        .tv_playpage_info_author);
                VolleySingleton.getInstance().getImage(pics, ibSmall);
                tvInfoTitle.setText(response.getSonginfo().getAlbum_title());
                tvInfoAuthor.setText(listBeanArrayList.get(position).getAuthor());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

}
