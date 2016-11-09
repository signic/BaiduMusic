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
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidumusic.main.music.state.StateChangeListener;
import lanou.baidumusic.main.music.state.StateEvent;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/5.
 */
public class PlayerService extends Service implements StateChangeListener {

    private MediaPlayer player;
    private PlayerBroadCastReceiver cast;
    private int pState = 0;
    private ArrayList<ListBean> listBeanArrayList;
    private DBTools dbTools;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        dbTools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
        listBeanArrayList = dbTools.QueryAllSong();
        initMediaPlayer();

        cast = new PlayerBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("sendInfo");
        registerReceiver(cast, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        playMediaPlayer();

        return super.onStartCommand(intent, flags, startId);
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
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        //文件时长 mils 毫秒，我们需要自行转换为所需的显示格式
        player.getDuration();
        //当前播放时间 单位同上
        player.getCurrentPosition();
    }

//    private void playMediaPlayer() {
//        // 当一首歌播完后自动播放下一首
//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                position = position + 1;
//                if (position < listBeanArrayList.size()) {
//                    GsonLink(position);
//                } else {
//                    GsonLink(0);
//                }
//            }
//        });
//    }

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
            case 2:
                position = position + 1;
                if (position < listBeanArrayList.size()) {
                    GsonLink(position);
                } else {
                    GsonLink(0);
                }
                break;
            case 3:
                GsonLink(position);
                break;
        }
    }

    @Subscribe
    public void getStateEvent(StateEvent stateEvent) {
        pState = stateEvent.getState();
        Log.d("PlayerService", "pPosition:" + position);
        switch (pState) {
            case 0:
                player.start();
                break;
            case 1:
                player.pause();
                break;
            case 2:
                position = position + 1;
                if (position < listBeanArrayList.size()) {
                    GsonLink(position);
                } else {
                    GsonLink(0);
                }
                break;
            case 3:
                GsonLink(position);
                break;
        }
    }

    public void GsonLink(final int position) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + listBeanArrayList.get(position).getSongId(), new Response
                .Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                Log.d("PlayerServiceBefore", "position:" + position);
                String fileLink = response.getBitrate().getFile_link();
                Log.d("PlayerServiceSuccess", fileLink);
                try {
                    player.reset();
                    player.setDataSource(PlayerService.this, Uri.parse(fileLink));
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    class PlayerBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            position = intent.getIntExtra("position", -1);
            Log.d("PlayerBroadCastReceiver", "position:" + position);
            GsonLink(position);
        }
    }
}
