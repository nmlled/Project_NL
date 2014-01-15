package com.example.project_nl;

import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity creates a surface view to use the device camera
 * 
 * @author Challenge.IT
 */
public class MainActivity2 extends Activity implements SurfaceHolder.Callback {
    
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private final int CAMERA_NUMBER = 1;
    //private PhotoManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);
        manager = new PhotoManager(this);
        
        //setup button listeners
             // capture button
        findViewById(R.id.btn_take_photo).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                camera.takePicture(null, null, new PictureCallback() {
                                        @Override
                                        public void onPictureTaken(byte[] data, Camera camera) {
                                                createPopup(data);
                                                camera.startPreview();
                                        }
                                });
                        }
                });
        // list button
        findViewById(R.id.btn_list).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, PhotosListActivity.class));
                        }
                });
        
        // surface settings
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        */
    }

    //This is called immediately after any structural changes (format or size) have been made to the surface.
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        camera.startPreview();
    }

    //This is called immediately after the surface is first created.
    @Override
    public void surfaceCreated(SurfaceHolder holder) {    
    	/*
            try
            {
                    camera = Camera.open();
            }
            catch(RuntimeException e)
            {
                    Toast.makeText(MainActivity.this, R.string.camera_error, Toast.LENGTH_SHORT).show();
                    return;
            }
            setCameraDisplayOrientation(MainActivity.this, CAMERA_NUMBER, camera);
            try {
                    camera.setPreviewDisplay(surfaceHolder);
                    //camera.startPreview();
            } catch (Exception e) {
                    Toast.makeText(MainActivity.this, R.string.camera_prev_error, Toast.LENGTH_SHORT).show();
                    return;
            }
            */
    }

    //This is called immediately before a surface is being destroyed.
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) 
    {
    	/*
        camera.stopPreview();
        camera.release();
        camera = null;
        */
    }
    
    /**
     * Code available at: 
     * http://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation%28int%29
     * 
     * @param activity
     * @param cameraId
     * @param camera
     */
    private static void setCameraDisplayOrientation(Activity activity,
            int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    
    /**
     * This method builds a popup window to user type the name and description of the photo
     * @param photo
     */
    private void createPopup(final byte[] photo)
    {
    	/*
            // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        
            // prepare popup dialog
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
                final View view = inflater.inflate(R.layout.photo_popup, null);
                adBuilder.setView(view);
                adBuilder.setTitle(R.string.popup_title);
                adBuilder.setCancelable(true);
                
                // buttons behavior
                adBuilder.setNegativeButton(getString(R.string.btn_cancel),
                                new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                        }
                }); 
                adBuilder.setPositiveButton(getString(R.string.btn_submit),
                                new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                                EditText editName = (EditText) view.findViewById(R.id.edit_name);
                                EditText editDesc = (EditText) view.findViewById(R.id.edit_desc);
                                manager.save(new Photo(photo,
                                                         editDesc.getText().toString().isEmpty() ? getString(R.string.default_desc) : editDesc.getText().toString(),
                                                         editName.getText().toString().isEmpty() ? getString(R.string.default_name) : editName.getText().toString(),
                                                         new Date()
                                                ));
                        }
                });
                
                // create and show popup
                AlertDialog alertDialog = adBuilder.create();
                alertDialog.show();
		*/
    }
}
