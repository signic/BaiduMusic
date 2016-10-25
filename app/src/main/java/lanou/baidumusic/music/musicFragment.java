package lanou.baidumusic.music;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;
import lanou.baidumusic.music.karaoke.KaraokeFragment;
import lanou.baidumusic.music.playlist.PlayListFragment;
import lanou.baidumusic.music.recommendation.RecommendationFragment;
import lanou.baidumusic.music.toplist.TopListFragment;
import lanou.baidumusic.music.video.VideoFragment;

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
        TabLayout tb_music = bindView(R.id.tb_music);
        ViewPager vp_music = bindView(R.id.vp_music);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new RecommendationFragment());
        fragmentArrayList.add(new PlayListFragment());
        fragmentArrayList.add(new TopListFragment());
        fragmentArrayList.add(new VideoFragment());
        fragmentArrayList.add(new KaraokeFragment());

        MusicAdapter adapter = new MusicAdapter(getActivity().getSupportFragmentManager());
        adapter.setFragmentArrayList(fragmentArrayList);
        vp_music.setAdapter(adapter);
        tb_music.setupWithViewPager(vp_music);
    }

    @Override
    protected void initData() {

    }
}
