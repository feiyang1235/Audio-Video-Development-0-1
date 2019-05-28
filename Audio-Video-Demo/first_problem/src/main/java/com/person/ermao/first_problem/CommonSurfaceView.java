package com.person.ermao.first_problem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 冯飞杨 on 2019/5/28.
 */
public class CommonSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    //用于绘制的线程
    private Thread mThread;
    //线程状态的标记(线程的控制开关)
    private boolean isRunning;
    private int x, y;
    private Paint mPaint;

    public CommonSurfaceView(Context context) {
        this(context, null);
    }

    public CommonSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        mHolder = getHolder();
        mHolder.addCallback(this);//管理生命周期
        //获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常量
        setKeepScreenOn(true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFF0000FF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        isRunning = true;
        mThread = new Thread(this);
        mThread.start();//开启线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        //不断地进行绘制
        while (isRunning) {
            draw();
        }
    }

    private void draw() {

        //为什么要try catch 因为当view在主界面时有可能按Home键或Back键时回到界面，Surface被销毁了。
        //这时有可能已经进入到draw() ，这时候获取的mCanvas可能为null。
        // 还有一种可能，就是界面被销毁的，我们的线程还没有销毁，mCanvas可能为null。

        try {
            //获取Canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //do something
                if(x>=10){
                    mCanvas.drawText(String.valueOf(x++),getWidth()/2,getHeight()/2,mPaint);
//                    mHolder.unlockCanvasAndPost(mCanvas);
//                    mCanvas = mHolder.lockCanvas();
//                    mHolder.unlockCanvasAndPost(mCanvas);
//                    mCanvas = mHolder.lockCanvas();
//                    mHolder.unlockCanvasAndPost(mCanvas);
//                    mCanvas = mHolder.lockCanvas();
//                    mCanvas.drawColor(0xFFFFFFFF);
//                    mCanvas.drawText(String.valueOf(x++),getWidth()/2,getHeight()/2,mPaint);
                    isRunning = false;
                }else {
                    mCanvas.drawColor(0xFFFFFFFF);
                    mCanvas.drawText(String.valueOf(x++),getWidth()/2,getHeight()/2,mPaint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                //释放Canvas
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }
}
