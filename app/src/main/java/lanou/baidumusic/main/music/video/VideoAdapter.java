package lanou.baidumusic.main.music.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.VideoBean;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/25.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private VideoBean bean;
    private Context mContext;

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(VideoBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_video_item,
                parent, false);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getMv_list().get(position)
                .getThumbnail2(), holder.ivVideo);
        holder.tvTitle.setText(bean.getResult().getMv_list().get(position).getTitle());
        holder.tvAuthor.setText(bean.getResult().getMv_list().get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMv_list() == null ? 0 : bean.getResult().getMv_list().size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivVideo;
        private TextView tvTitle;
        private TextView tvAuthor;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ivVideo = (ImageView) itemView.findViewById(R.id.iv_video);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
        }
    }
}
