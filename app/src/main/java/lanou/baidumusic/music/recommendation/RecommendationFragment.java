package lanou.baidumusic.music.recommendation;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.GsonRequest;
import lanou.baidumusic.tool.Values;
import lanou.baidumusic.tool.VolleySingleton;
import lanou.baidumusic.tool.base.BaseFragment;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/24.
 */
public class RecommendationFragment extends BaseFragment {

    private Handler handler;
    private ViewPager bannerViewPager;
    private BannerAdapter bannerAdapter;
    private ImageView singer;
    private ImageView songs;
    private ImageView radio;
    private ImageView vip;
    private ImageView ivRecommendation;
    private ImageView ivLast;
    private ImageView ivHot;
    private ImageView ivScene;
    private ImageView ivToday;
    private ImageView ivDiy;
    private ImageView ivMv;
    private ImageView ivLebo;
    private ImageView ivMod;
    private RecyclerView rvRecommendation;
    private LastAdapter lastAdapter;
    private RecyclerView rvLast;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_recommendation;
    }

    @Override
    protected void initView() {
        bannerViewPager = bindView(R.id.vp_music_recommendation);
        bannerAdapter = new BannerAdapter();
        rvRecommendation = bindView(R.id.rv_recommendation);
//        RecommendationAdapter recommendationAdapter = new RecommendationAdapter();
        rvLast = bindView(R.id.rv_last);
        lastAdapter = new LastAdapter(getActivity());

        singer = bindView(R.id.iv_music_recommendation_singer);
        songs = bindView(R.id.iv_music_recommendation_songs);
        radio = bindView(R.id.iv_music_recommendation_radio);
        vip = bindView(R.id.iv_music_recommendation_vip);

        ivRecommendation = bindView(R.id.iv_module_recommendation);
        ivLast = bindView(R.id.iv_module_last);
        ivHot = bindView(R.id.iv_module_hot);
        ivScene = bindView(R.id.iv_module_scene);
        ivToday = bindView(R.id.iv_module_today);
        ivDiy = bindView(R.id.iv_module_diy);
        ivMv = bindView(R.id.iv_module_mv);
        ivLebo = bindView(R.id.iv_module_lebo);
        ivMod = bindView(R.id.iv_module_mod);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GsonData(Values.MUSIC_RECOMMENDATION);
            }
        }).start();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvLast.setLayoutManager(gridLayoutManager);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (bannerViewPager != null && msg.what == 1) {
                    bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem() + 1);
                }
                handler.sendEmptyMessageDelayed(1, 5000);
            }
        };

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }

    @Override
    public void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(1, 5000);
    }

    private void GsonData(String url) {
        GsonRequest<RecommendationBean> gsonRequest = new GsonRequest<>(RecommendationBean
                .class, url,
                new Response.Listener<RecommendationBean>() {
                    @Override
                    public void onResponse(RecommendationBean response) {
                        // 请求成功的方法

                        bannerAdapter.setBean(response);
                        bannerViewPager.setAdapter(bannerAdapter);

                        lastAdapter.setBean(response);
                        rvLast.setAdapter(lastAdapter);

                        VolleySingleton.getInstance().getImage(response.getResult().getEntry()
                                .getResult().get(0).getIcon(), singer);
                        VolleySingleton.getInstance().getImage(response.getResult().getEntry()
                                .getResult().get(1).getIcon(), songs);
                        VolleySingleton.getInstance().getImage(response.getResult().getEntry()
                                .getResult().get(2).getIcon(), radio);
                        VolleySingleton.getInstance().getImage(response.getResult().getEntry()
                                .getResult().get(3).getIcon(), vip);

                        VolleySingleton.getInstance().getImage(response.getModule().get(3)
                                .getPicurl(), ivRecommendation);
                        VolleySingleton.getInstance().getImage(response.getModule().get(5)
                                .getPicurl(), ivLast);
                        VolleySingleton.getInstance().getImage(response.getModule().get(6)
                                .getPicurl(), ivHot);
                        VolleySingleton.getInstance().getImage(response.getModule().get(8)
                                .getPicurl(), ivScene);
                        VolleySingleton.getInstance().getImage(response.getModule().get(9)
                                .getPicurl(), ivToday);
                        VolleySingleton.getInstance().getImage(response.getModule().get(10)
                                .getPicurl(), ivDiy);
                        VolleySingleton.getInstance().getImage(response.getModule().get(11)
                                .getPicurl(), ivMv);
                        VolleySingleton.getInstance().getImage(response.getModule().get(12)
                                .getPicurl(), ivLebo);
                        VolleySingleton.getInstance().getImage(response.getModule().get(13)
                                .getPicurl(), ivMod);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
