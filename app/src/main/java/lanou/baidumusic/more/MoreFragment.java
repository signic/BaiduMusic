package lanou.baidumusic.more;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import lanou.baidumusic.R;
import lanou.baidumusic.base.BaseFragment;

/**
 * Created by dllo on 16/10/25.
 */
public class MoreFragment extends BaseFragment {

    String[] names = {"我的消息", "成为白金VIP", "设置", "定时关闭", "电脑导歌", "活动专区", "精品应用"};

    int[] pics = {R.mipmap.icon_option_setting_my_message, R.mipmap.icon_option_setting_vip2,
                    R.mipmap.icon_option_setting_setting, R.mipmap.icon_option_setting_auto_close,
                    R.mipmap.icon_option_setting_pc_sync, R.mipmap.icon_option_setting_action_zone,
                    R.mipmap.icon_option_setting_app_recommend};

    @Override
    protected int getLayout() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initView() {
        ListView lv_more = bindView(R.id.lv_more);
        LinearLayout ll_more = bindView(R.id.ll_more);

        ArrayList<MoreBean> beanArrayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MoreBean bean = new MoreBean();
            bean.setName(names[i]);
            bean.setIcon(pics[i]);
            beanArrayList.add(bean);
        }

        MoreAdapter adapter = new MoreAdapter(getActivity());
        adapter.setBeanArrayList(beanArrayList);

        lv_more.setAdapter(adapter);

        ll_more.setOnClickListener(new View.OnClickListener() {
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
