package lanou.baidumusic.main.mine.download;

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
public class DownloadFragment extends BaseFragment {

    private LinearLayout llReturn;

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine_download;
    }

    @Override
    protected void initView() {
        llReturn = bindView(R.id.ll_mine_download_return);
        TabLayout tbDownload = bindView(R.id.tb_mine_download);
        ViewPager vpDownload = bindView(R.id.vp_mine_download);

        DownloadAdapter adapter = new DownloadAdapter(getChildFragmentManager());
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new DoneFragment());
        fragmentArrayList.add(new DoingFragment());
        adapter.setFragmentArrayList(fragmentArrayList);
        vpDownload.setAdapter(adapter);
        tbDownload.setupWithViewPager(vpDownload);

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
