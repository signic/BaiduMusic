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

    private TextView tvLastest;
    private TextView tvHostest;
    private RecyclerView rvVideo;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_video;
    }

    @Override
    protected void initView() {
        rvVideo = bindView(R.id.rv_video);
        tvLastest = bindView(R.id.tv_lastest);
        tvHostest = bindView(R.id.tv_hostest);

        ArrayList<VideoBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            VideoBean bean = new VideoBean();
            bean.setTitle("咆哮");
            bean.setAuthor("EXO");
            arrayList.add(bean);
        }

        VideoAdapter adapter = new VideoAdapter(getActivity());
        adapter.setBeanArrayList(arrayList);
        rvVideo.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvVideo.setLayoutManager(manager);

        tvLastest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHostest.setTextColor(Color.GRAY);
                tvLastest.setTextColor(Color.BLUE);
            }
        });

        tvHostest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLastest.setTextColor(Color.GRAY);
                tvHostest.setTextColor(Color.BLUE);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
