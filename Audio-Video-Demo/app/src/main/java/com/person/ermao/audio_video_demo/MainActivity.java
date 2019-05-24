package com.person.ermao.audio_video_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.person.ermao.first_problem.ShowImageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTaskClick(View view) {
        startActivity(new Intent(this,ShowImageActivity.class));
    }
}
