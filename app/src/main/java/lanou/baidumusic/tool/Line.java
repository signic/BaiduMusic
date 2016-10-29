package lanou.baidumusic.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dllo on 16/10/28.
 * 点
 */
public class Line extends View {
    private int r = 10;
    private boolean isSelected = false;
    public Line(Context context) {
        super(context);
    }

    public Line(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 改变选中状态
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();// 重新绘制
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 开启抗锯齿
        if (isSelected) {
            paint.setColor(0xFF000000);
        } else {
            paint.setColor(0xFF6AABDB);
        }

        // 画圆
//        canvas.drawLine(0, 0, 10, 0, paint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, paint);
    }
}
