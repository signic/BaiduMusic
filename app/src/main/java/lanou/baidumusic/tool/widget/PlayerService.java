package lanou.baidumusic.tool.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.AtyToSerEvent;
import lanou.baidumusic.tool.state.PlayerAtyToSerEvent;
import lanou.baidumusic.tool.state.PositionEvent;
import lanou.baidumusic.tool.state.SerToAtyEvent;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/5.
 */
public class PlayerService extends Service {

    private MediaPlayer player;
    private int state = 0;
    private ArrayList<ListBean> listBeanArrayList;
    private DBTools dbTools;
    private int position = 0;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;
    private String songId;

    @Override
    public void onCreate() {
        super.onCreate();
        initMediaPlayer();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dbTools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
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
        EventBus.getDefault().unregister(this);
    }

    // 初始化MediaPlayer
    private void initMediaPlayer() {
        player = new MediaPlayer();
        //文件时长 mils 毫秒，我们需要自行转换为所需的显示格式
        player.getDuration();
        //当前播放时间 单位同上
        player.getCurrentPosition();
    }

    // 通知栏
    private void playerNotification(String url) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.icon);

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.player_notification);
        views.setTextViewText(R.id.tv_notification_title, "A Little Braver");
        views.setTextViewText(R.id.tv_notification_author, "New Empire");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        views.setImageViewBitmap(R.id.iv_notification, bitmap);
//        VolleySingleton.getInstance().getImage(url, iv);
//        views.setImageViewUri(R.id.iv_notification, Uri.parse(url));

        Intent intent = new Intent(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 301, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ib_notification_close, pendingIntent);
        views.setOnClickPendingIntent(R.id.ib_notification_twins, pendingIntent);
        views.setOnClickPendingIntent(R.id.ib_notification_previous, pendingIntent);
        views.setOnClickPendingIntent(R.id.ib_notification_next, pendingIntent);
        builder.setContent(views);
        Notification notification = builder.build();
        manager.notify(404, notification);

//        Notification foregroundNote;
//        RemoteViews bigView = new RemoteViews(getPackageName(), R.layout.player_notification);
//        Notification.Builder mNotifyBuilder = new Notification.Builder(this);
//        foregroundNote = mNotifyBuilder.setContentTitle("some string")
//                .setContentText("Slide down on note to expand")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .build();
//        foregroundNote.bigContentView = bigView;
//        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotifyManager.notify(1, foregroundNote);
    }

    // 歌曲自动播放下一首
    private void playMediaPlayer() {
        listBeanArrayList = dbTools.QueryAllSong();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                position = position + 1;
                if (position < listBeanArrayList.size()) {
                    GsonLink(position);
                } else {
                    GsonLink(0);
                }
                title = listBeanArrayList.get(position).getTitle();
                albumTitle = listBeanArrayList.get(position).getAlbumTitle();
                author = listBeanArrayList.get(position).getAuthor();
                songId = listBeanArrayList.get(position).getSongId();
                GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                        .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
                    @Override
                    public void onResponse(PlayListSongInfoBean response) {
                        // 请求成功的方法
                        pic = response.getSonginfo().getPic_huge();
                        EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                VolleySingleton.getInstance().addRequest(gsonRequest);
            }
        });
    }

    // 接收从PlayListItemFragment发过来的数据
    @Subscribe
    public void getPositionEvent(PositionEvent positionEvent) {
        position = positionEvent.getPosition();
        GsonLink(position);
        listBeanArrayList = dbTools.QueryAllSong();
        title = listBeanArrayList.get(position).getTitle();
        albumTitle = listBeanArrayList.get(position).getAlbumTitle();
        author = listBeanArrayList.get(position).getAuthor();
        songId = listBeanArrayList.get(position).getSongId();
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                pic = response.getSonginfo().getPic_huge();
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    // 接收从MainActivity发送过来的数据
    @Subscribe
    public void getActivityEvent(AtyToSerEvent stateEvent) {
        state = stateEvent.getState();
        position = stateEvent.getPosition();
        title = stateEvent.getTitle();
        albumTitle = stateEvent.getAlbumTitle();
        author = stateEvent.getAuthor();
        switch (state) {
            case 0://播放
                player.start();
                break;
            case 1://暂停
                player.pause();
                break;
            case 2://下一首
                if (position < listBeanArrayList.size()) {
                    GsonLink(position);
                } else {
                    GsonLink(0);
                }
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
            case 3:
                GsonLink(position);
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
            case 4://上一首
                if (position > 0) {
                    GsonLink(position);
                } else {
                    GsonLink(listBeanArrayList.size() - 1);
                }
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
        }
    }

    // 接收从PlayActivity发送过来的数据
    @Subscribe
    public void getPlayerActivityEvent(PlayerAtyToSerEvent playerAtyToSerEvent) {
        state = playerAtyToSerEvent.getState();
        position = playerAtyToSerEvent.getPosition();
        title = playerAtyToSerEvent.getTitle();
        albumTitle = playerAtyToSerEvent.getAlbumTitle();
        author = playerAtyToSerEvent.getAuthor();
        switch (state) {
            case 0://播放
                player.start();
                break;
            case 1://暂停
                player.pause();
                break;
            case 2://下一首
                GsonLink(position);
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
            case 3:
                GsonLink(position);
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
            case 4://上一首
                GsonLink(position);
                EventBus.getDefault().post(new SerToAtyEvent(position, pic, title, albumTitle, author));
                break;
        }
    }

    // 解析歌曲链接
    public void GsonLink(final int position) {
        listBeanArrayList = dbTools.QueryAllSong();
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + listBeanArrayList.get(position).getSongId(), new Response
                .Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                String fileLink = response.getBitrate().getFile_link();
                String pic = response.getSonginfo().getPic_big();
                try {
                    player.reset();
                    player.setDataSource(PlayerService.this, Uri.parse(fileLink));
                    player.prepare();
                    player.start();
                    // 歌曲结束后自动播放下一首
                    playMediaPlayer();
                    // 通知栏
                    playerNotification(pic);
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

}
