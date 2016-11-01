package lanou.baidumusic.main.mine;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import lanou.baidumusic.R;
import lanou.baidumusic.main.mine.download.DownloadFragment;
import lanou.baidumusic.main.mine.local.LocalFragment;
import lanou.baidumusic.tool.base.BaseFragment;
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
        LinearLayout llLocal = bindView(R.id.ll_mine_local);
        LinearLayout llDownload = bindView(R.id.ll_mine_download);
        llLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new LocalFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.replace_view, new DownloadFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
