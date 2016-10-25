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

import java.util.ArrayList;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/10/24.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {
    ArrayList<PlayListBean> beanArrayList;
    Context mContext;

    public PlayListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBeanArrayList(ArrayList<PlayListBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_playlist_item, null);
        PlayListViewHolder viewHolder = new PlayListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        holder.rl_playList.setBackgroundResource(R.mipmap.ic_launcher);
        holder.iv_playList.setImageResource(R.mipmap.ic_launcher);
        holder.tv_count.setText("4219");
        holder.ib_playList.setImageResource(R.mipmap.bt_localmusic_play_nor);
        holder.tv_introduction.setText("沉淀记忆岁月的歌");
        holder.tv_author.setText("by super悟空");
    }

    @Override
    public int getItemCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rl_playList;
        private final ImageView iv_playList;
        private final TextView tv_count;
        private final ImageButton ib_playList;
        private final TextView tv_introduction;
        private final TextView tv_author;

        public PlayListViewHolder(View itemView) {
            super(itemView);
            rl_playList = (RelativeLayout) itemView.findViewById(R.id.rl_playList);
            iv_playList = (ImageView) itemView.findViewById(R.id.iv_playList);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            ib_playList = (ImageButton) itemView.findViewById(R.id.ib_playList);
            tv_introduction = (TextView) itemView.findViewById(R.id.tv_introduction);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
