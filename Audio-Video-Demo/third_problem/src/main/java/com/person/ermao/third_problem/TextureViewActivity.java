package com.person.ermao.third_problem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zjf.audiodemo.play.PlayFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 冯飞杨
 * @date 2019/6/5
 */
public class TextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {
    private TextureView textureView;
    private Camera camera;
    private ImageView icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_texture);
        textureView = findViewById(R.id.texture_view);
        icon = findViewById(R.id.icon);
        textureView.setKeepScreenOn(true);
        textureView.setSurfaceTextureListener(this);
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setRotation(90);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setPreviewFormat(ImageFormat.NV21);
        camera.setParameters(parameters);
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    Toast.makeText(TextureViewActivity.this, "对焦成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TextureViewActivity.this, "对焦失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Size previewSize = camera.getParameters().getPreviewSize();
                YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                yuvImage.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                icon.setImageBitmap(rotateBitmap(bitmap, getDegree()));
            }
        });
    }

    private int getDegree() {
        //获取当前屏幕旋转的角度
        int rotating = this.getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;//度数
        //根据手机旋转的角度，来设置surfaceView的显示的角度
        switch (rotating) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param degree 旋转角度，可正可负
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap origin, float degree) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(degree);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            camera.setPreviewTexture(surface);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.stopPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.release();
    }

    public void startTakePic(View view) {
        takePic();
    }

    private void takePic() {
        if (camera != null) {
            camera.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    new PlayFactory(TextureViewActivity.this.getApplicationContext()).createPlay(PlayFactory.STATICMODE).startPlayPcm();
                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (data == null || data.length == 0) {
                        return;
                    }
                    File file = new File(TextureViewActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "texture_wfs.img");
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

                    if (camera != null) {
                        camera.startPreview();
                    }
                }
            }, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    if (data == null || data.length == 0) {
                        return;
                    }
                    File file = new File(TextureViewActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "texture_wfs1.jpg");
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

                    if (camera != null) {
                        camera.startPreview();
                    }
                }
            });
        }
    }
}
