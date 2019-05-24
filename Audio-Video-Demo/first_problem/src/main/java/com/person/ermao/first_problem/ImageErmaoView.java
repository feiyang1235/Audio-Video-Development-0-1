package com.person.ermao.first_problem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 冯飞杨 on 2019/5/24.
 */
public class ImageErmaoView extends View {
    private Context context;
    private Paint paint;
    private Bitmap bitmap;

    public ImageErmaoView(Context context) {
        super(context);
        init(context);
    }

    public ImageErmaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageErmaoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ermao);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bitmap!=null) {
            setMeasuredDimension(bitmap.getWidth(), bitmap.getHeight());
        }else {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
