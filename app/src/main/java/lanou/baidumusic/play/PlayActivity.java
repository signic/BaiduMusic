package lanou.baidumusic.play;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseActivity;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.AtyToSerEvent;
import lanou.baidumusic.tool.widget.PlayerService;

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
    private DBTools tools;
    private ArrayList<ListBean> listBeanArrayList;
    private int state;
    private int pos;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;
    private PlayAdapter adapter;
    private ArrayList<Fragment> fragments;
    private TabLayout tbPlay;
    private ViewPager vpPlay;
    private SeekBar seekBar;
    private TextView timeStart;
    private TextView timeEnd;
    private PlayerService.PlayerBinder binder;
    private ServiceConnection connection;

    @Override
    protected int getLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        tbPlay = bindView(R.id.tb_play);
        vpPlay = bindView(R.id.vp_play);
        ibReturn = bindView(R.id.ib_play_return);
        ibTwins = bindView(R.id.ib_playpage_twins);
        ibPrevious = bindView(R.id.ib_playage_previous);
        ibNext = bindView(R.id.ib_playpage_next);
        seekBar = bindView(R.id.seekBar);
        timeStart = bindView(R.id.time_start);
        timeEnd = bindView(R.id.time_end);

        ibReturn.setOnClickListener(this);
        ibTwins.setOnClickListener(this);
        ibPrevious.setOnClickListener(this);
        ibNext.setOnClickListener(this);

        adapter = new PlayAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();
        mainFragment = new MainFragment();
        infoFragment = new InfoFragment();
        lyricsFragment = new LyricsFragment();
        fragments.add(infoFragment);
        fragments.add(mainFragment);
        fragments.add(lyricsFragment);
        adapter.setFragments(fragments);
        vpPlay.setAdapter(adapter);
        tbPlay.setupWithViewPager(vpPlay);
        vpPlay.setCurrentItem(1);
        tbPlay.setTabTextColors(Color.GRAY, Color.WHITE);
    }

    //刷新进度条的Handler
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 101){
                int index = (int) msg.obj;
                seekBar.setProgress(index);
            }
            return false;
        }
    });

    @Override
    protected void initData() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, final IBinder service) {
                binder = (PlayerService.PlayerBinder) service;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        //如果音乐播放就获取进度
//                        while (true) {
//                            if (binder != null) {
//                                if (binder.getPlayerIsPlaying()) {
//                                    int a = binder.getPlayerCurrentPosition();
//                                    //用Handler发的主线程
//                                    Message message = new Message();
//                                    message.what = 101;
//                                    message.obj = a;
//                                    handler.sendMessage(message);
//                                    try {
//                                        Thread.sleep(0);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                        }
                        // 将SeekBar位置设置到当前播放位置
                        seekBar.setProgress(binder.getPlayerCurrentPosition());
                        try {
                            // 每1000毫秒更新一次位置
                            Thread.sleep(1000);
                            //播放进度
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(PlayActivity.this, PlayerService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);

        tools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
        listBeanArrayList = tools.QueryAllSong();

        Intent intent1 = getIntent();
        state = intent1.getIntExtra("state", -1);
        pos = intent1.getIntExtra("pos", -1);
        pic = intent1.getStringExtra("pic");
        title = intent1.getStringExtra("title");
        albumTitle = intent1.getStringExtra("albumTitle");
        author = intent1.getStringExtra("author");

        Bundle bundle = new Bundle();
        bundle.putString("pic", pic);
        bundle.putString("title", title);
        bundle.putString("albumTitle", albumTitle);
        bundle.putString("author", author);
        mainFragment.setArguments(bundle);
        infoFragment.setArguments(bundle);
        lyricsFragment.setArguments(bundle);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (binder != null) {
                        binder.fastMove(progress);
                        timeStart.setText(ShowTime(progress));
                        seekBar.setProgress(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PlayerService playerService = new PlayerService();

                timeStart.setText(ShowTime(seekBar.getProgress()));
            }
        });

        if (state == 0) {
            ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
            EventBus.getDefault().post(new AtyToSerEvent(state, pos));
        } else {
            ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
            EventBus.getDefault().post(new AtyToSerEvent(state, pos));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_playpage_twins:
                if (!binder.getPlayerIsPlaying()) {
                    state = 0;//表示播放
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                    seekBar.setMax(binder.getPlayerDuration());
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                } else {
                    state = 1;//表示暂停
                    ibTwins.setImageResource(R.mipmap.bt_playpage_button_play_normal_new);
                    EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                }
                break;
            case R.id.ib_playpage_next:
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                state = 2;//表示下一首
                pos = pos + 1;
                if (pos >= listBeanArrayList.size()) {
                    pos = 0;
                }
                EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                break;
            case R.id.ib_playage_previous:
                ibTwins.setImageResource(R.mipmap.bt_playpage_button_pause_normal_new);
                state = 4;//表示上一首
                pos = pos - 1;
                if (pos < 0) {
                    pos = listBeanArrayList.size() - 1;
                }
                EventBus.getDefault().post(new AtyToSerEvent(state, pos));
                break;
            case R.id.ib_play_return:
                onBackPressed();
                break;
        }
    }

    private void setTime(int currentTime) {
        int duration = binder.getPlayerDuration();
        int minute = duration / (1000 * 60);
        int second = (duration % (1000 * 60)) / 1000;
        String minuteStr = String.valueOf(minute);
        String secondStr = String.valueOf(second);
        if (minuteStr.length() == 1) {
            minuteStr = "0" + minuteStr;
        }
        if (secondStr.length() == 1) {
            secondStr = "0" + secondStr;
        }
        timeEnd.setText(minuteStr + ":" + secondStr);
    }

    //时间显示函数,我们获得音乐信息的是以毫秒为单位的，把把转换成我们熟悉的00:00格式
    public String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

//    private void setPlayerInfo(ListBean listBean) {
//        String title = listBean.getTitle();
//        String albumTitle = listBean.getAlbumTitle();
//        String author = listBean.getAuthor();
//        String pic = listBean.getPic();
//        int duration = listBean.getDuration();
//        int minute = duration / 60000;
//        int second = duration % 60000;
//        String minuteStr = String.valueOf(minute);
//        String secondStr = String.valueOf(second);
//        if (minuteStr.length() == 1) {
//            minuteStr = "0" + minuteStr;
//        }
//        if (secondStr.length() == 1) {
//            secondStr = "0" + secondStr;
//        }
//        timeStart.setText(minuteStr + ":" + secondStr);
//        timeEnd.setText(playerBinder.getPlayerDuration());
//
//        EventBus.getDefault().post(new AtyToFragment(pic, title, albumTitle, author));
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
