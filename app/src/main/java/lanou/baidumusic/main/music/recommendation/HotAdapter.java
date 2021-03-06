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
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {
    private Context mContext;
    private RecommendationBean bean;
    private OnRvClickListener onRvClickListener;

    public void setOnRvClickListener(OnRvClickListener onRvClickListener) {
        this.onRvClickListener = onRvClickListener;
    }

    public HotAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_hot, parent, false);
        HotViewHolder viewHolder = new HotViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, final int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getMix_22().getResult().get
                (position).getPic(), holder.ivHot);
        holder.tvTitle.setText(bean.getResult().getMix_22().getResult().get
                (position).getTitle());
        holder.tvAuthor.setText(bean.getResult().getMix_22().getResult().get
                (position).getAuthor());
        holder.llHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvClickListener.onHotClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMix_22().getResult() == null ? 0 : bean.getResult().getMix_22()
                .getResult().size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHot;
        private TextView tvTitle;
        private TextView tvAuthor;
        private LinearLayout llHot;
        public HotViewHolder(View itemView) {
            super(itemView);
            llHot = (LinearLayout) itemView.findViewById(R.id.ll_recommendation_hot);
            ivHot = (ImageView) itemView.findViewById(R.id.iv_recommendation_hot);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_hot_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_recommendation_hot_author);
        }
    }
}
