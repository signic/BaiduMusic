package lanou.baidumusic.main.music.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.bean.ListBean;
import lanou.baidumusic.tool.bean.PlayListItemBean;
import lanou.baidumusic.tool.bean.PlayListSongInfoBean;
import lanou.baidumusic.tool.database.DBTools;
import lanou.baidumusic.tool.volley.GsonRequest;
import lanou.baidumusic.tool.volley.Values;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/3.
 */
public class PlayListItemAdapter extends RecyclerView.Adapter<PlayListItemAdapter.ListItemViewHolder> {
    private Context mContext;
    private PlayListItemBean bean;
    private PopupWindow popupWindow;
    private OnPlaylistItemClickListener onPlaylistItemClickListener;
    private DBTools dbTools;

    public void setOnPlaylistItemClickListener(OnPlaylistItemClickListener onPlaylistItemClickListener) {
        this.onPlaylistItemClickListener = onPlaylistItemClickListener;
    }

    public PlayListItemAdapter(Context mContext) {
        this.mContext = mContext;
        dbTools = new DBTools(mContext);
    }

    public void setBean(PlayListItemBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout
                .fragment_music_playlist_item_playlist_item, parent, false);
        ListItemViewHolder viewHolder = new ListItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, final int position) {
        holder.tvTitle.setText(bean.getContent().get(position).getTitle());
        holder.tvAuthor.setText(bean.getContent().get(position).getAuthor());
        if (bean.getContent().get(position).getHas_mv_mobile() == 1) {
            holder.ivMv.setImageResource(R.mipmap.ic_mv);
        }
        if (bean.getContent().get(position).getIs_ksong().equals("1")) {
            holder.ivK.setImageResource(R.mipmap.ic_mike_normal);
        }
        holder.ivSq.setImageResource(R.mipmap.ic_sq);
        holder.ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null || !popupWindow.isShowing()) {
                    popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    View view = LayoutInflater.from(mContext).inflate(R.layout.pop_item, null);
                    popupWindow.setContentView(view);
                } else {
                    popupWindow.dismiss();
                }
            }
        });

        holder.llList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaylistItemClickListener.onItemClickListener(position);
                dbTools.deleteAllSong();
                for (int i = 0; i < bean.getContent().size(); i++) {
                    String songId = bean.getContent().get(i).getSong_id();
                    Log.d("PlayListItemAdapter1", songId);
                    GsonInfo(songId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getContent() == null ? 0 : bean.getContent().size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAuthor;
        private ImageView ivMv;
        private ImageView ivSq;
        private ImageView ivK;
        private ImageView ibMore;
        private LinearLayout llList;
        public ListItemViewHolder(View itemView) {
            super(itemView);
            llList = (LinearLayout) itemView.findViewById(R.id.ll_playlist_list);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_playlist_list_title);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_playlist_list_author);
            ivMv = (ImageView) itemView.findViewById(R.id.iv_playlist_list_mv);
            ivSq = (ImageView) itemView.findViewById(R.id.iv_playlist_list_sq);
            ivK = (ImageView) itemView.findViewById(R.id.iv_playlist_list_k);
            ibMore = (ImageView) itemView.findViewById(R.id.ib_playlist_list_more);
        }
    }

    public void GsonInfo(String songId) {
        GsonRequest<PlayListSongInfoBean> gsonRequest = new GsonRequest<>(PlayListSongInfoBean.class,
                Values.SONG_INFO + songId, new Response.Listener<PlayListSongInfoBean>() {

            @Override
            public void onResponse(PlayListSongInfoBean response) {
                // 请求成功的方法
                String title = response.getSonginfo().getTitle();
                String albumTitle = response.getSonginfo().getAlbum_title();
                String author = response.getSonginfo().getAuthor();
                String pic = response.getSonginfo().getPic_premium();
                String fileLink = response.getBitrate().getFile_link();
                int duration = response.getBitrate().getFile_duration();
                ListBean listBean = new ListBean();
                listBean.setTitle(title);
                listBean.setAlbumTitle(albumTitle);
                listBean.setAuthor(author);
                listBean.setPic(pic);
                listBean.setFileLink(fileLink);
                listBean.setDuration(duration);
                dbTools.insertSongTable(listBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

}
