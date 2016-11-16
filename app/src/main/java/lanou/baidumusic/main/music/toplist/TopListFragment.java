package lanou.baidumusic.main.music.toplist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.TopListBean;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.widget.DividerItemDecoration;

/**
 * Created by dllo on 16/10/24.
 */
public class TopListFragment extends BaseFragment implements OnTopListClickListener {

    private TopListAdapter adapter;
    private RecyclerView rvTopList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_toplist;
    }

    @Override
    protected void initView() {
        rvTopList = bindView(R.id.rv_toplist);
        adapter = new TopListAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvTopList.setLayoutManager(manager);
        rvTopList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        GsonRequest<TopListBean> gsonRequest = new GsonRequest<>(TopListBean.class,
                Values.MUSIC_TOPLIST, new Response.Listener<TopListBean>() {
            @Override
            public void onResponse(TopListBean response) {
                // 请求成功的方法
                adapter.setOnTopListClickListener(TopListFragment.this);
                adapter.setBean(response);
                rvTopList.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    @Override
    public void onTopClick(String type) {
        TopListItemFragment fragmentItem = new TopListItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragmentItem.setArguments(bundle);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.replace_view, fragmentItem);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
