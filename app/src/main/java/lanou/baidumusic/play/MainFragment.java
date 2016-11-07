package lanou.baidumusic.play;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.volley.VolleySingleton;

/**
 * Created by dllo on 16/11/1.
 */
public class MainFragment extends BaseFragment {

    private ImageButton ibPlayPage;
    private TextView tvTitle;
    private TextView tvAuthor;

    @Override
    protected int getLayout() {
        return R.layout.fragment_play_main;
    }

    @Override
    protected void initView() {
        ibPlayPage = bindView(R.id.ib_playpage_big);
        tvTitle = bindView(R.id.tv_playpage_title);
        tvAuthor = bindView(R.id.tv_playpage_author);
    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        String imgBig = intent.getStringExtra("pic");
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");

        VolleySingleton.getInstance().getImage(imgBig, ibPlayPage);
        tvTitle.setText(title);
        tvAuthor.setText(author);
    }

}
