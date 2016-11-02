package lanou.baidumusic.main.mine.local;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;

/**
 * Created by dllo on 16/11/1.
 */
public class LocalFragment extends BaseFragment {

    private LinearLayout llReturn;

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine_local;
    }

    @Override
    protected void initView() {
        TabLayout tbLocal = bindView(R.id.tb_mine_local);
        ViewPager vpLocal = bindView(R.id.vp_mine_local);
        llReturn = bindView(R.id.ll_mine_local_return);

        LocalAdapter adapter = new LocalAdapter(getChildFragmentManager());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new SongFragment());
        fragmentArrayList.add(new JiaFragment());
        fragmentArrayList.add(new SingerFragment());
        fragmentArrayList.add(new ZhuanFragment());
        adapter.setFragmentArrayList(fragmentArrayList);
        vpLocal.setAdapter(adapter);
        tbLocal.setupWithViewPager(vpLocal);

    }

    @Override
    protected void initData() {
        llReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
