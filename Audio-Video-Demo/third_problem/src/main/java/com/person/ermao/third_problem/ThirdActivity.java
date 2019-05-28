package com.person.ermao.third_problem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by 冯飞杨 on 2019/5/28.
 */
public class ThirdActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;

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
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    public void startPreview(View view) {
//        ErmaoCamera.getInstance(this).preview(mSurfaceView.getHolder());
    }

    public void startTakePic(View view) {
        ErmaoCamera.getInstance(this).takePic();
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
