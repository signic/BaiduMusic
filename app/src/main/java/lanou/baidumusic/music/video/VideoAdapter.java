package lanou.baidumusic.music.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/10/25.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    ArrayList<VideoBean> beanArrayList;
    Context mContext;

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBeanArrayList(ArrayList<VideoBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_video_item, null);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.iv_video.setImageResource(R.mipmap.ic_launcher);
        holder.tv_title.setText("咆哮");
        holder.tv_author.setText("EXO");
    }

    @Override
    public int getItemCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_video;
        private final TextView tv_title;
        private final TextView tv_author;
        public VideoViewHolder(View itemView) {
            super(itemView);
            iv_video = (ImageView) itemView.findViewById(R.id.iv_video);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
