package cn.woniu.demo.camera;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import woniu.cn.libcamera.MagicEngine;
import woniu.cn.libcamera.camera.CameraEngine;
import woniu.cn.libcamera.widget.MagicCameraView;
import woniu.cn.libcamera.widget.SquareCameraContainer;

public class CameraActivity extends Activity {

    private MagicEngine magicEngine;

    private MagicCameraView cameraView;

    private SquareCameraContainer mCameraContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (PermissionChecker.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[] { Manifest.permission.CAMERA },
                    0);
        } else {
            initView();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults.length != 1 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // TODO
            Log.e("CameraActivity", "==========================onRequestPermissionsResult");
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initView(){


//        MagicEngine.Builder builder = new MagicEngine.Builder();
//        magicEngine = builder.build((MagicCameraView)findViewById(R.id.glsurfaceview_camera));
//        Point screenSize = new Point();
//        getWindowManager().getDefaultDisplay().getSize(screenSize);
//        cameraView = (MagicCameraView)findViewById(R.id.glsurfaceview_camera);
//        ViewGroup.LayoutParams params = cameraView.getLayoutParams();
//        params.width = screenSize.x;
//        params.height = screenSize.x * 4 / 3;
//        cameraView.setLayoutParams(params);


        mCameraContainer = (SquareCameraContainer) findViewById(R.id.cameraContainer);

        MagicEngine.Builder builder = new MagicEngine.Builder();
        magicEngine = builder.build(mCameraContainer.getCameraView());


        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        ViewGroup.LayoutParams params = mCameraContainer.getCameraView().getLayoutParams();
        params.width = screenSize.x;
        params.height = screenSize.x * 4 / 3;
        mCameraContainer.getCameraView().setLayoutParams(params);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO
        if (mCameraContainer != null) {
            mCameraContainer.onStart();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //TODO
        if (mCameraContainer != null) {
            mCameraContainer.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO
        if (mCameraContainer != null) {
            CameraEngine.releaseCamera();
//            mCameraManager.unbinding();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //在创建前  释放相机
    }

    /**
     * 对一些参数重置
     */
    public void rest() {
        //TODO
//        mFinishCount = 2;
    }

    /**
     * 退出按钮点击
     */
    public void onExitClicked(View view) {
        onBackPressed();
    }

    /**
     * 照相按钮点击
     */
    public void onTakePhotoClicked(View view) {
        //TODO
//        mCameraContainer.takePicture();
    }
}
