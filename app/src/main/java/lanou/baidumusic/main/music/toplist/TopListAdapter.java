package lanou.baidumusic.main.music.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.TopListBean;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/25.
 */
public class TopListAdapter extends BaseAdapter {
    Context mContext;
    TopListBean bean;
    OnTopListClickListener onTopListClickListener;

    public TopListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(TopListBean bean) {
        this.bean = bean;
    }

    public void setOnTopListClickListener(OnTopListClickListener onTopListClickListener) {
        this.onTopListClickListener = onTopListClickListener;
    }

    @Override
    public int getCount() {
        return bean.getContent() == null ? 0 : bean.getContent().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getContent().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TopListViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout
                    .fragment_music_toplist_item, parent, false);
            viewHolder = new TopListViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TopListViewHolder) convertView.getTag();
        }

        VolleySingleton.getInstance().getImage(bean.getContent().get(position)
                .getPic_s192(), viewHolder.ivTopList);
        viewHolder.tvTitle.setText(bean.getContent().get(position).getName());
        viewHolder.tvTop1.setText(bean.getContent().get(position)
                .getContent().get(0).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(0).getAuthor());
        viewHolder.tvTop2.setText(bean.getContent().get(position)
                .getContent().get(1).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(1).getAuthor());
        viewHolder.tvTop3.setText(bean.getContent().get(position)
                .getContent().get(2).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(2).getAuthor());

        viewHolder.llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = String.valueOf(bean.getContent().get(position).getType());
                onTopListClickListener.onTopClick(type);
            }
        });

        return convertView;
    }

    private class TopListViewHolder {

        private ImageView ivTopList;
        private TextView tvTitle;
        private TextView tvTop1;
        private TextView tvTop2;
        private TextView tvTop3;
        private LinearLayout llTop;

        public TopListViewHolder(View convertView) {
            llTop = (LinearLayout) convertView.findViewById(R.id.ll_toplist);
            ivTopList = (ImageView) convertView.findViewById(R.id.iv_toplist);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvTop1 = (TextView) convertView.findViewById(R.id.tv_top1);
            tvTop2 = (TextView) convertView.findViewById(R.id.tv_top2);
            tvTop3 = (TextView) convertView.findViewById(R.id.tv_top3);
        }
    }
}
