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
 * Created by dllo on 16/10/29.
 */
public class LeboAdapter extends RecyclerView.Adapter<LeboAdapter.LeboViewHolder> {
    Context mContext;
    RecommendationBean bean;

    public LeboAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
    }
    @Override
    public LeboViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_lebo, parent, false);
        LeboViewHolder viewHolder = new LeboViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LeboViewHolder holder, int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getRadio().getResult().get
                (position).getPic(), holder.ivHot);
        String str = bean.getResult().getRadio().getResult().get(position).getTitle();
        if (str.length() > 20) {
            str = str.substring(0, 15) + "...";
        }
        holder.tvTitle.setText(str);
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getRadio().getResult() == null ? 0 : bean.getResult().getRadio()
                .getResult().size();
    }

    public class LeboViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHot;
        private TextView tvTitle;
        public LeboViewHolder(View itemView) {
            super(itemView);
            ivHot = (ImageView) itemView.findViewById(R.id.iv_recommendation_lebo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_lebo_title);
        }
    }
}
