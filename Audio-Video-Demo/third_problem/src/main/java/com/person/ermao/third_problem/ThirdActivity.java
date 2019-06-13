package com.person.ermao.third_problem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 冯飞杨 on 2019/5/28.
 */
public class ThirdActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;
    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ErmaoCamera.getInstance(this).initCamera();
        mSurfaceView = findViewById(R.id.surface_view);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                ErmaoCamera.getInstance(ThirdActivity.this).preview(holder);
                Log.i(TAG, "surfaceCreated: ");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG, "surfaceChanged: format:" + format + ",width:" + width + ",height:" + height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.i(TAG, "surfaceDestroyed: ");
            }
        });

    }

    public void startPreview(View view) {
//        ErmaoCamera.getInstance(this).preview(mSurfaceView.getHolder());
    }

    public void startTakePic(View view) {
//        ErmaoCamera.getInstance(this).takePic();
        ViewGroup.LayoutParams layoutParams = mSurfaceView.getLayoutParams();
        layoutParams.height *= 2;
        layoutParams.width *= 2;
        mSurfaceView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ErmaoCamera.getInstance(this).resumePreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ErmaoCamera.getInstance(this).stopPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ErmaoCamera.getInstance(this).release();
    }
}
