package lanou.baidumusic.music.recommendation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/28.
 */
public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter
        .RecommendationViewHolder> {

    Context mContext;
    RecommendationBean bean = new RecommendationBean();

    public RecommendationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_recommendation, parent, false);
        RecommendationViewHolder viewHolder = new RecommendationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecommendationViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRecommendation;
        private ImageView ivRecommendation;

        public RecommendationViewHolder(View itemView) {
            super(itemView);

            tvRecommendation = (TextView) itemView.findViewById(R.id.tv_recommendation_recommendation);
            ivRecommendation = (ImageView) itemView.findViewById(R.id.iv_recommendation_recommendation);
        }
    }
}
