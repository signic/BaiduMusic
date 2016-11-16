package lanou.baidumusic.tool.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.state.AtyToSerEvent;
import lanou.baidumusic.tool.state.PositionEvent;
import lanou.baidumusic.tool.state.SerToAtyEvent;

/**
 * Created by dllo on 16/11/5.
 */
public class PlayerService extends Service {
    private int state;
    private int position = 0;
    private String pic;
    private String title;
    private String albumTitle;
    private String author;
    private String link;
    private MediaPlayer player;
    private ArrayList<ListBean> listBeanArrayList;
    private DBTools dbTools;
    private RemoteViews views;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        dbTools = new DBTools(this);
        listBeanArrayList = new ArrayList<>();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        PlayerBinder binder = new PlayerBinder();
        return binder;
    }

    public class PlayerBinder extends Binder {
        // 判断歌曲状态
        public boolean getPlayerIsPlaying() {
            return player.isPlaying();
        }

        // 获取歌曲时长
        public int getPlayerDuration() {
            return player.getDuration();
        }

        // 获取歌曲当前的播放进度
        public int getPlayerCurrentPosition() {
            return player.getCurrentPosition();
        }

        // 快进/快退
        public void fastMove(int progress) {
            player.seekTo(progress);
        }
    }

//    public class PlayerBinder extends Binder {
//        // 获取歌曲集合的方法
//        public ArrayList<ListBean> getListViewData() {
//            if (listBeanArrayList != null) {
//                return listBeanArrayList;
//            } else {
//                return null;
//            }
//        }
//
//        // 初始化音乐播放器
//        public void initMediaPlayer(int position) {
//            try {
//                String path = listBeanArrayList.get(position).getFileLink();
//                player.reset();
//                player.setDataSource(PlayerService.this, Uri.parse(path));
//                player.prepare();
//                player.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 播放
//        public void play() {
//            player.start();
//        }
//
//        // 暂停
//        public void pause() {
//            player.pause();
//        }
//
//        // 获取当前歌曲实体类
//        public ListBean getCurrentSongBean() {
//            return listBeanArrayList.get(position);
//        }
//
//        // 下一首
//        public void next() {
//            position++;
//            link = listBeanArrayList.get(position).getFileLink();
//            pic = listBeanArrayList.get(position).getPic();
//            title = listBeanArrayList.get(position).getTitle();
//            albumTitle = listBeanArrayList.get(position).getAlbumTitle();
//            author = listBeanArrayList.get(position).getAuthor();
//            if (position == listBeanArrayList.size()) {
//                position = 0;
//            }
//            try {
//                player.reset();
//                player.setDataSource(PlayerService.this, Uri.parse(link));
//                player.prepare();
//                player.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
//        }
//
//        // 上一首
//        public void previous() {
//            position--;
//            link = listBeanArrayList.get(position).getFileLink();
//            pic = listBeanArrayList.get(position).getPic();
//            title = listBeanArrayList.get(position).getTitle();
//            albumTitle = listBeanArrayList.get(position).getAlbumTitle();
//            author = listBeanArrayList.get(position).getAuthor();
//            if (position < 0) {
//                position = listBeanArrayList.size() - 1;
//            }
//            try {
//                player.reset();
//                player.setDataSource(PlayerService.this, Uri.parse(link));
//                player.prepare();
//                player.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
//        }
//
//        // 判断歌曲状态
//        public boolean getPlayerIsPlaying() {
//            return player.isPlaying();
//        }
//
//        // 获取歌曲时长
//        public int getPlayerDuration() {
//            return player.getDuration();
//        }
//
//        // 获取歌曲当前的播放进度
//        public int getPlayerCurrentPosition() {
//            return player.getCurrentPosition();
//        }
//
//        // 快进/快退
//        public void fastMove(int progress) {
//            player.seekTo(progress);
//        }
//
//        // 退出应用
//        public void stop() {
//            player.stop();
//            player.release();
//        }
//
//    }

    // 通知栏
    private void playerNotification() {
        listBeanArrayList = dbTools.QueryAllSong();
        title = listBeanArrayList.get(position).getTitle();
        author = listBeanArrayList.get(position).getAuthor();
        pic = listBeanArrayList.get(position).getPic();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.icon);

        views = new RemoteViews(getPackageName(), R.layout.player_notification);
        views.setTextViewText(R.id.tv_notification_title, title);
        views.setTextViewText(R.id.tv_notification_author, author);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        views.setImageViewBitmap(R.id.iv_notification, bitmap);
