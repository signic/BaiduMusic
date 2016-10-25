package lanou.baidumusic.music.toplist;

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
public class TopListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<TopListBean> beanArrayList;

    public TopListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBeanArrayList(ArrayList<TopListBean> beanArrayList) {
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
        TopListViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout
                    .fragment_music_toplist_item, null);
            viewHolder = new TopListViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TopListViewHolder) convertView.getTag();
        }

        viewHolder.iv_toplist.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tv_title.setText("新歌榜");
        viewHolder.tv_top1.setText("下完这场雨-后铉");
        viewHolder.tv_top2.setText("呵护-梁静茹");
        viewHolder.tv_top3.setText("你在终点等我-王菲");

        return convertView;
    }

    private class TopListViewHolder {

        private final ImageView iv_toplist;
        private final TextView tv_title;
        private final TextView tv_top1;
        private final TextView tv_top2;
        private final TextView tv_top3;

        public TopListViewHolder(View convertView) {
            iv_toplist = (ImageView) convertView.findViewById(R.id.iv_toplist);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            tv_top1 = (TextView) convertView.findViewById(R.id.tv_top1);
            tv_top2 = (TextView) convertView.findViewById(R.id.tv_top2);
            tv_top3 = (TextView) convertView.findViewById(R.id.tv_top3);
        }
    }
}
