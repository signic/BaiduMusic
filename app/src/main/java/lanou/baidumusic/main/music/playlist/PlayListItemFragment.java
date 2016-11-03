package lanou.baidumusic.main.music.playlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.PlayListItemBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/2.
 */
public class PlayListItemFragment extends BaseFragment {

    private RecyclerView rvList;
    private ListItemAdapter itemAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_playlist_item_playlist;
    }

    @Override
    protected void initView() {
        rvList = bindView(R.id.rv_music_playlist_list);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String listId = arguments.getString("listId");
        GsonData(Values.MUSIC_PLAYLIST_LIST_FRONT +  listId + Values.MUSIC_PLAYLIST_LIST_BEHIND);
    }

    private void GsonData(String url) {
        GsonRequest<PlayListItemBean> gsonRequest = new GsonRequest<>(PlayListItemBean.class, url,
                new Response.Listener<PlayListItemBean>() {
                    @Override
                    public void onResponse(PlayListItemBean response) {
                        // 请求成功的方法
                        itemAdapter = new ListItemAdapter(getActivity());
                        itemAdapter.setBean(response);
                        rvList.setAdapter(itemAdapter);

                        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                        rvList.setLayoutManager(manager);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

}