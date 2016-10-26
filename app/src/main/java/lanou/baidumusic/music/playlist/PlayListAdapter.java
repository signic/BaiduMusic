package lanou.baidumusic.music.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/10/24.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    Context mContext;
    PlayListBean bean;

    public PlayListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(PlayListBean bean) {
        this.bean = bean;
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_playlist_item, null);
        PlayListViewHolder viewHolder = new PlayListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        Picasso.with(mContext).load(bean.getContent().get(position)
                .getPic_300()).into(holder.ivBackground);
        holder.tvCount.setText(bean.getContent().get(position).getListenum());
        holder.tvIntroduction.setText(bean.getContent().get(position).getTitle());
        holder.tvAuthor.setText("by super悟空");
    }

    @Override
    public int getItemCount() {
        return bean.getContent().size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlPlayList;
        private final ImageView ivPlayList;
        private final TextView tvCount;
        private final ImageButton ibPlayList;
        private final TextView tvIntroduction;
        private final TextView tvAuthor;
        private final ImageView ivBackground;

        public PlayListViewHolder(View itemView) {
            super(itemView);
            rlPlayList = (RelativeLayout) itemView.findViewById(R.id.rl_playList);
            ivBackground = (ImageView) itemView.findViewById(R.id.iv_playList_background);
            ivPlayList = (ImageView) itemView.findViewById(R.id.iv_playList);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            ibPlayList = (ImageButton) itemView.findViewById(R.id.ib_playList);
            tvIntroduction = (TextView) itemView.findViewById(R.id.tv_introduction);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
