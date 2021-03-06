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
public class ModAdapter extends RecyclerView.Adapter<ModAdapter.ModViewHolder> {
    private Context mContext;
    private RecommendationBean bean;
    private OnRvClickListener onRvClickListener;

    public void setOnRvClickListener(OnRvClickListener onRvClickListener) {
        this.onRvClickListener = onRvClickListener;
    }

    public ModAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }
    @Override
    public ModViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_recommendation_mod, parent, false);
        ModViewHolder viewHolder = new ModViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ModViewHolder holder, final int position) {
        VolleySingleton.getInstance().getImage(bean.getResult().getMod_7().getResult().get
                (position).getPic(), holder.ivMod);
        holder.tvTitle.setText(bean.getResult().getMod_7().getResult().get
                (position).getTitle());
        holder.tvDesc.setText(bean.getResult().getMod_7().getResult().get
                (position).getDesc());
        holder.llMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRvClickListener.onModClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getMod_7().getResult() == null ? 0 : bean.getResult().getMod_7()
                .getResult().size();
    }

    public class ModViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMod;
        private TextView tvTitle;
        private TextView tvDesc;
        private LinearLayout llMod;
        public ModViewHolder(View itemView) {
            super(itemView);
            llMod = (LinearLayout) itemView.findViewById(R.id.ll_recommendation_mod);
            ivMod = (ImageView) itemView.findViewById(R.id.iv_recommendation_mod);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_recommendation_mod_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_recommendation_mod_desc);
        }
    }
}
