package com.person.ermao.third_problem;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.zjf.audiodemo.play.PlayFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 冯飞杨 on 2019/5/28.
 */
public class ErmaoCamera {
    private static ErmaoCamera instance;
    private Camera open;
    private Context mContext;

    private ErmaoCamera(final Context context) {
        this.mContext = context;
    }

    public static synchronized ErmaoCamera getInstance(Context context) {
        synchronized (ErmaoCamera.class) {
            if (instance == null) {
                instance = new ErmaoCamera(context);
            }
        }
        return instance;
    }

    public void initCamera() {
        if (open == null) {
            open = Camera.open();
            open.setDisplayOrientation(90);
            Camera.Parameters parameters = open.getParameters();
            parameters.setRotation(90);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            open.setParameters(parameters);
            open.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        Toast.makeText(mContext, "对焦成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "对焦失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void preview(SurfaceHolder surfaceHolder) {
        try {
            if (open != null) {
                open.setPreviewDisplay(surfaceHolder);
                open.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takePic() {
        if (open != null) {
            open.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    new PlayFactory(mContext.getApplicationContext()).createPlay(PlayFactory.STATICMODE).startPlayPcm();
                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (data == null || data.length == 0) {
                        return;
                    }
                    File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "wfs.img");
                    try {
                        FileOutputStream os = new FileOutputStream(file);
                        os.write(data);
                        os.flush();
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (open != null) {
                        open.startPreview();
                    }
                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (data == null || data.length == 0) {
                        return;
                    }
                    File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "wfs1.jpg");
                    try {
                        FileOutputStream os = new FileOutputStream(file);
                        os.write(data);
                        os.flush();
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (open != null) {
                        open.startPreview();
                    }
                }
            });
        }
    }

    public void resumePreview() {
        if (open != null) {
            open.startPreview();
        }
    }

    public void stopPreview() {
        if (open != null) {
            open.stopPreview();
        }
    }

    public void release() {
        if (open != null) {
            open.setPreviewCallback(null);
            open.stopPreview();
            open.lock();
            open.release();
            open = null;
        }
    }
}
