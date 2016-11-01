package lanou.baidumusic.main.live;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.LiveBean;

/**
 * Created by dllo on 16/10/27.
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.LiveViewHolder> {
    Context mContext;
    LiveBean bean;

    public LiveAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(LiveBean bean) {
        this.bean = bean;
    }

    @Override
    public LiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_live_item, parent, false);
        LiveViewHolder viewHolder = new LiveViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveViewHolder holder, int position) {
        Picasso.with(mContext).load(bean.getData().getData().get(position)
                .getLiveimg()).into(holder.ivBackground);
        holder.tvNum.setText(String.valueOf(bean.getData().getData().get(position).getUsercount()));
        holder.tvName.setText(bean.getData().getData().get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        return bean.getData().getData() == null ? 0 : bean.getData().getData().size();
    }

    public class LiveViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rlLive;
        private final ImageView ivBackground;
        private final ImageView ivLive;
        private final TextView tvNum;
        private final ImageButton ibLive;
        private final TextView tvName;

        public LiveViewHolder(View itemView) {
            super(itemView);
            rlLive = (RelativeLayout) itemView.findViewById(R.id.rl_live);
            ivBackground = (ImageView) itemView.findViewById(R.id.iv_live_background);
            ivLive = (ImageView) itemView.findViewById(R.id.iv_live);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            ibLive = (ImageButton) itemView.findViewById(R.id.ib_live);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
