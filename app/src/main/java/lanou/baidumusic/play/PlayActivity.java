package lanou.baidumusic.play;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseActivity;

/**
 * Created by dllo on 16/10/26.
 */
public class PlayActivity extends BaseActivity {

    private ImageButton ibReturn;

    @Override
    protected int getLayout() {
        return R.layout.activity_play;
    }

    @Override
    protected void initView() {
        TabLayout tbPlay = bindView(R.id.tb_play);
        ViewPager vpPlay = bindView(R.id.vp_play);
        ibReturn = bindView(R.id.ib_play_return);

        PlayAdapter adapter = new PlayAdapter(getSupportFragmentManager());
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new InfoFragment());
        fragments.add(new MainFragment());
        fragments.add(new LyricsFragment());
        adapter.setFragments(fragments);
        vpPlay.setAdapter(adapter);
        tbPlay.setupWithViewPager(vpPlay);
        tbPlay.setTabTextColors(Color.GRAY, Color.WHITE);
    }

    @Override
    protected void initData() {
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
