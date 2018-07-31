package woniu.cn.libcamera.camera.operation;

import android.content.Context;
import android.hardware.Camera;

public interface ICameraHelper {

    public int getNumberOfCameras();

    public Camera openCameraFacing(int facing) throws Exception;

    public boolean hasCamera(int facing);

    public void getCameraInfo(int cameraId, Camera.CameraInfo cameraInfo);
}
