package lanou.baidumusic.tool.widget;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidumusic.main.music.state.StateChangeListener;
import lanou.baidumusic.main.music.state.StateEvent;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.database.DBTools;

/**
 * Created by dllo on 16/11/5.
 */
public class PlayerService extends Service implements StateChangeListener {

    private MediaPlayer player;
    private PlayerBroadCastReceiver cast;
    private int pState = 0;
    private ArrayList<ListBean> listBeanArrayList;
    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        DBTools dbTools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
        listBeanArrayList = dbTools.QueryAllSong();
        initMediaPlayer();

        cast = new PlayerBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("sendInfo");
        registerReceiver(cast, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(cast);
        EventBus.getDefault().unregister(this);
        player.release();
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        //文件时长 mils 毫秒，我们需要自行转换为所需的显示格式
        player.getDuration();
        //当前播放时间 单位同上
        player.getCurrentPosition();
    }

    @Override
    public void setState(int state) {
        pState = state;

        switch (pState) {
            case 0:
                player.start();
                break;
            case 1:
                player.pause();
                break;
        }
    }

    @Subscribe
    public void getStateEvent(StateEvent stateEvent) {
        pState = stateEvent.getState();

        switch (pState) {
            case 0:
                player.start();
                break;
            case 1:
                player.pause();
                break;
        }
    }

    class PlayerBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fileLink = intent.getStringExtra("fileLink");

            try {
                player.reset();
                player.setDataSource(PlayerService.this, Uri.parse(fileLink));
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
                player.reset();
            }

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (count < listBeanArrayList.size()) {
                        count++;
                    } else {
                        count = 0;
                    }
                    player.start();
                }
            });
        }
    }
}
