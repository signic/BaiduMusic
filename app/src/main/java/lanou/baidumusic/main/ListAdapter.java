package lanou.baidumusic.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/11/4.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListBean> beanArrayList;

    public ListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBeanArrayList(ArrayList<ListBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beanArrayList == null ? 0 : beanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_main_list,
                    parent, false);
            viewHolder = new ListViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(beanArrayList.get(position).getTitle());
        viewHolder.tvAuthor.setText(beanArrayList.get(position).getAuthor());
        return convertView;
    }

    private class ListViewHolder {
        private TextView tvTitle;
        private TextView tvAuthor;
        public ListViewHolder(View convertView) {
            tvTitle = (TextView) convertView.findViewById(R.id.tv_main_list_title);
            tvAuthor = (TextView) convertView.findViewById(R.id.tv_main_list_author);
        }
    }
}
