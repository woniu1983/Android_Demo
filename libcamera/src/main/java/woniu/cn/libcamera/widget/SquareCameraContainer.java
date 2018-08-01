package woniu.cn.libcamera.widget;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import woniu.cn.libcamera.R;
import woniu.cn.libcamera.application.BaseCameraApplication;
import woniu.cn.libcamera.camera.focus.SensorControler;

public class SquareCameraContainer extends FrameLayout {

    private Context mContext;

    /**
     * 相机绑定的GLSurfaceView
     */
    private MagicCameraView mCameraView;

    /**
     * 触摸屏幕时显示的聚焦图案
     */
    private FocusImageView mFocusImageView;
    /**
     * 缩放控件
     */
    private SeekBar mZoomSeekBar;

    /**
     * 声音播放
     */
    private SoundPool mSoundPool;

    private boolean mFocusSoundPrepared;

    private int mFocusSoundId;

    public static final int RESETMASK_DELY = 1000; //一段时间后遮罩层一定要隐藏


    public SquareCameraContainer(Context context) {
        super(context);

        mContext = context;
        init();
    }

    public SquareCameraContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    void init() {
        inflate(mContext, R.layout.custom_camera_container, this);

        mCameraView = (MagicCameraView) findViewById(R.id.cameraView);
        mFocusImageView = (FocusImageView) findViewById(R.id.focusImageView);
        mZoomSeekBar = (SeekBar) findViewById(R.id.zoomSeekBar);

        mZoomSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        mSensorControler = SensorControler.getInstance();

        mSensorControler.setCameraFocusListener(new SensorControler.CameraFocusListener() {
            @Override
            public void onFocus() {
                int screenWidth = BaseCameraApplication.mScreenWidth;
                Point point = new Point(screenWidth / 2, screenWidth / 2);

                onCameraFocus(point);
            }
        });

        //音效初始化
        mSoundPool = getSoundPool();
    }

    private SoundPool getSoundPool(){
        if(mSoundPool == null) {
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            mFocusSoundId = mSoundPool.load(mContext,R.raw.camera_focus,1);
            mFocusSoundPrepared = false;
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    mFocusSoundPrepared = true;
                }
            });
        }
        return mSoundPool;
    }


    // ######################################
    // 图像ZOOM
    // ######################################


    /**
     * 记录是拖拉照片模式还是放大缩小照片模式
     */

    private static final int MODE_INIT = 0;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 1;
    private int mode = MODE_INIT;// 初始状态

    private float startDis;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    // TODO
    // TODO

    @Override
    public boolean onTouchEvent(MotionEvent event) {
/** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 手指压下屏幕
            case MotionEvent.ACTION_DOWN:
                mode = MODE_INIT;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //如果mZoomSeekBar为null 表示该设备不支持缩放 直接跳过设置mode Move指令也无法执行
                if (mZoomSeekBar == null) return true;
                //移除token对象为mZoomSeekBar的延时任务
                mHandler.removeCallbacksAndMessages(mZoomSeekBar);
//                mZoomSeekBar.setVisibility(View.VISIBLE);
                mZoomSeekBar.setVisibility(View.GONE);

                mode = MODE_ZOOM;
                /** 计算两个手指间的距离 */
                startDis = spacing(event);
                break;
//            case MotionEvent.ACTION_MOVE:
//                if (mode == MODE_ZOOM) {
//                    //只有同时触屏两个点的时候才执行
//                    if (event.getPointerCount() < 2) return true;
//                    float endDis = spacing(event);// 结束距离
//                    //每变化10f zoom变1
//                    int scale = (int) ((endDis - startDis) / 10f);
//                    if (scale >= 1 || scale <= -1) {
//                        int zoom = mCameraView.getZoom() + scale;
//                        //zoom不能超出范围
//                        if (zoom > mCameraView.getMaxZoom()) zoom = mCameraView.getMaxZoom();
//                        if (zoom < 0) zoom = 0;
//                        mCameraView.setZoom(zoom);
//                        mZoomSeekBar.setProgress(zoom);
//                        //将最后一次的距离设为当前距离
//                        startDis = endDis;
//                    }
//                }
//                break;
            // 手指离开屏幕
            case MotionEvent.ACTION_UP:
                if (mode != MODE_ZOOM) {
                    //设置聚焦
                    Point point = new Point((int) event.getX(), (int) event.getY());
                    onCameraFocus(point);
                } else {
                    //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
                    mHandler.postAtTime(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mZoomSeekBar.setVisibility(View.GONE);
                        }
                    }, mZoomSeekBar, SystemClock.uptimeMillis() + 2000);
                }
                break;
        }
        return true;
    }

    /**
     * 两点的距离
     */
    private float spacing(MotionEvent event) {
        if (event == null) {
            return 0;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
//        return FloatMath.sqrt(x * x + y * y);
        return (float) Math.sqrt(x * x + y * y);
    }



    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO
            // TODO
//            mCameraView.setZoom(progress);
//            mHandler.removeCallbacksAndMessages(mZoomSeekBar);
//            //ZOOM模式下 在结束两秒后隐藏seekbar 设置token为mZoomSeekBar用以在连续点击时移除前一个定时任务
//            mHandler.postAtTime(new Runnable() {
//
//                @Override
//                public void run() {
//                    mZoomSeekBar.setVisibility(View.GONE);
//                }
//            }, mZoomSeekBar, SystemClock.uptimeMillis() + 2000);
        }


        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }
    };



    // ######################################
    // 相机对焦
    // ######################################


    private SensorControler mSensorControler;

    /**
     * 相机对焦  默认不需要延时
     *
     * @param point
     */
    private void onCameraFocus(final Point point) {
        onCameraFocus(point, false);
    }

    /**
     * 相机对焦
     *
     * @param point
     * @param needDelay 是否需要延时
     */
    public void onCameraFocus(final Point point, boolean needDelay) {
        long delayDuration = needDelay ? 300 : 0;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mSensorControler.isFocusLocked()) {
                    if (mCameraView.onFocus(point, autoFocusCallback)) { //TODO
                        mSensorControler.lockFocus();
                        mFocusImageView.startFocus(point);

                        //播放对焦音效
                        if(mFocusSoundPrepared) {
                            mSoundPool.play(mFocusSoundId, 1.0f, 0.5f, 1, 0, 1.0f);
                        }
                    }
                }
            }
        }, delayDuration);
    }

    private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            if (success) {
                mFocusImageView.onFocusSuccess();
            } else {
                //聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
                mFocusImageView.onFocusFailed();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //一秒之后才能再次对焦
                    mSensorControler.unlockFocus();
                }
            }, 1000);
        }
    };

    public MagicCameraView getCameraView() {
        return mCameraView;
    }
}
