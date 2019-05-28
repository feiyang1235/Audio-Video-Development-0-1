package com.person.ermao.audio_video_demo;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zjf.audiodemo.SecondProblemActivity;
import com.person.ermao.audio_video_demo.second_problem.ErmaoAudioRecord;
import com.person.ermao.first_problem.ShowImageActivity;
import com.person.ermao.third_problem.ThirdActivity;

public class MainActivity extends AppCompatActivity {
    private ErmaoAudioRecord record = new ErmaoAudioRecord();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    public void onTaskClick(View view) {
        startActivity(new Intent(this, ShowImageActivity.class));

    }

    public void onTaskClick2(View view) {
        startActivity(new Intent(this, SecondProblemActivity.class));
    }

    public void onTaskClick3(View view) {
        startActivity(new Intent(this,ThirdActivity.class));
    }
}
