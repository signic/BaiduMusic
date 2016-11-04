package lanou.baidumusic.main.music;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.main.music.karaoke.KaraokeFragment;
import lanou.baidumusic.main.music.playlist.PlayListFragment;
import lanou.baidumusic.main.music.recommendation.RecommendationFragment;
import lanou.baidumusic.main.music.toplist.TopListFragment;
import lanou.baidumusic.main.music.video.VideoFragment;
import lanou.baidumusic.tool.base.BaseFragment;

/**
 * Created by dllo on 16/10/21.
 */
public class MusicFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initView() {
        TabLayout tbMusic = bindView(R.id.tb_music);
        ViewPager vpMusic = bindView(R.id.vp_music);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new RecommendationFragment());
        fragmentArrayList.add(new PlayListFragment());
        fragmentArrayList.add(new TopListFragment());
        fragmentArrayList.add(new VideoFragment());
        fragmentArrayList.add(new KaraokeFragment());

        MusicAdapter adapter = new MusicAdapter(getChildFragmentManager());
        adapter.setFragmentArrayList(fragmentArrayList);
        vpMusic.setAdapter(adapter);
        tbMusic.setupWithViewPager(vpMusic);
    }

    @Override
    protected void initData() {

    }
}
