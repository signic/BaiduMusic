package lanou.baidumusic.main.music.toplist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.TopListItemBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/3.
 */
public class TopListItemFragment extends BaseFragment {

    private RecyclerView rvList;
    private TopListItemAdapter itemAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_toplist_list;
    }

    @Override
    protected void initView() {
        rvList = bindView(R.id.rv_music_toplist_list);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String type = arguments.getString("type");
        GsonData(Values.MUSIC_TOPLIST_LIST_FRONT +  type + Values.MUSIC_TOPLIST_LIST_BEHIND);
    }

    private void GsonData(String url) {
        GsonRequest<TopListItemBean> gsonRequest = new GsonRequest<>(TopListItemBean.class, url,
                new Response.Listener<TopListItemBean>() {
                    @Override
                    public void onResponse(TopListItemBean response) {
                        // 请求成功的方法
                        itemAdapter = new TopListItemAdapter(getActivity());
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
