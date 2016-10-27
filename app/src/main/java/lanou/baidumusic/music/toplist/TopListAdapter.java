package lanou.baidumusic.music.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.TopListBean;

/**
 * Created by dllo on 16/10/25.
 */
public class TopListAdapter extends BaseAdapter {
    Context mContext;
    TopListBean bean;

    public TopListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(TopListBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getContent().size();
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

//        VolleySingleton.getInstance().getImage(bean.getContent().get(position)
//                .getPic_s192(), viewHolder.ivTopList);

        Picasso.with(mContext).load(bean.getContent().get(position)
                .getPic_s192()).into(viewHolder.ivTopList);
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

        return convertView;
    }

    private class TopListViewHolder {

        private final ImageView ivTopList;
        private final TextView tvTitle;
        private final TextView tvTop1;
        private final TextView tvTop2;
        private final TextView tvTop3;

        public TopListViewHolder(View convertView) {
            ivTopList = (ImageView) convertView.findViewById(R.id.iv_toplist);
            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvTop1 = (TextView) convertView.findViewById(R.id.tv_top1);
            tvTop2 = (TextView) convertView.findViewById(R.id.tv_top2);
            tvTop3 = (TextView) convertView.findViewById(R.id.tv_top3);
        }
    }
}
