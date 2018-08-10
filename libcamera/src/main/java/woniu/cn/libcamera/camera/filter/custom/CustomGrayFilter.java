package woniu.cn.libcamera.camera.filter.custom;

import woniu.cn.libcamera.R;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.helper.OpenGlUtils;

public class CustomGrayFilter extends GPUImageFilter {


    public CustomGrayFilter() {
        super(NO_FILTER_VERTEX_SHADER, OpenGlUtils.readShaderFromRawResource(R.raw.custom_gray));
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
