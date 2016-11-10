package lanou.baidumusic.main.music.playlist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.PlayListBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/24.
 */
public class PlayListFragment extends BaseFragment implements OnPlayListClickListener {

    private TextView tvLastest;
    private TextView tvHostest;
    private RecyclerView rvPlay;
    private PlayListAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_playlist;
    }

    @Override
    protected void initView() {
        rvPlay = bindView(R.id.rv_play);
        tvLastest = bindView(R.id.tv_lastest);
        tvHostest = bindView(R.id.tv_hostest);

        adapter = new PlayListAdapter(getActivity());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvPlay.setLayoutManager(manager);
        RecyclerViewHeader header = bindView(R.id.rv_header_playlist);
        header.attachTo(rvPlay, true);
    }

    @Override
    protected void initData() {
        GsonData(Values.MUSIC_PLAYLIST_HOT);
        tvHostest.setTextColor(Color.BLUE);

        tvHostest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLastest.setTextColor(Color.GRAY);
                tvHostest.setTextColor(Color.BLUE);
                GsonData(Values.MUSIC_PLAYLIST_HOT);
            }
        });

        tvLastest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHostest.setTextColor(Color.GRAY);
                tvLastest.setTextColor(Color.BLUE);
                GsonData(Values.MUSIC_PLAYLIST_LAST);
            }
        });
    }

    private void GsonData(String url) {
        GsonRequest<PlayListBean> gsonRequest = new GsonRequest<>(PlayListBean.class, url,
                new Response.Listener<PlayListBean>() {
                    @Override
                    public void onResponse(PlayListBean response) {
                        // 请求成功的方法
                        adapter.setPlayListItemClickListener(PlayListFragment.this);
                        adapter.setBean(response);
                        rvPlay.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    @Override
    public void onPlayListClick(String listId, int songNum, int listenNum, String username, String title) {
        PlayListItemFragment fragmentItem = new PlayListItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("listId", listId);
        bundle.putInt("songNum", songNum);
        bundle.putInt("listenNum", listenNum);
        bundle.putString("username", username);
        bundle.putString("title", title);
        fragmentItem.setArguments(bundle);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replace_view, fragmentItem);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
