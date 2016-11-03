package lanou.baidumusic.main.music.toplist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.TopListItemBean;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/3.
 */
public class TopListItemAdapter extends RecyclerView.Adapter<TopListItemAdapter
        .TopListItemViewHolder> {
    Context mContext;
    TopListItemBean bean;

    public TopListItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(TopListItemBean bean) {
        this.bean = bean;
    }

    @Override
    public TopListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_toplist_list_item, parent, false);
        TopListItemViewHolder viewHolder = new TopListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopListItemViewHolder holder, int position) {
        VolleySingleton.getInstance().getImage(bean.getSong_list().get(position).getPic_small(),
                holder.ivList);
        holder.tvTitle.setText(bean.getSong_list().get(position).getTitle());
        holder.tvAuthor.setText(bean.getSong_list().get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return bean.getSong_list() == null ? 0 : bean.getSong_list().size();
    }

    public class TopListItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivList;
        private TextView tvTitle;
        private TextView tvAuthor;

        public TopListItemViewHolder(View itemView) {
            super(itemView);
            ivList = (ImageView) itemView.findViewById(R.id.iv_toplist_list);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_playlist_list_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_playlist_list_author);
        }
    }
}
