package lanou.baidumusic.play;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/1.
 */
public class InfoFragment extends BaseFragment {

    private ImageButton ibSmall;
    private TextView tvTitle;
    private TextView tvAuthor;

    @Override
    protected int getLayout() {
        return R.layout.fragment_play_info;
    }

    @Override
    protected void initView() {
        ibSmall = bindView(R.id.ib_playpage_small);
        tvTitle = bindView(R.id.tv_playpage_info_title);
        tvAuthor = bindView(R.id.tv_playpage_info_author);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String pic = arguments.getString("pic");
        String albumTitle = arguments.getString("albumTitle");
        String author = arguments.getString("author");
        VolleySingleton.getInstance().getImage(pic, ibSmall);
        tvTitle.setText(albumTitle);
        tvAuthor.setText(author);
    }

}
