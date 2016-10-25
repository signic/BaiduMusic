package lanou.baidumusic.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/10/25.
 */
public class MoreAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<MoreBean> beanArrayList;

    public MoreAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBeanArrayList(ArrayList<MoreBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    @Override
    public int getCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoreViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_more_list, null);
            viewHolder = new MoreViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MoreViewHolder) convertView.getTag();
        }
        viewHolder.iv_more_list.setImageResource(beanArrayList.get(position).getIcon());
        viewHolder.tv_more_list.setText(beanArrayList.get(position).getName());
        return convertView;
    }

    private class MoreViewHolder {

        private final ImageView iv_more_list;
        private final TextView tv_more_list;

        public MoreViewHolder(View convertView) {
            iv_more_list = (ImageView) convertView.findViewById(R.id.iv_more_list);
            tv_more_list = (TextView) convertView.findViewById(R.id.tv_more_list);
        }
    }
}
