package lanou.baidumusic.tool.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import lanou.baidumusic.R;

/**
 * Created by dllo on 16/9/27.
 */
public class CircleImageView extends ImageView {

    private boolean isCircle;

    // 在代码里初始化组件的时候执行这个构造方法
    public CircleImageView(Context context) {
        super(context);
    }
    // xml里布局这个组件的时候执行这个构造方法
    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取xml布局里设置的自定义组件的属性值
        // 找到attrs.xml中自定义组件设置的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        isCircle = array.getBoolean(R.styleable.CircleImageView_isCircle, false);
        Log.d("CircleImageView", "isCircle:" + isCircle);

    }
    // 自定义组件的style的时候会执行这个构造方法
    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isCircle) {
            // 这里绘制一个圆形的图片
            // 获取到imageview的src资源文件
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            if (drawable != null) {
                // 将src设置的图片转换成Bitmap类型
                Bitmap bitmap = drawable.getBitmap();

                Bitmap circleBitmap = getCircleBitmap(bitmap);

                Paint paint = new Paint();
                paint.setAntiAlias(true);

                Rect rect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
                canvas.drawBitmap(circleBitmap, rect, rect, paint);
            }

        } else {
            // 这个父类方法里有一些实现方法, 可以帮助组件显示
            super.onDraw(canvas);
        }

    }

    private Bitmap getSmallBitmap(Bitmap bitmap){
        int width = getWidth();
        int height = getHeight();
        if(width <= 0 || height <= 0){
            return bitmap;
        }
        bitmap = Bitmap.createScaledBitmap(bitmap,width,height,false);
        return bitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        bitmap = getSmallBitmap(bitmap);
        // 初始化一个空的跟src图片一样大小的Bitmap
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 创建一个空的装载bitmap的画布
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        // 设置画笔的模式,两者相交去前景模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return outBitmap;
    }
}
