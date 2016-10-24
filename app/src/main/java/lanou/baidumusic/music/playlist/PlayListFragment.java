package lanou.baidumusic.music.playlist;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/24.
 */
public class PlayListFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_music_playlist;
    }

    @Override
    protected void initView() {
        RecyclerView rv_play = (RecyclerView) getView().findViewById(R.id.rv_play);

        ArrayList<PlayListBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            PlayListBean bean = new PlayListBean();
            bean.setTitle("title");
            bean.setCount("4219");
            bean.setAuthor("zuohze");
            arrayList.add(bean);
        }

        PlayListAdapter adapter = new PlayListAdapter(getActivity());
        adapter.setBeanArrayList(arrayList);
        rv_play.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv_play.setLayoutManager(manager);

    }

    @Override
    protected void initData() {

    }
}
