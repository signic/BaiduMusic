package lanou.baidumusic.main.music.recommendation;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lanou.baidumusic.tool.volley.VolleySingleton;
import lanou.baidumusic.tool.bean.RecommendationBean;

/**
 * Created by dllo on 16/10/28.
 */
public class BannerAdapter extends PagerAdapter {
    RecommendationBean bean = new RecommendationBean();

    public void setBean(RecommendationBean bean) {
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.getResult().getFocus().getResult() == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        VolleySingleton.getInstance().getImage(bean.getResult().getFocus().getResult().get
               (position % (bean.getResult().getFocus().getResult().size())).getRandpic(),
               imageView);
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container.getChildAt(position) == object) {
            container.removeViewAt(position);
        }
    }
}
