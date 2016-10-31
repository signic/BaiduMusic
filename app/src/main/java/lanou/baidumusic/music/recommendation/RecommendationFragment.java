package lanou.baidumusic.music.recommendation;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ImageView ivAd;
    private ImageView ivScene1;
    private ImageView ivScene2;
    private ImageView ivScene3;
    private ImageView ivScene4;
    private TextView tvScene1;
    private TextView tvScene2;
    private TextView tvScene3;
    private TextView tvScene4;
    private RecyclerView rvRecommendation;
    private RecommendationAdapter recommendationAdapter;
    private LastAdapter lastAdapter;
    private RecyclerView rvLast;
    private HotAdapter hotAdapter;
    private RecyclerView rvHot;
    private RecyclerView rvToday;
    private TodayAdapter todayAdapter;
    private RecyclerView rvDiy;
    private DiyAdapter diyAdapter;
    private RecyclerView rvMv;
    private MvAdapter mvAdapter;
    private RecyclerView rvLebo;
    private LeboAdapter leboAdapter;
    private RecyclerView rvMod;
    private ModAdapter modAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_music_recommendation;
    }

    @Override
    protected void initView() {
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
        ivAd = bindView(R.id.iv_ad);
        ivScene1 = bindView(R.id.iv_recommendation_scene1);
        ivScene2 = bindView(R.id.iv_recommendation_scene2);
        ivScene3 = bindView(R.id.iv_recommendation_scene3);
        ivScene4 = bindView(R.id.iv_recommendation_scene4);
        tvScene1 = bindView(R.id.tv_recommendation_scene1_name);
        tvScene2 = bindView(R.id.tv_recommendation_scene2_name);
        tvScene3 = bindView(R.id.tv_recommendation_scene3_name);
        tvScene4 = bindView(R.id.tv_recommendation_scene4_name);


        bannerViewPager = bindView(R.id.vp_music_recommendation);
        bannerAdapter = new BannerAdapter();

        rvRecommendation = bindView(R.id.rv_recommendation);
        recommendationAdapter = new RecommendationAdapter(getActivity());

        rvLast = bindView(R.id.rv_last);
        lastAdapter = new LastAdapter(getActivity());

        rvHot = bindView(R.id.rv_hot);
        hotAdapter = new HotAdapter(getActivity());

        rvToday = bindView(R.id.rv_today);
        todayAdapter = new TodayAdapter(getActivity());

        rvDiy = bindView(R.id.rv_diy);
        diyAdapter = new DiyAdapter(getActivity());

        rvMv = bindView(R.id.rv_mv);
        mvAdapter = new MvAdapter(getActivity());

        rvLebo = bindView(R.id.rv_lebo);
        leboAdapter = new LeboAdapter(getActivity());

        rvMod = bindView(R.id.rv_mod);
        modAdapter = new ModAdapter(getActivity());

        GsonData(Values.MUSIC_RECOMMENDATION);

        GridLayoutManager recommendationManager = new GridLayoutManager(getActivity(), 3);
        rvRecommendation.setLayoutManager(recommendationManager);
        GridLayoutManager lastManager = new GridLayoutManager(getActivity(), 3);
        rvLast.setLayoutManager(lastManager);
        GridLayoutManager hotManager = new GridLayoutManager(getActivity(), 3);
        rvHot.setLayoutManager(hotManager);
        LinearLayoutManager todayManager = new LinearLayoutManager(getActivity());
        todayManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvToday.setLayoutManager(todayManager);
        GridLayoutManager diyManager = new GridLayoutManager(getActivity(), 3);
        rvDiy.setLayoutManager(diyManager);
        GridLayoutManager mvManager = new GridLayoutManager(getActivity(), 3);
        rvMv.setLayoutManager(mvManager);
        GridLayoutManager leboManager = new GridLayoutManager(getActivity(), 3);
        rvLebo.setLayoutManager(leboManager);
        LinearLayoutManager modManager = new LinearLayoutManager(getActivity());
        modManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMod.setLayoutManager(modManager);

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
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(RecommendationBean response) {
                        // 请求成功的方法
                        bannerAdapter.setBean(response);
                        bannerViewPager.setAdapter(bannerAdapter);

                        recommendationAdapter.setBean(response);
                        rvRecommendation.setAdapter(recommendationAdapter);

                        lastAdapter.setBean(response);
                        rvLast.setAdapter(lastAdapter);

                        hotAdapter.setBean(response);
                        rvHot.setAdapter(hotAdapter);

                        todayAdapter.setBean(response);
                        rvToday.setAdapter(todayAdapter);

                        diyAdapter.setBean(response);
                        rvDiy.setAdapter(diyAdapter);

                        mvAdapter.setBean(response);
                        rvMv.setAdapter(mvAdapter);

                        leboAdapter.setBean(response);
                        rvLebo.setAdapter(leboAdapter);

                        modAdapter.setBean(response);
                        rvMod.setAdapter(modAdapter);

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
                        VolleySingleton.getInstance().getImage(response.getResult().getMod_26()
                                .getResult().get(0).getPic(), ivAd);

//                        ivScene1.setImageTintList(getActivity().getResources()
//                                .getColorStateList(R.color.colorOrange));
//                        ivScene2.setImageTintList(getActivity().getResources().getColorStateList
//                                (R.color.colorGreen));
//                        ivScene3.setImageTintList(getActivity().getResources().getColorStateList
//                                (R.color.colorCyan));
//                        ivScene4.setImageTintList(getActivity().getResources().getColorStateList
//                                (R.color.colorBlue));
                        VolleySingleton.getInstance().getImage(response.getResult().getScene()
                                .getResult().getAction().get(0).getIcon_android(), ivScene1);
                        VolleySingleton.getInstance().getImage(response.getResult().getScene()
                                .getResult().getAction().get(1).getIcon_android(), ivScene2);
                        VolleySingleton.getInstance().getImage(response.getResult().getScene()
                                .getResult().getAction().get(2).getIcon_android(), ivScene3);
                        VolleySingleton.getInstance().getImage(response.getResult().getScene()
                                .getResult().getAction().get(3).getIcon_android(), ivScene4);
                        tvScene1.setText((response.getResult().getScene().getResult().getAction()
                                .get(0).getScene_name()));
                        tvScene2.setText((response.getResult().getScene().getResult().getAction()
                                .get(1).getScene_name()));
                        tvScene3.setText((response.getResult().getScene().getResult().getAction()
                                .get(2).getScene_name()));
                        tvScene4.setText((response.getResult().getScene().getResult().getAction()
                                .get(3).getScene_name()));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }
}
