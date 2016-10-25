package lanou.baidumusic.music.video;

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
public class VideoFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_music_video;
    }

    @Override
    protected void initView() {
        RecyclerView rv_video = bindView(R.id.rv_video);
        final TextView tv_lastest = bindView(R.id.tv_lastest);
        final TextView tv_hostest = bindView(R.id.tv_hostest);

        ArrayList<VideoBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            VideoBean bean = new VideoBean();
            bean.setTitle("咆哮");
            bean.setAuthor("EXO");
            arrayList.add(bean);
        }

        VideoAdapter adapter = new VideoAdapter(getActivity());
        adapter.setBeanArrayList(arrayList);
        rv_video.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rv_video.setLayoutManager(manager);

        tv_lastest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hostest.setTextColor(Color.GRAY);
                tv_lastest.setTextColor(Color.BLUE);
            }
        });

        tv_hostest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lastest.setTextColor(Color.GRAY);
                tv_hostest.setTextColor(Color.BLUE);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
