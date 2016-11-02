package lanou.baidumusic.main.live;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.LiveBean;

/**
 * Created by dllo on 16/10/21.
 */
public class LiveFragment extends BaseFragment {

    private RecyclerView rvK;
    private LiveAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initView() {
        rvK = bindView(R.id.rv_live);

        adapter = new LiveAdapter(getActivity());

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        rvK.setLayoutManager(manager);

        RecyclerViewHeader header = bindView(R.id.rv_header);
        header.attachTo(rvK, true);
    }

    @Override
    protected void initData() {
        GsonData(Values.LIVE);
    }

    private void GsonData(String url) {
        GsonRequest<LiveBean> gsonRequest = new GsonRequest<>(LiveBean.class, url,
                new Response.Listener<LiveBean>() {
                    @Override
                    public void onResponse(LiveBean response) {
                        // 请求成功的方法
                        adapter.setBean(response);
                        rvK.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
