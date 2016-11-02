package lanou.baidumusic.main.music.recommendation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/29.
 */
public class DiyAdapter extends RecyclerView.Adapter<DiyAdapter.DiyViewHolder> {
    Context mContext;
    RecommendationBean bean;
    OnRvClickListener onRvClickListener;

    public void setOnRvClickListener(OnRvClickListener onRvClickListener) {
        this.onRvClickListener = onRvClickListener;
    }

    public DiyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
    }
    @Override
    public DiyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_diy, parent, false);
        DiyViewHolder viewHolder = new DiyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiyViewHolder holder, final int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getMix_9().getResult().get
                (position).getPic(), holder.ivDiy);
        holder.tvTitle.setText(bean.getResult().getMix_9().getResult().get
                (position).getTitle());
        holder.llDiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvClickListener.onDiyClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_9().getResult() == null ? 0 : bean.getResult()
                .getMix_9().getResult().size();
    }

    public class DiyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDiy;
        private TextView tvTitle;
        private LinearLayout llDiy;
        public DiyViewHolder(View itemView) {
            super(itemView);
            llDiy = (LinearLayout) itemView.findViewById(R.id.ll_recommendation_diy);
            ivDiy = (ImageView) itemView.findViewById(R.id.iv_recommendation_diy);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_diy_title);
        }
    }
}
