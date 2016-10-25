package lanou.baidumusic.music.toplist;

import android.widget.ListView;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/24.
 */
public class TopListFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_music_toplist;
    }

    @Override
    protected void initView() {
        ListView lv_toplist = bindView(R.id.lv_toplist);

        ArrayList<TopListBean> beanArrayList = new ArrayList<>();
        TopListAdapter adapter = new TopListAdapter(getActivity());
        for (int i = 0; i < 15; i++) {
            TopListBean bean = new TopListBean();
            bean.setTitle("新歌榜");
            bean.setTop1("下完这场雨-后铉");
            bean.setTop2("呵护-梁静茹");
            bean.setTop3("你在终点等我-王菲");
            beanArrayList.add(bean);
        }
        adapter.setBeanArrayList(beanArrayList);
        lv_toplist.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
