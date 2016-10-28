package lanou.baidumusic.music.recommendation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.VolleySingleton;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/28.
 */
public class EntryAdapter extends BaseAdapter {
    Context mContext;
    RecommendationBean bean = new RecommendationBean();

    public EntryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bean.getResult().getEntry().getResult().size();
    }

    @Override
    public Object getItem(int position) {
        return bean.getResult().getEntry().getResult().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EntryViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new EntryViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EntryViewHolder) convertView.getTag();
        }

        VolleySingleton.getInstance().getImage(bean.getResult().getEntry().getResult().get
                (0).getIcon(), viewHolder.singer);
        VolleySingleton.getInstance().getImage(bean.getResult().getEntry().getResult().get
                (1).getIcon(), viewHolder.songs);
        VolleySingleton.getInstance().getImage(bean.getResult().getEntry().getResult().get
                (2).getIcon(), viewHolder.radio);
        VolleySingleton.getInstance().getImage(bean.getResult().getEntry().getResult().get
                (3).getIcon(), viewHolder.vip);

        return convertView;
    }

    private class EntryViewHolder {

        private ImageView singer;
        private ImageView songs;
        private ImageView radio;
        private ImageView vip;

        public EntryViewHolder(View convertView) {
            singer = (ImageView) convertView.findViewById(R.id.iv_music_recommendation_singer);
            songs = (ImageView) convertView.findViewById(R.id.iv_music_recommendation_songs);
            radio = (ImageView) convertView.findViewById(R.id.iv_music_recommendation_radio);
            vip = (ImageView) convertView.findViewById(R.id.iv_music_recommendation_vip);
        }
    }
}
