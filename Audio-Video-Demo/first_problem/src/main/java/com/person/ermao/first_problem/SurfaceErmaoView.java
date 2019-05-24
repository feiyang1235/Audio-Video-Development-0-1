package com.person.ermao.first_problem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 冯飞杨 on 2019/5/24.
 */
public class SurfaceErmaoView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Bitmap bitmap;

    public SurfaceErmaoView(Context context) {
        super(context);
        init(context);
    }

    public SurfaceErmaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SurfaceErmaoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ermao);
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bitmap != null) {
            setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //surface 创建
        Canvas canvas = holder.lockCanvas();
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //surface 大小发生变化的时候
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //销毁时发生,一般在这里将画图停止.释放
        if (bitmap != null) {
            bitmap.recycle();
        }
    }
}
