package lanou.baidumusic.main.music.toplist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.TopListBean;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/10/25.
 */
public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.TopListViewHolder> {

    private Context mContext;
    private TopListBean bean;
    private OnTopListClickListener onTopListClickListener;

    public TopListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(TopListBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void setOnTopListClickListener(OnTopListClickListener onTopListClickListener) {
        this.onTopListClickListener = onTopListClickListener;
    }

    @Override
    public TopListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_music_toplist_item,
                parent, false);
        TopListViewHolder viewHolder = new TopListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TopListViewHolder holder, final int position) {
        VolleySingleton.getInstance().getImage(bean.getContent().get(position)
                .getPic_s192(), holder.ivTopList);
        holder.tvTitle.setText(bean.getContent().get(position).getName());
        holder.tvTop1.setText(bean.getContent().get(position)
                .getContent().get(0).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(0).getAuthor());
        holder.tvTop2.setText(bean.getContent().get(position)
                .getContent().get(1).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(1).getAuthor());
        holder.tvTop3.setText(bean.getContent().get(position)
                .getContent().get(2).getTitle() + "-" + bean.getContent()
                .get(position).getContent().get(2).getAuthor());
        holder.llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = String.valueOf(bean.getContent().get(position).getType());
                onTopListClickListener.onTopClick(type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getContent() == null ? 0 : bean.getContent().size();
    }

    public class TopListViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivTopList;
        private TextView tvTitle;
        private TextView tvTop1;
        private TextView tvTop2;
        private TextView tvTop3;
        private LinearLayout llTop;
        public TopListViewHolder(View itemView) {
            super(itemView);
            llTop = (LinearLayout) itemView.findViewById(R.id.ll_toplist);
            ivTopList = (ImageView) itemView.findViewById(R.id.iv_toplist);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTop1 = (TextView) itemView.findViewById(R.id.tv_top1);
            tvTop2 = (TextView) itemView.findViewById(R.id.tv_top2);
            tvTop3 = (TextView) itemView.findViewById(R.id.tv_top3);
        }
    }
}
