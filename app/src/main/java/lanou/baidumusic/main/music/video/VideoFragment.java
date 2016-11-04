package lanou.baidumusic.main.music.video;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.VideoBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/24.
 */
public class VideoFragment extends BaseFragment {

    private TextView tvLastest;
    private TextView tvHostest;
    private RecyclerView rvVideo;
    private VideoAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_video;
    }

    @Override
    protected void initView() {
        rvVideo = bindView(R.id.rv_video);
        tvLastest = bindView(R.id.tv_lastest);
        tvHostest = bindView(R.id.tv_hostest);

        adapter = new VideoAdapter(getActivity());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvVideo.setLayoutManager(manager);
        RecyclerViewHeader header = bindView(R.id.rv_header_video);
        header.attachTo(rvVideo, true);
    }

    @Override
    protected void initData() {
        GsonData(Values.MUSIC_VIDEO_LAST);
        tvLastest.setTextColor(Color.BLUE);

        tvLastest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHostest.setTextColor(Color.GRAY);
                tvLastest.setTextColor(Color.BLUE);
                GsonData(Values.MUSIC_VIDEO_LAST);
            }
        });

        tvHostest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLastest.setTextColor(Color.GRAY);
                tvHostest.setTextColor(Color.BLUE);
                GsonData(Values.MUSIC_VIDEO_HOT);
            }
        });
    }

    private void GsonData(String url) {
        GsonRequest<VideoBean> gsonRequest = new GsonRequest<>(VideoBean.class, url
                , new Response.Listener<VideoBean>() {
            @Override
            public void onResponse(VideoBean response) {
                // 请求成功的方法
                adapter.setBean(response);
                rvVideo.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
