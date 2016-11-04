package lanou.baidumusic.main.music.playlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.PlayListItemBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/2.
 */
public class PlayListItemFragment extends BaseFragment {

    private RecyclerView rvList;
    private PlayListItemAdapter itemAdapter;

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
//        itemAdapter.setOnPlaylistItemClickListener(PlayListItemFragment.this);
    }

    private void GsonData(String url) {
        GsonRequest<PlayListItemBean> gsonRequest = new GsonRequest<>(PlayListItemBean.class, url,
                new Response.Listener<PlayListItemBean>() {
                    @Override
                    public void onResponse(PlayListItemBean response) {
                        // 请求成功的方法
                        itemAdapter = new PlayListItemAdapter(getActivity());
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

//    @Override
//    public void onItemClickListener(String songId) {
//        GsonDataItem(Values.SONG_INFO_FRONT + songId + Values.SONG_INFO_BEHIND);
//    }
//
//    private void GsonDataItem(String url) {
//        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean.class,
//                url,
//                new Response.Listener<PlayListSongInfoBean>() {
//                    @Override
//                    public void onResponse(PlayListSongInfoBean response) {
//                        // 请求成功的方法
//                        MediaPlayer player = new MediaPlayer();
//                        try {
//                            player.setDataSource(mContext, Uri.parse(response.getBitrate().getFile_link()));
//                            player.prepare();
//                            player.start();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        VolleySingleton.getInstance().addRequest(gsonRequest);
//    }
}