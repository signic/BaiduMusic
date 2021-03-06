package lanou.baidumusic.tool.volley;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import lanou.baidumusic.R;
import lanou.baidumusic.tool.base.MyApp;

/**
 * Created by dllo on 16/10/24.
 */
public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;// 用来请求图片的

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApp.getContext());
        // 初始化ImageLoader
        mImageLoader = new ImageLoader(mRequestQueue, new MemoryCache());
    }

    public static VolleySingleton getInstance() {
        if (volleySingleton == null) {
            synchronized (VolleySingleton.class) {
                if (volleySingleton == null) {
                    volleySingleton = new VolleySingleton();
                }
            }
        }
        return volleySingleton;
    }

    // 请求图片
    public void getImage(String url, ImageView imageView) {
        mImageLoader.get(url, ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher
                , R.mipmap.ic_launcher));
    }

    // 获得RequestQueue
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        mRequestQueue.add(request);
    }

    public void getImage(String url,GetBitmap getBitmap) {
        mImageLoader.get(url,new BitmapLoader(getBitmap));
    }

    public class BitmapLoader implements ImageLoader.ImageListener {
        private GetBitmap getBitmap;

        public BitmapLoader(GetBitmap getBitmap) {
            this.getBitmap = getBitmap;
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            Bitmap bitmap = response.getBitmap();
            if(bitmap != null) {
                getBitmap.onGetBitmap(bitmap);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            getBitmap.onGetBitmap(null);
        }
    }

    public interface GetBitmap {
        void onGetBitmap(Bitmap bitmap);
    }

}
