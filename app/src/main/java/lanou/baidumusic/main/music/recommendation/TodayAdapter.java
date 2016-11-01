package lanou.baidumusic.main.music.recommendation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/29.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolder> {
    Context mContext;
    RecommendationBean bean;

    public TodayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
    }
    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_today, parent, false);
        TodayViewHolder viewHolder = new TodayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodayViewHolder holder, int position) {
        Picasso.with(mContext).load(bean.getResult().getRecsong().getResult().get
                (position).getPic_premium()).fit().into(holder.ivToday);
        holder.tvTitle.setText(bean.getResult().getRecsong().getResult().get
                (position).getTitle());
        holder.tvAuthor.setText(bean.getResult().getRecsong().getResult().get
                (position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getRecsong().getResult() == null ? 0 : 3;
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivToday;
        private TextView tvTitle;
        private TextView tvAuthor;
        public TodayViewHolder(View itemView) {
            super(itemView);
            ivToday = (ImageView) itemView.findViewById(R.id.iv_recommendation_today);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_today_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_recommendation_today_author);
        }
    }
}
