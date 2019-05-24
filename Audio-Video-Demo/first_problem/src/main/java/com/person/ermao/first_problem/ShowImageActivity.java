package com.person.ermao.first_problem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

/**
 * @author 冯飞杨
 * @date 2019/5/24
 */
public class ShowImageActivity extends AppCompatActivity {
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        surfaceView = findViewById(R.id.surface_view);

    }
}
