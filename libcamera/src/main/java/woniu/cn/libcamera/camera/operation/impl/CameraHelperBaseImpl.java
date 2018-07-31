package woniu.cn.libcamera.camera.operation.impl;

import android.content.pm.PackageManager;
import android.hardware.Camera;

import woniu.cn.libcamera.application.BaseCameraApplication;
import woniu.cn.libcamera.camera.operation.ICameraHelper;

/**
 * API 9 以下使用  只支持一个摄像头
 * @author Woniu
 */
public class CameraHelperBaseImpl implements ICameraHelper {

    @Override
    public int getNumberOfCameras() {
        return hasCameraSupport() ? 1 : 0;
    }

    @Override
    public Camera openCameraFacing(int facing) {
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            return Camera.open();
        }
        return null;
    }

    @Override
    public boolean hasCamera(int facing) {
        if (facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            return hasCameraSupport();
        }
        return false;
    }

    @Override
    public void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo) {
        cameraInfo.facing = Camera.CameraInfo.CAMERA_FACING_BACK;
        cameraInfo.orientation = 90;
    }

    private boolean hasCameraSupport() {
        return BaseCameraApplication.hasCameraSupport;
    }
}
