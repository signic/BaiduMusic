package lanou.baidumusic.more;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.MoreBean;

/**
 * Created by dllo on 16/10/25.
 */
public class MoreFragment extends BaseFragment {

    private String[] names = {"我的消息", "成为白金VIP", "设置", "定时关闭", "电脑导歌", "活动专区", "精品应用"};

    private int[] pics = {R.mipmap.icon_option_setting_my_message, R.mipmap.icon_option_setting_vip2,
                    R.mipmap.icon_option_setting_setting, R.mipmap.icon_option_setting_auto_close,
                    R.mipmap.icon_option_setting_pc_sync, R.mipmap.icon_option_setting_action_zone,
                    R.mipmap.icon_option_setting_app_recommend};
    private LinearLayout llMore;

    @Override
    protected int getLayout() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initView() {
        ListView lvMore = bindView(R.id.lv_more);
        llMore = bindView(R.id.ll_more);

        MoreAdapter adapter = new MoreAdapter(getActivity());
        ArrayList<MoreBean> beanArrayList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            MoreBean bean = new MoreBean();
            bean.setName(names[i]);
            bean.setIcon(pics[i]);
            beanArrayList.add(bean);
        }
        adapter.setBeanArrayList(beanArrayList);
        lvMore.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
