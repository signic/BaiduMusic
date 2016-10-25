package lanou.baidumusic.mine;

import android.support.design.widget.TabLayout;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/21.
 */
public class MineFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        TabLayout tb_main = bindView(R.id.tb_main);
    }

    @Override
    protected void initData() {

    }
}
