package lanou.baidumusic.main.music.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.PlayListItemBean;

/**
 * Created by dllo on 16/11/3.
 */
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder> {
    Context mContext;
    PlayListItemBean bean;
    private ListItemViewHolder viewHolder;

    public ListItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(PlayListItemBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_playlist_item_playlist_item, parent, false);
        viewHolder = new ListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        holder.tvTitle.setText(bean.getResult().getSonglist().get(position).getTitle());
        Log.d("ListItemAdapter", bean.getResult().getSonglist().get(position).getTitle());
        holder.tvAuthor.setText(bean.getResult().getSonglist().get(position).getAuthor());
        if (bean.getResult().getSonglist().get(position).getHas_mv_mobile() == 1) {
            holder.ivMv.setImageResource(R.mipmap.ic_mv);
        }
        if (bean.getResult().getSonglist().get(position).getIs_ksong().equals("1")) {
            holder.ivK.setImageResource(R.mipmap.ic_mike_normal);
        }
        holder.ivSq.setImageResource(R.mipmap.ic_sq);
    }

    @Override
    public int getItemCount() {
        Log.d("ListItemAdapter", "bean:" + bean);
        Log.d("ListItemAdapter", "bean.getError_code():" + bean.getError_code());
        Log.d("ListItemAdapter", "bean.getResult():" + bean.getResult());
        Log.d("ListItemAdapter", "bean.getResult().getSonglist():" + bean.getResult().getSonglist());

        return bean.getResult().getSonglist() == null ? 0 : bean.getResult().getSonglist().size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvAuthor;
        private ImageView ivMv;
        private ImageView ivSq;
        private ImageView ivK;
        private ImageView ivMore;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_playlist_list_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_playlist_list_author);
            ivMv = (ImageView) itemView.findViewById(R.id.iv_playlist_list_mv);
            ivSq = (ImageView) itemView.findViewById(R.id.iv_playlist_list_sq);
            ivK = (ImageView) itemView.findViewById(R.id.iv_playlist_list_k);
            ivMore = (ImageView) itemView.findViewById(R.id.iv_playlist_list_more);
        }
    }
}
