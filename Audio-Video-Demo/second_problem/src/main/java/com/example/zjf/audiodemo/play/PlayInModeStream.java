package com.example.zjf.audiodemo.play;


import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.example.zjf.audiodemo.Consts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author zjf
 * @description 工厂模式的产品类：使用AudioTrack播放pcm音频
 * @date 2018/9/6 9:47
 */
public class PlayInModeStream extends StartPlayable {
    private AudioTrack audioTrack;
    private Context context;
    private static final String TAG = PlayInModeStream.class.getSimpleName();

    public PlayInModeStream(Context mContext) {
        context = mContext;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startPlayPcm() {
        /*
         * SAMPLE_RATE_INHZ 对应pcm音频的采样率
         * channelConfig 对应pcm音频的声道
         * AUDIO_FORMAT 对应pcm音频的格式
         * */

        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        final int minBufferSize = AudioTrack.getMinBufferSize(Consts.SAMPLE_RATE_INHZ, channelConfig, Consts.AUDIO_FORMAT);
        audioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(Consts.SAMPLE_RATE_INHZ)
                        .setEncoding(Consts.AUDIO_FORMAT)
                        .setChannelMask(channelConfig)
                        .build(),
                minBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        audioTrack.play();

        final File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.pcm");
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] tmpBuffer = new byte[minBufferSize];
                        while (fileInputStream.available() > 0 && audioTrack != null) {
                            int readCount = fileInputStream.read(tmpBuffer);
                            if (readCount == AudioTrack.ERROR_BAD_VALUE || readCount == AudioTrack.ERROR_INVALID_OPERATION) {
                                continue;
                            }
                            if (readCount != 0 && readCount != -1) {
                                audioTrack.write(tmpBuffer, 0, readCount);
                            }
                        }
                    } catch (IOException|IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopPlayPcm() {
        if (audioTrack != null) {
            Log.d(TAG, "Stopping");
            audioTrack.stop();
            Log.d(TAG, "Releasing");
            audioTrack.release();
            Log.d(TAG, "Nulling");
        }
    }
}
