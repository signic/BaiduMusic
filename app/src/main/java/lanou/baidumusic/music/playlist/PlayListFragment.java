package lanou.baidumusic.music.playlist;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
        RecyclerView rv_play = bindView(R.id.rv_play);
        final TextView tv_lastest = bindView(R.id.tv_lastest);
        final TextView tv_hostest = bindView(R.id.tv_hostest);

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

        tv_hostest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lastest.setTextColor(Color.GRAY);
                tv_hostest.setTextColor(Color.BLUE);
            }
        });

        tv_lastest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hostest.setTextColor(Color.GRAY);
                tv_lastest.setTextColor(Color.BLUE);
            }
        });

    }

    @Override
    protected void initData() {

    }
}
