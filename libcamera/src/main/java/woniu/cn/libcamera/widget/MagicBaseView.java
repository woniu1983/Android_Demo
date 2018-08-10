package woniu.cn.libcamera.widget;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.helper.MagicFilterFactory;
import woniu.cn.libcamera.camera.filter.helper.MagicFilterType;
import woniu.cn.libcamera.camera.filter.helper.OpenGlUtils;
import woniu.cn.libcamera.camera.filter.helper.Rotation;
import woniu.cn.libcamera.camera.filter.helper.TextureRotationUtil;

/**
 * Created by why8222 on 2016/2/25.
 * Modified By Woniu.
 * 基本滤镜View
 * - 根据当前滤镜filter对象将FrameBuffer中的纹理绘制到屏幕中
 * - filter为空时， 将使用MagicCameraView中的cameraInputFilter绘制到屏幕
 */
public abstract class MagicBaseView extends GLSurfaceView implements GLSurfaceView.Renderer {
    /**
     * 所选择的滤镜
     * 1.mCameraInputFilter将SurfaceTexture中YUV数据绘制到 FrameBuffer(在**CameraView中的filter对象)
     * 2.filter将FrameBuffer中的纹理绘制到屏幕中
     */
    protected GPUImageFilter filter;

    /**
     * SurfaceTexure ID : 纹理id
     */
    protected int textureId = OpenGlUtils.NO_TEXTURE;

    /**
     * 顶点坐标
     */
    protected final FloatBuffer gLCubeBuffer;

    /**
     * 纹理坐标
     */
    protected final FloatBuffer gLTextureBuffer;

    /**
     * GLSurfaceView的宽高
     */
    protected int surfaceWidth, surfaceHeight;

    /**
     * 图像宽高
     */
    protected int imageWidth, imageHeight;

    protected ScaleType scaleType = ScaleType.FIT_XY;

    public MagicBaseView(Context context) {
        this(context, null);
    }

    public MagicBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        gLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);

        gLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        gLTextureBuffer.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);

        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glDisable(GL10.GL_DITHER);
        GLES20.glClearColor(0,0, 0, 0);// 设置清屏颜色RGBA
        GLES20.glEnable(GL10.GL_CULL_FACE);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);//设置窗口大小
        surfaceWidth = width;
        surfaceHeight = height;
        onFilterChanged();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 设置清屏颜色RGBA
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // 调用glClear(GL10.GL_COLOR_BUFFER_BIT)方法清除屏幕颜色,执行这个方法之后
        // 屏幕就会渲染之前通过glClearColor设置的清屏颜色.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    /**
     * 当用户选择其他滤镜时调用
     */
    protected void onFilterChanged(){
        // TODO
        if(filter == null) {
            setFilter(MagicFilterType.IMAGE_ADJUST);
        }
        if(filter != null) {
            filter.onDisplaySizeChanged(surfaceWidth, surfaceHeight);
            filter.onInputSizeChanged(imageWidth, imageHeight);
        }
    }

    public void setFilter(final MagicFilterType type){
        queueEvent(new Runnable() {
            @Override
            public void run() {
                if (filter != null)
                    filter.destroy();
                filter = null;
                filter = MagicFilterFactory.initFilters(type);
                if (filter != null)
                    filter.init();
                onFilterChanged();
            }
        });
        Log.i("MagicBaseView", "----> Call requestRender to refresh frame in screeen");
        requestRender();
    }

    protected void deleteTextures() {
        if(textureId != OpenGlUtils.NO_TEXTURE){
            queueEvent(new Runnable() {
                @Override
                public void run() {
                    GLES20.glDeleteTextures(1, new int[]{
                            textureId
                    }, 0);
                    textureId = OpenGlUtils.NO_TEXTURE;
                }
            });
        }
    }

    //TODO 注释掉
//    public abstract void savePicture(SavePictureTask savePictureTask);

    protected void adjustSize(int rotation, boolean flipHorizontal, boolean flipVertical){
        float[] textureCords = TextureRotationUtil.getRotation(Rotation.fromInt(rotation),
                flipHorizontal, flipVertical);
        float[] cube = TextureRotationUtil.CUBE;
        float ratio1 = (float)surfaceWidth / imageWidth;
        float ratio2 = (float)surfaceHeight / imageHeight;
        float ratioMax = Math.max(ratio1, ratio2);
        int imageWidthNew = Math.round(imageWidth * ratioMax);
        int imageHeightNew = Math.round(imageHeight * ratioMax);

        Log.i("MagicBaseView==", "" + ratio1 + "-" + ratio2
                    + "-imageWidthNew：" + imageWidthNew + "-imageHeightNew：" + imageHeightNew
                + "-imageWidth：" + imageWidth+ "-imageHeight：" + imageHeight);

        float ratioWidth = imageWidthNew / (float)surfaceWidth;
        float ratioHeight = imageHeightNew / (float)surfaceHeight;

        if(scaleType == ScaleType.CENTER_INSIDE){
            cube = new float[]{
                    TextureRotationUtil.CUBE[0] / ratioHeight, TextureRotationUtil.CUBE[1] / ratioWidth,
                    TextureRotationUtil.CUBE[2] / ratioHeight, TextureRotationUtil.CUBE[3] / ratioWidth,
                    TextureRotationUtil.CUBE[4] / ratioHeight, TextureRotationUtil.CUBE[5] / ratioWidth,
                    TextureRotationUtil.CUBE[6] / ratioHeight, TextureRotationUtil.CUBE[7] / ratioWidth,
            };
        }else if(scaleType == ScaleType.FIT_XY){

        }else if(scaleType == ScaleType.CENTER_CROP){
            float distHorizontal = (1 - 1 / ratioWidth) / 2;
            float distVertical = (1 - 1 / ratioHeight) / 2;
            textureCords = new float[]{
                    addDistance(textureCords[0], distVertical), addDistance(textureCords[1], distHorizontal),
                    addDistance(textureCords[2], distVertical), addDistance(textureCords[3], distHorizontal),
                    addDistance(textureCords[4], distVertical), addDistance(textureCords[5], distHorizontal),
                    addDistance(textureCords[6], distVertical), addDistance(textureCords[7], distHorizontal),
            };
        }
        gLCubeBuffer.clear();
        gLCubeBuffer.put(cube).position(0);
        gLTextureBuffer.clear();
        gLTextureBuffer.put(textureCords).position(0);
    }

    private float addDistance(float coordinate, float distance) {
        return coordinate == 0.0f ? distance : 1 - distance;
    }

    public enum  ScaleType{
        CENTER_INSIDE,
        CENTER_CROP,
        FIT_XY;
    }
}
