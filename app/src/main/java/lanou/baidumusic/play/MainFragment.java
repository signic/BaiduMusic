package lanou.baidumusic.play;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/1.
 */
public class MainFragment extends BaseFragment {

    private ImageButton ibPlayPage;
    private TextView tvTitle;
    private TextView tvAuthor;
    private DBTools tools;
    private ArrayList<ListBean> listBeanArrayList;
    private int position;

    @Override
    protected int getLayout() {
        return R.layout.fragment_play_main;
    }

    @Override
    protected void initView() {
        ibPlayPage = bindView(R.id.ib_playpage_big);
        tvTitle = bindView(R.id.tv_playpage_title);
        tvAuthor = bindView(R.id.tv_playpage_author);
    }

    @Override
    protected void initData() {
        tools = new DBTools(getActivity());
        listBeanArrayList = new ArrayList<>();
        listBeanArrayList = tools.QueryAllSong();

        Bundle arguments = getArguments();
        position = arguments.getInt("position");
        Log.d("MainFragmentPosMain", "position:" + position);

        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean
                .class, Values.SONG_INFO + listBeanArrayList.get(position).getSongId(), new Response
                .Listener<PlayListSongInfoBean>() {
            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                VolleySingleton.getInstance().getImage(response.getSonginfo().getPic_premium(), ibPlayPage);
                tvTitle.setText(listBeanArrayList.get(position).getTitle());
                tvAuthor.setText(listBeanArrayList.get(position).getAuthor());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

}
