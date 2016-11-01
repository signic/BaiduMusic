package lanou.baidumusic.main.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.VolleySingleton;
import lanou.baidumusic.tool.bean.DynamicBean;

/**
 * Created by dllo on 16/10/31.
 */
public class DynamicItemAdapter extends RecyclerView.Adapter<DynamicItemAdapter
        .DynamicItemViewHolder> {
    Context mContext;
    DynamicBean bean;

    public DynamicItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(DynamicBean bean) {
        this.bean = bean;
    }

    @Override
    public DynamicItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_item_item,
                parent, false);
        DynamicItemViewHolder viewHolder = new DynamicItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DynamicItemViewHolder holder, int position) {
        if (bean.getMsg().get(position).getPiclist() != null) {
            VolleySingleton.getInstance().getImage(bean.getMsg().get(position).getPiclist()
                    .get(position).getPic_small(), holder.ivPic);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class DynamicItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPic;

        public DynamicItemViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_dynamic_piclist);
        }
    }
}
