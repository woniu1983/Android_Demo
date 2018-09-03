package woniu.cn.libcamera.camera;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraEngine {
    private static Camera camera = null;
    private static int cameraID = 0;
    private static SurfaceTexture surfaceTexture;
    private static SurfaceView surfaceView;

    public static Camera getCamera(){
        return camera;
    }

    public static boolean openCamera(){
        if(camera == null){
            try{
                camera = Camera.open(cameraID);
                setDefaultParameters();
                return true;
            }catch(RuntimeException e){
                return false;
            }
        }
        return false;
    }

    public static boolean openCamera(int id){
        if(camera == null){
            try{
                camera = Camera.open(id);
                cameraID = id;
                setDefaultParameters();
                return true;
            }catch(RuntimeException e){
                return false;
            }
        }
        return false;
    }

    public static void releaseCamera(){
        if(camera != null){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public static void resumeCamera(){
        openCamera();
    }

    public static void setParameters(Parameters parameters){
        camera.setParameters(parameters);
    }

    public static Parameters getParameters(){
        if(camera != null)
            camera.getParameters();
        return null;
    }

    public static void switchCamera(){
        releaseCamera();
        cameraID = cameraID == 0 ? 1 : 0;
        openCamera(cameraID);
        startPreview(surfaceTexture);
    }

    private static void setDefaultParameters(){
        Parameters parameters = camera.getParameters();

        // TODO
        // 设置对焦模式
//        if (parameters.getSupportedFocusModes().contains(
//                Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//            parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//        }
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        printParameters();

        Size previewSize = CameraUtils.getLargePreviewSize(camera);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        Size pictureSize = CameraUtils.getLargePictureSize(camera);
        parameters.setPictureSize(pictureSize.width, pictureSize.height);
        parameters.setRotation(90);
        camera.setParameters(parameters);
    }

    private static void printParameters() {
        if (camera == null) {
            return;
        }
        Parameters parameters = camera.getParameters();
        int minExpo = parameters.getMinExposureCompensation();
        int maxExpo = parameters.getMaxExposureCompensation();
        int currExpo = parameters.getExposureCompensation();

//        Log.i("CameraEngine", ">>ExposureCompensation=" + currExpo + "[" + minExpo + "--" + maxExpo + "]");
////
////        List<String> scenList = parameters.getSupportedSceneModes();
////        String currScen = parameters.getSceneMode();
////        for (String scen : scenList) {
////            Log.i("CameraEngine", "SupportedSceneMode: " + scen);
////        }
////        Log.i("CameraEngine", "Current Scene: " + currScen);
////
////        List<String> wbList = parameters.getSupportedWhiteBalance();
////        String currWb = parameters.getWhiteBalance();
////        for (String wb : wbList) {
////            Log.i("CameraEngine", "SupportedWhiteBalance: " + wb);
////        }
////        Log.i("CameraEngine", "Current WhiteBalance: " + currWb);

        String flatten = parameters.flatten();
        String[] attrs = flatten.split(";");
        for (String attr : attrs) {
            Log.i("CameraEngine", attr);
        }
//        flatten = flatten.replaceAll(";", ";\n");


    }

    private static Size getPreviewSize(){
        return camera.getParameters().getPreviewSize();
    }

    private static Size getPictureSize(){
        return camera.getParameters().getPictureSize();
    }

    public static void startPreview(SurfaceTexture surfaceTexture){
        if(camera != null)
            try {
                camera.setPreviewTexture(surfaceTexture);
                CameraEngine.surfaceTexture = surfaceTexture;
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void startPreview(){
        if(camera != null)
            camera.startPreview();
    }

    public static void stopPreview(){
        camera.stopPreview();
    }

    public static void setRotation(int rotation){
        Parameters params = camera.getParameters();
        params.setRotation(rotation);
        camera.setParameters(params);
    }

    public static void takePicture(Camera.ShutterCallback shutterCallback, Camera.PictureCallback rawCallback,
                                   Camera.PictureCallback jpegCallback){
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    public static woniu.cn.libcamera.camera.CameraInfo getCameraInfo(){
        woniu.cn.libcamera.camera.CameraInfo info = new woniu.cn.libcamera.camera.CameraInfo();
        Size size = getPreviewSize();
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraID, cameraInfo);
        info.previewWidth = size.width;
        info.previewHeight = size.height;
        info.orientation = cameraInfo.orientation;
        info.isFront = cameraID == 1 ? true : false;
        size = getPictureSize();
        info.pictureWidth = size.width;
        info.pictureHeight = size.height;
        return info;
    }


    //////////////对焦代码



    private static final String TAG = "CameraEngine";

    /**
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    public static boolean onFocus(Point point, Camera.AutoFocusCallback callback) {
        if (camera == null) {
            return false;
        }

        Parameters parameters = null;
        try {
            parameters = camera.getParameters();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //不支持设置自定义聚焦，则使用自动聚焦，返回

        if(Build.VERSION.SDK_INT >= 14) {

            if (parameters.getMaxNumFocusAreas() <= 0) {
                return focus(callback);
            }

            Log.v(TAG, "onCameraFocus:" + point.x + "," + point.y);

            List<Camera.Area> areas = new ArrayList<Camera.Area>();
            int left = point.x - 300;
            int top = point.y - 300;
            int right = point.x + 300;
            int bottom = point.y + 300;
            left = left < -1000 ? -1000 : left;
            top = top < -1000 ? -1000 : top;
            right = right > 1000 ? 1000 : right;
            bottom = bottom > 1000 ? 1000 : bottom;
            areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
            parameters.setFocusAreas(areas);
            try {
                //本人使用的小米手机在设置聚焦区域的时候经常会出异常，看日志发现是框架层的字符串转int的时候出错了，
                //目测是小米修改了框架层代码导致，在此try掉，对实际聚焦效果没影响
                camera.setParameters(parameters);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return false;
            }
        }


        return focus(callback);
    }

    private static boolean focus(Camera.AutoFocusCallback callback) {
        try {
            camera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}