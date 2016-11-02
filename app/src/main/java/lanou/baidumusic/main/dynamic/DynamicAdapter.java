package lanou.baidumusic.main.dynamic;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.bean.DynamicBean;

/**
 * Created by dllo on 16/10/31.
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicViewHolder> {
    Context mContext;
    DynamicBean bean;

    public DynamicAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(DynamicBean bean) {
        this.bean = bean;
    }

    @Override
    public DynamicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_item, parent,
                false);
        DynamicViewHolder viewHolder = new DynamicViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DynamicViewHolder holder, int position) {

        if (bean.getMsg().get(position).getMsgtype() == 1) {
            VolleySingleton.getInstance().getImage(bean.getMsg().get(position).getAuthor()
                    .getUserpic_small(), holder.ivUserPic);
            holder.tvUsername.setText(bean.getMsg().get(position).getAuthor().getUsername());
            holder.tvCTime.setText(String.valueOf(bean.getMsg().get(position).getCtime()));
            holder.tvMsg.setText(bean.getMsg().get(position).getMsg());

            if (bean.getMsg().get(position).getTopic() != null) {
                holder.tvTopicTitle.setText("#  " + bean.getMsg().get(position).getTopic().getTopic_title());
            }


//        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_item_item,
//                null);
//        ImageView ivPicList = (ImageView) view.findViewById(R.id.iv_dynamic_piclist);
//        VolleySingleton.getInstance().getImage(bean.getMsg().get(position).getPiclist(),
//                holder.ivPicList);

//            DynamicItemAdapter itemAdapter = new DynamicItemAdapter(mContext);
//            holder.rvDynamicItem.setAdapter(itemAdapter);
//            GridLayoutManager manager = new GridLayoutManager(mContext, 3);
//            holder.rvDynamicItem.setLayoutManager(manager);

            VolleySingleton.getInstance().getImage(bean.getMsg().get(position).getContent().getPic(),
                    holder.ivContentPic);
            switch (Integer.valueOf(bean.getMsg().get(position).getContent().getContent_type())) {
                case 0:
                    holder.tvContentType.setText("");
                    break;
                case 1:
                    holder.tvContentType.setText("歌单: ");
                    holder.tvContentType.setTextColor(Color.BLUE);
                    break;
                case 2:
                    holder.tvContentType.setText("专辑: ");
                    holder.tvContentType.setTextColor(Color.BLUE);
                    break;
            }
            holder.tvContentTitle.setText(bean.getMsg().get(position).getContent().getTitle());
            holder.tvZan.setText(String.valueOf(bean.getMsg().get(position).getZan_num()));
            holder.tvShare.setText(String.valueOf(bean.getMsg().get(position).getShare_num()));
        }

    }

    @Override
    public int getItemCount() {
        return bean.getMsg() == null ? 0 : bean.getMsg().size();
    }

    public class DynamicViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvShare;
        private final TextView tvZan;
        private final TextView tvContentTitle;
        private final TextView tvContentType;
        private final ImageView ivContentPic;
        private final RecyclerView rvDynamicItem;
        private final TextView tvTopicTitle;
        private final TextView tvMsg;
        private final TextView tvCTime;
        private final TextView tvUsername;
        private final ImageView ivUserPic;

        public DynamicViewHolder(View itemView) {
            super(itemView);

            ivUserPic = (ImageView) itemView.findViewById(R.id.iv_dynamic_userpic);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_dynamic_username);
            tvCTime = (TextView) itemView.findViewById(R.id.tv_dynamic_ctime);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_dynamic_msg);
            tvTopicTitle = (TextView) itemView.findViewById(R.id.tv_dynamic_topic_title);
            rvDynamicItem = (RecyclerView) itemView.findViewById(R.id.rv_dynamic_item);
            ivContentPic = (ImageView) itemView.findViewById(R.id.iv_dynamic_content_pic);
            tvContentType = (TextView) itemView.findViewById(R.id.tv_dynamic_content_type);
            tvContentTitle = (TextView) itemView.findViewById(R.id.tv_dynamic_content_title);
            tvZan = (TextView) itemView.findViewById(R.id.tv_dynamic_zan_num);
            tvShare = (TextView) itemView.findViewById(R.id.tv_dynamic_share_num);
        }
    }
}
