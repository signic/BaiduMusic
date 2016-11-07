package lanou.baidumusic.main.music.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.PlayListBean;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/24.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private Context mContext;
    private PlayListBean bean;
    private OnPlayListClickListener playListClickListener;

    public void setPlayListItemClickListener(OnPlayListClickListener playListClickListener) {
        this.playListClickListener = playListClickListener;
    }

    public PlayListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(PlayListBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_playlist_item,
                parent, false);
        PlayListViewHolder viewHolder = new PlayListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, final int position) {

        VolleySingleton.getInstance().getImage(bean.getDiyInfo().get(position)
                .getList_pic(), holder.ivBackground);
        holder.tvCount.setText(String.valueOf(bean.getDiyInfo().get(position).getListen_num()));
        holder.tvIntroduction.setText(bean.getDiyInfo().get(position).getTitle());
        holder.tvAuthor.setText("by " + bean.getDiyInfo().get(position).getUsername());
        holder.rlPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listId = bean.getDiyInfo().get(position).getList_id();
                int songNum = bean.getDiyInfo().get(position).getSong_num();
                int listenNum = bean.getDiyInfo().get(position).getListen_num();
                String username = bean.getDiyInfo().get(position).getUsername();
                String title = bean.getDiyInfo().get(position).getTitle();
                playListClickListener.onPlayListClick(listId, songNum, listenNum, username, title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getDiyInfo() == null ? 0 : bean.getDiyInfo().size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlPlayList;
        private ImageView ivPlayList;
        private TextView tvCount;
        private ImageButton ibPlayList;
        private TextView tvIntroduction;
        private TextView tvAuthor;
        private ImageView ivBackground;

        public PlayListViewHolder(View itemView) {
            super(itemView);
            rlPlayList = (RelativeLayout) itemView.findViewById(R.id.rl_playlist);
            ivBackground = (ImageView) itemView.findViewById(R.id.iv_playlist_background);
            ivPlayList = (ImageView) itemView.findViewById(R.id.iv_playlist);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            ibPlayList = (ImageButton) itemView.findViewById(R.id.ib_playlist);
            tvIntroduction = (TextView) itemView.findViewById(R.id.tv_introduction);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
