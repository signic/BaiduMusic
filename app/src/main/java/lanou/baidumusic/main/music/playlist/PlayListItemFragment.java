package lanou.baidumusic.main.music.playlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.PlayListItemBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.widget.DividerItemDecoration;

/**
 * Created by dllo on 16/11/2.
 */
public class PlayListItemFragment extends BaseFragment implements OnPlaylistItemClickListener {

    private RecyclerView rvList;
    private PlayListItemAdapter itemAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_playlist_item_playlist;
    }

    @Override
    protected void initView() {
        rvList = bindView(R.id.rv_music_playlist_list);

        itemAdapter = new PlayListItemAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(manager);
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
                        itemAdapter.setOnPlaylistItemClickListener(PlayListItemFragment.this);
                        itemAdapter.setBean(response);
                        rvList.setAdapter(itemAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    @Override
    public void onItemClickListener(String songId) {

        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
                    @Override
                    public void onResponse(PlayListSongInfoBean response) {
                        // 请求成功的方法
                        String fileLink = response.getBitrate().getFile_link();
                        Intent intent = new Intent("sendInfo");
                        intent.putExtra("fileLink", fileLink);
                        intent.putExtra("pic", response.getSonginfo().getPic_small());
                        intent.putExtra("title", response.getSonginfo().getTitle());
                        intent.putExtra("author", response.getSonginfo().getAuthor());
                        getActivity().sendBroadcast(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}