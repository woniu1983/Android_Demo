package woniu.cn.libcamera.application;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import woniu.cn.libcamera.camera.operation.impl.CameraManager;
import woniu.cn.libcamera.util.SPConfigUtil;

public class BaseCameraApplication extends Application {

    public static BaseCameraApplication CONTEXT;
    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;
    public static boolean hasCameraSupport = false;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = this;

        // 初始化 CameraManager 类中的属性
        CameraManager.init(this.getApplicationContext());
        // 初始化 SPConfigUtil 类中的属性
        SPConfigUtil.init(this.getApplicationContext());
        // 是否支持相机
        hasCameraSupport = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        // DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        mScreenWidth = mDisplayMetrics.widthPixels;
        mScreenHeight = mDisplayMetrics.heightPixels;

    }
}