//        VolleySingleton.getInstance().getImage(url, iv);
//        views.setImageViewUri(R.id.iv_notification, Uri.parse(url));
        //注册广播
        NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("notification");
        registerReceiver(receiver, intentFilter);
        Intent intent = new Intent(getPackageName());
        intent.putExtra("buttonId", 0);
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 0, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        intent.putExtra("buttonId", 1);
        PendingIntent intent_twins = PendingIntent.getBroadcast(this, 1, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        intent.putExtra("buttonId", 2);
        PendingIntent intent_next = PendingIntent.getBroadcast(this, 2, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        intent.putExtra("buttonId", 3);
        PendingIntent intent_close = PendingIntent.getBroadcast(this, 3, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 301, intent, PendingIntent
//                .FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ib_notification_previous, intent_prev);
        views.setOnClickPendingIntent(R.id.ib_notification_twins, intent_twins);
        views.setOnClickPendingIntent(R.id.ib_notification_next, intent_next);
        views.setOnClickPendingIntent(R.id.ib_notification_close, intent_close);
        builder.setContent(views);
        Notification notification = builder.build();
//        manager.notify(404, notification);
        startForeground(463, notification);
    }

    class NotificationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            listBeanArrayList = dbTools.QueryAllSong();
            link = listBeanArrayList.get(position).getFileLink();
            pic = listBeanArrayList.get(position).getPic();
            title = listBeanArrayList.get(position).getTitle();
            albumTitle = listBeanArrayList.get(position).getAlbumTitle();
            author = listBeanArrayList.get(position).getAuthor();
            String action = intent.getAction();
            if (action.equals("notification")) {
                //通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra("buttonId", -1);
                switch (buttonId) {
                    // 上一首
                    case R.id.ib_notification_previous:
                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap
                                .bt_notificationbar_pause);
                        views.setImageViewBitmap(R.id.ib_notification_twins, bitmap1);
                        position--;
                        if (position > 0) {
                            initPlayer(link);
                        } else {
                            position = listBeanArrayList.size() - 1;
                            initPlayer(link);
                        }
                        EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                        break;
                    // 播放/暂停
                    case R.id.ib_notification_twins:
                        if (player.isPlaying()) {
                            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap
                                    .bt_notificationbar_pause);
                            views.setImageViewBitmap(R.id.ib_notification_twins, bitmap2);
                            player.start();
                        } else {
                            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap
                                    .bt_notificationbar_play);
                            views.setImageViewBitmap(R.id.ib_notification_twins, bitmap3);
                            player.pause();
                        }
                        break;
                    // 下一首
                    case R.id.ib_notification_next:
                        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap
                                .bt_notificationbar_pause);
                        views.setImageViewBitmap(R.id.ib_notification_twins, bitmap4);
                        position++;
                        if (position < listBeanArrayList.size()) {
                            initPlayer(link);
                        } else {
                            position = 0;
                            initPlayer(link);
                        }
                        EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                        break;
                    // 关闭
                    case R.id.ib_notification_close:
                        onDestroy();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    // 歌曲自动播放下一首
    private void playMediaPlayer() {
        listBeanArrayList = dbTools.QueryAllSong();
        title = listBeanArrayList.get(position).getTitle();
        albumTitle = listBeanArrayList.get(position).getAlbumTitle();
        author = listBeanArrayList.get(position).getAuthor();
        pic = listBeanArrayList.get(position).getPic();
        link = listBeanArrayList.get(position).getFileLink();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                position++;
                if (position < listBeanArrayList.size()) {
                    initPlayer(link);
                } else {
                    position = 0;
                    initPlayer(link);
                }
                EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
            }
        });
    }

    // 接收从PlayListItemFragment发过来的数据
    @Subscribe
    public void getPositionEvent(PositionEvent positionEvent) {
        listBeanArrayList = dbTools.QueryAllSong();
        position = positionEvent.getPosition();
        title = listBeanArrayList.get(position).getTitle();
        albumTitle = listBeanArrayList.get(position).getAlbumTitle();
        author = listBeanArrayList.get(position).getAuthor();
        pic = listBeanArrayList.get(position).getPic();
        link = listBeanArrayList.get(position).getFileLink();
        initPlayer(link);
        EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
    }

    // 音乐播放
    public void initPlayer(String link) {
        try {
            player.reset();
            player.setDataSource(PlayerService.this, Uri.parse(link));
            player.prepare();
            player.start();
            // 歌曲结束后自动播放下一首
//            playMediaPlayer();
            // 通知栏
            playerNotification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 接收从MainActivity发送过来的数据
    @Subscribe
    public void getActivityEvent(AtyToSerEvent atyToSerEvent) {
        state = atyToSerEvent.getState();
        position = atyToSerEvent.getPosition();
        listBeanArrayList = dbTools.QueryAllSong();
        pic = listBeanArrayList.get(position).getPic();
        title = listBeanArrayList.get(position).getTitle();
        albumTitle = listBeanArrayList.get(position).getAlbumTitle();
        author = listBeanArrayList.get(position).getAuthor();
        link = listBeanArrayList.get(position).getFileLink();
        switch (state) {
            case 0://播放
                player.start();
                break;
            case 1://暂停
                player.pause();
                break;
            case 2://下一首
                if (position < listBeanArrayList.size()) {
                    initPlayer(link);
                    EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                } else {
                    position = 0;
                    initPlayer(link);
                    EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                }
                Log.d("PlayerService", link);
                break;
            case 3:
                initPlayer(link);
                EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                break;
            case 4://上一首
                if (position > 0) {
                    initPlayer(link);
                } else {
                    position = listBeanArrayList.size() - 1;
                    initPlayer(link);
                }
                EventBus.getDefault().post(new SerToAtyEvent(pic, title, albumTitle, author));
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
