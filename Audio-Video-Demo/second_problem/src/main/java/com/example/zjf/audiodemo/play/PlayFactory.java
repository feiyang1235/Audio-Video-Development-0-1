package com.example.zjf.audiodemo.play;

import android.content.Context;

/**
 * @author zjf
 * @description
 * @date 2018/9/6 10:40
 */
public class PlayFactory {
    private Context context;
    public static final int STREAMMODE = 1;
    public static final int STATICMODE = 2;
    private PlayInModeStream playInModeStream;
    private PlayInModeStatic playInModeStatic;

    public PlayFactory(Context mContext) {
        context = mContext;
    }

    public StartPlayable createPlay(int type) {
        switch (type) {
            case STREAMMODE:
                return playInModeStream == null ? (playInModeStream = new PlayInModeStream(context)) : playInModeStream;
            case STATICMODE:
                return playInModeStatic == null ? (playInModeStatic = new PlayInModeStatic(context)) : playInModeStatic;
            default:
                return new PlayInModeStream(context);
        }
    }
}
