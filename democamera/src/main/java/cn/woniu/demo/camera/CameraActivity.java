package cn.woniu.demo.camera;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import woniu.cn.libcamera.MagicEngine;
import woniu.cn.libcamera.widget.MagicCameraView;

public class CameraActivity extends Activity {

    private MagicEngine magicEngine;

    private MagicCameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        MagicEngine.Builder builder = new MagicEngine.Builder();
        magicEngine = builder.build((MagicCameraView)findViewById(R.id.glsurfaceview_camera));

        initView();

    }

    private void initView(){

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        cameraView = (MagicCameraView)findViewById(R.id.glsurfaceview_camera);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cameraView.getLayoutParams();
        params.width = screenSize.x;
        params.height = screenSize.x * 4 / 3;
        cameraView.setLayoutParams(params);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO
//        if (mCameraContainer != null) {
//            mCameraContainer.onStart();
//        }

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
//        if (mCameraContainer != null) {
//            mCameraContainer.onStop();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //TODO
//        if (mCameraContainer != null) {
//            mCameraManager.unbinding();
//            mCameraManager.releaseActivityCamera();
//        }
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
