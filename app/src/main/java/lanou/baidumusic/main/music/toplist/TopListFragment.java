package lanou.baidumusic.main.music.toplist;

import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.TopListBean;

/**
 * Created by dllo on 16/10/24.
 */
public class TopListFragment extends BaseFragment {

    private TopListAdapter adapter;
    private ListView lvTopList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_toplist;
    }

    @Override
    protected void initView() {
        lvTopList = bindView(R.id.lv_toplist);

        adapter = new TopListAdapter(getActivity());
    }

    @Override
    protected void initData() {
        GsonRequest<TopListBean> gsonRequest = new GsonRequest<>(TopListBean.class,
                Values.MUSIC_TOPLIST, new Response.Listener<TopListBean>() {
            @Override
            public void onResponse(TopListBean response) {
                // 请求成功的方法
                adapter.setBean(response);
                lvTopList.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
