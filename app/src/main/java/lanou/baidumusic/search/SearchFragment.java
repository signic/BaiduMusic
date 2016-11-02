package lanou.baidumusic.search;

import android.view.View;
import android.widget.ImageButton;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;

/**
 * Created by dllo on 16/10/26.
 */
public class SearchFragment extends BaseFragment {

    private ImageButton ibSearch;

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        ibSearch = bindView(R.id.ib_search);
    }

    @Override
    protected void initData() {
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
