package lanou.baidumusic.music.recommendation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.VolleySingleton;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/28.
 */
public class LastAdapter extends RecyclerView.Adapter<LastAdapter.LastViewHolder> {

    Context mContext;
    RecommendationBean bean;

    public LastAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public LastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_last, parent, false);
        LastViewHolder viewHolder = new LastViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LastViewHolder holder, int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getMix_1().getResult().get
                (position).getPic(), holder.ivLast);
        holder.tvTitle.setText(bean.getResult().getMix_1().getResult().get
                (position).getTitle());
        holder.tvAuthor.setText(bean.getResult().getMix_1().getResult().get
                (position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_1().getResult() == null ? 0 : 6;
    }

    public class LastViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivLast;
        private TextView tvTitle;
        private TextView tvAuthor;

        public LastViewHolder(View itemView) {
            super(itemView);

            ivLast = (ImageView) itemView.findViewById(R.id.iv_recommendation_last);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_last_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_recommendation_last_author);
        }
    }
}
