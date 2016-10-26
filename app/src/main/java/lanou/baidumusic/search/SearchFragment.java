package lanou.baidumusic.search;

import android.view.View;
import android.widget.ImageButton;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/26.
 */
public class SearchFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView() {
        ImageButton ibSearch = bindView(R.id.ib_search);

        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }

    @Override
    protected void initData() {

    }
}
