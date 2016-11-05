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

import java.io.IOException;

/**
 * Created by dllo on 16/11/5.
 */
public class PlayerService extends Service {

    private MediaPlayer player;
    private PlayerBroadCastReceiver cast;

    @Override
    public void onCreate() {
        super.onCreate();

        cast = new PlayerBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("sendInfo");
        registerReceiver(cast, filter);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("sendState");
        registerReceiver(cast, filter);

        player = new MediaPlayer();
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
    }

    class PlayerBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String fileLink = intent.getStringExtra("fileLink");
            Log.d("PlayerBroadCastReceiver", fileLink);
            try {
                boolean state = intent.getBooleanExtra("isPlaying", false);
                if (player == null) {
                    player.setDataSource(PlayerService.this, Uri.parse(fileLink));
                    player.prepare();
                    player.start();
                    state = true;
                    Intent intent1 = new Intent("sendState1");
                    intent1.putExtra("state", state);
                    sendBroadcast(intent1);
                } else {
                    if (state) {
                        player.start();
                    } else {
                        player.pause();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
