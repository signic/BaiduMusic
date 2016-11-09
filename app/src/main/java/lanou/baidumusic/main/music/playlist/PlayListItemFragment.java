package lanou.baidumusic.main.music.playlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.PlayListItemBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
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
    private Toolbar toolbar;
    private ImageView ivBig;
    private TextView tvTitle;
    private TextView tvTag;
    private TextView tvListenNum;
    private TextView tvSongNum;
    private ImageButton ibReturn;
    private ImageView ivBackground;
    private TextView tvUsername;
    private int songNum;
    private String username;
    private String title;
    private int listenNum;
    private DBTools dbTools;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_playlist_item_playlist;
    }

    @Override
    protected void initView() {
        rvList = bindView(R.id.rv_music_playlist_list);
        ivBig = bindView(R.id.iv_playlist_list_big);
        tvTitle = bindView(R.id.tv_playlist_list_title);
        tvTag = bindView(R.id.tv_playlist_list_tag);
        tvListenNum = bindView(R.id.tv_playlist_list_listennum);
        tvSongNum = bindView(R.id.tv_playlist_list_songnum);
        tvUsername = bindView(R.id.tv_playlist_list_username);
        ibReturn = bindView(R.id.ib_playlist_return);
        ivBackground = bindView(R.id.iv_playlist_list_background);
        toolbar = bindView(R.id.toolbar);

        dbTools = new DBTools(getActivity());
        itemAdapter = new PlayListItemAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(manager);
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String listId = arguments.getString("listId");
        songNum = arguments.getInt("songNum");
        listenNum = arguments.getInt("listenNum");
        username = arguments.getString("username");
        title = arguments.getString("title");
        GsonRequest<PlayListItemBean> gsonRequest = new GsonRequest<>(PlayListItemBean.class,
                Values.MUSIC_PLAYLIST_LIST_FRONT +  listId + Values.MUSIC_PLAYLIST_LIST_BEHIND,
                new Response.Listener<PlayListItemBean>() {

                    @Override
                    public void onResponse(final PlayListItemBean response) {
                        // 请求成功的方法
                        VolleySingleton.getInstance().getImage(response.getPic_300(), ivBig);
                        tvTitle.setText(title);
                        tvTag.setText(response.getTag());
                        tvListenNum.setText(String.valueOf(listenNum));
                        tvSongNum.setText("/" + String.valueOf(songNum) + "首歌");
                        tvUsername.setText(username);

                        VolleySingleton.getInstance().getImage(response.getPic_300(), new
                                 VolleySingleton.GetBitmap() {
                            @Override
                            public void onGetBitmap(Bitmap bitmap) {
//                                bitmap = FastBlur.doBlur(bitmap, 10, true);
                                ivBackground.setImageBitmap(bitmap);
                            }
                        });


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


        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onItemClickListener(String sId, int position) {
        GsonLink(sId, position);
    }

    private void GsonLink(final String songId, final int position) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                Intent intent = new Intent("sendInfo");
                intent.putExtra("pic", response.getSonginfo().getPic_premium());
                intent.putExtra("title", response.getSonginfo().getTitle());
                intent.putExtra("author", response.getSonginfo().getAuthor());
                intent.putExtra("albumTitle", response.getSonginfo().getAlbum_title());
                intent.putExtra("lyrLink", response.getSonginfo().getLrclink());
                intent.putExtra("position", position);
                intent.putExtra("isPlaying", true);
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