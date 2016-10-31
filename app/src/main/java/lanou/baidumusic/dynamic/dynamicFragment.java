package lanou.baidumusic.dynamic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.DividerItemDecoration;
import lanou.baidumusic.tool.GsonRequest;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.VolleySingleton;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.DynamicBean;

/**
 * Created by dllo on 16/10/21.
 */
public class DynamicFragment extends BaseFragment {

    private DynamicAdapter adapter;
    private RecyclerView rvDynamic;

    @Override
    protected int getLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        rvDynamic = bindView(R.id.rv_dynamic);
        adapter = new DynamicAdapter(getActivity());

        GsonData(Values.DYNAMIC);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDynamic.setLayoutManager(manager);
        rvDynamic.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        RecyclerViewHeader header = bindView(R.id.rv_header_dynamic);
        header.attachTo(rvDynamic, true);
    }

    @Override
    protected void initData() {

    }

    private void GsonData(String url) {
        GsonRequest<DynamicBean> gsonRequest = new GsonRequest<>(DynamicBean.class, url,
                new Response.Listener<DynamicBean>() {
                    @Override
                    public void onResponse(DynamicBean response) {
                        // 请求成功的方法
                        adapter.setBean(response);
                        rvDynamic.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
