package lanou.baidumusic.main.music.recommendation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/28.
 */
public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder> {
    private Context mContext;
    private RecommendationBean bean;

    public RecommendationAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
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
        VolleySingleton.getInstance().getImage(bean.getResult().getDiy().getResult().get
                (position).getPic(), holder.ivRecommendation);
        holder.tvNum.setText(String.valueOf(bean.getResult().getDiy().getResult().get
                (position).getListenum()));
        holder.tvTitle.setText(bean.getResult().getDiy().getResult().get
                (position).getTitle());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        try {
            count = bean.getResult().getDiy()
                    .getResult().size();
        } catch (NullPointerException e){

        }
        return count;
    }

    public class RecommendationViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNum;
        private TextView tvTitle;
        private ImageView ivRecommendation;
        public RecommendationViewHolder(View itemView) {
            super(itemView);
            tvNum = (TextView) itemView.findViewById(R.id.tv_recommendation_recommendation_num);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_recommendation_title);
            ivRecommendation = (ImageView) itemView.findViewById(R.id.iv_recommendation_recommendation);
        }
    }
}
