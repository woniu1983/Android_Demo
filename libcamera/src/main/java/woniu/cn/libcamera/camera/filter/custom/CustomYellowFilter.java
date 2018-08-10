package woniu.cn.libcamera.camera.filter.custom;

import woniu.cn.libcamera.R;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.helper.OpenGlUtils;

public class CustomYellowFilter extends GPUImageFilter {

    public CustomYellowFilter() {
        super(NO_FILTER_VERTEX_SHADER, OpenGlUtils.readShaderFromRawResource(R.raw.custom_yellow));
    }

    @Override
    public void onInit() {
        super.onInit();
//        mContrastLocation = GLES20.glGetUniformLocation(getProgram(), "contrast");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
//        setContrast(mContrast);
    }
}
