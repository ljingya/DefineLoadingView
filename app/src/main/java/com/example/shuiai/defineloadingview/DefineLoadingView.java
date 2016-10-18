package com.example.shuiai.defineloadingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author shuiai@dianjia.io
 * @Company 杭州木瓜科技有限公司
 * @date 2016/10/18
 */

public class DefineLoadingView extends View {
    /**
     * 图像混合模式
     */
    private PorterDuffXfermode porterDuffXfermode;
    /**
     * 创建一个Bitmap
     */
    private Bitmap mBitmap;
    /**
     * 画布
     */
    private Canvas mCanvas;
    /**
     * View的宽
     */
    private int width;
    /**
     * View的高
     */
    private int height;
    /**
     * 画笔
     */
    private Paint wavePaint, circlePaint;
    /**
     * 路径对象
     */
    private Path mPath;
    private float x;
    private float y = 1f;
    private int min;

    public DefineLoadingView(Context context) {
        this(context, null);
    }

    public DefineLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefineLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRecrouse();
    }

    /**
     * 初始化资源
     */
    private void initRecrouse() {
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(Color.GRAY);
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#77aa0000"));
//        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        //设置图像混合模式
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            throw new IllegalArgumentException("请把宽写确定!");
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            throw new IllegalArgumentException("请把高写确定!");
        }
        setMeasuredDimension(width, height);
        min = Math.min(width, height);
        mBitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mCanvas.drawCircle(min / 2, min / 2, min / 2, circlePaint);//目标图
        wavePaint.setXfermode(porterDuffXfermode);
        if (x > 0.35) {
            x -= 0.2;
        } else {
            x += 0.1;
        }
        if (y >= (1 - finish / 100f)) {
            y -= 0.01;
        }
        mPath.reset();//防止路径重叠
        mPath.moveTo(0, (float) (min * y));
        mPath.cubicTo(min * x, (min * y) + (min / 10), (min * x) + (min / 2), min * y - (min / 10), min, min * y);
        mPath.lineTo(min, min);
        mPath.lineTo(0, min);
        mPath.close();
        mCanvas.drawPath(mPath, wavePaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidateDelayed(100);
    }

    private int finish;
    boolean isleft;

    public void setFinnish(int finish) {
        this.finish = finish;
    }
}
