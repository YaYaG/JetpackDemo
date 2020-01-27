package com.wangjy.camerax;

import android.Manifest;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private TextureView mViewFinder;
    private final int REQUEST_CODE_PERMISSIONS = 10;
    private String REQUIRED_PERMISSIONS[] = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewFinder = findViewById(R.id.view_finder);

        if (allPermissionsGranted()) {
            mViewFinder.post(new Runnable() {
                @Override
                public void run() {
                    startCamera();
                }
            });
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }


    private void startCamera() {
        PreviewConfig previewConfig = new PreviewConfig.Builder().build();

        Preview preview = new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(@NonNull Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) mViewFinder.getParent();
                parent.removeView(mViewFinder);
                parent.addView(mViewFinder, 0);
                mViewFinder.setSurfaceTexture(output.getSurfaceTexture());

                updateTransform();
            }
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .build();

        // Build the image capture use case and attach button click listener
        ImageCapture imageCapture = new ImageCapture(imageCaptureConfig);
        findViewById(R.id.capture_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getExternalMediaDirs()[0],
                        System.currentTimeMillis() + ".jpg");

                imageCapture.takePicture(file, executorService, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Photo capture succeeded: " + file.getAbsolutePath();
                        Log.d("CameraXApp", msg);
                        mViewFinder.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });

                        //通知显示到相册
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(file));
                        sendBroadcast(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.ImageCaptureError imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Photo capture failed: " + cause.getMessage();
                        Log.e("CameraXApp", msg + cause.getCause());
                        mViewFinder.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        CameraX.bindToLifecycle(this, preview, imageCapture);
    }


    private void updateTransform() {
        Matrix matrix = new Matrix();

        // Compute the center of the view finder
        float centerX = mViewFinder.getWidth() / 2f;
        float centerY = mViewFinder.getHeight() / 2f;

        int rotationDegrees = 0;
        // Correct preview output to account for display rotation
        switch (mViewFinder.getDisplay().getRotation()) {
            case Surface.ROTATION_0:
                rotationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                rotationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                rotationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                rotationDegrees = 270;
        }
        matrix.postRotate(-rotationDegrees, centerX, centerY);

        // Finally, apply transformations to our TextureView
        mViewFinder.setTransform(matrix);
    }

    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_CODE_PERMISSIONS == requestCode) {
            if (allPermissionsGranted()) {
                mViewFinder.post(new Runnable() {
                    @Override
                    public void run() {
                        startCamera();
                    }
                });
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }
}
