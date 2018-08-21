package woniu.cn.libcamera.camera.filter.advanced;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import woniu.cn.libcamera.camera.filter.base.MagicBaseGroupFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImage3x3TextureSamplingFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageBrightnessFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageContrastFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageExposureFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageGammaFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageGrayscaleFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageHueFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageLevelsFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSaturationFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSharpenFilter;
import woniu.cn.libcamera.camera.filter.custom.CustomGrayFilter;
import woniu.cn.libcamera.camera.filter.custom.CustomYellowFilter;

public class MagicImageAdjustFilter extends MagicBaseGroupFilter {

	public MagicImageAdjustFilter() {
		super(initFilters());
//		this.setContrast(2.0f); // contrast value ranges from 0.0 to 4.0, with 1.0 as the normal level
//		this.setBrightness(0.0f); // brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
//		this.setExposure(0.0f); // The adjusted exposure (-10.0 - 10.0, with 0.0 as the default)
//		this.setHue(0.0f);
//		this.setSaturation(1.7f);// The degree of saturation or desaturation to apply to the image (0.0 - 2.0, with 1.0 as the default)
//		this.setSharpness(2.0f);// sharpness: from -4.0 to 4.0, with 0.0 as the normal level
	}

	private static List<GPUImageFilter> initFilters(){
		String brand = android.os.Build.BRAND;
		String board = android.os.Build.BOARD;
		String manufacturer = android.os.Build.MANUFACTURER;
		Log.e("MagicImageAdjustFilter", "BOARD="+board
				+ "---- brand="+brand
				+ "---- device="+android.os.Build.DEVICE
				+ "---- product="+android.os.Build.PRODUCT
				+ "---- model="+android.os.Build.MODEL
				+ "---- manufacturer="+android.os.Build.MANUFACTURER);
		if (manufacturer == null) {
			return  initFilters_MiOK();
		} else {
			manufacturer = manufacturer.toUpperCase();
		}

		if (manufacturer.contains("OPPO")) {
			return initFilters_OPPO();
		} else if (manufacturer.contains("XIAOMI")) {
			return initFilters_MiOK();
		} else if (manufacturer.contains("HUAWEI")) {
			return initFilters_HW();
		} else {
			return initFilters_MiOK();
		}
	}

	private static List<GPUImageFilter> initFiltersA_OK(){
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();

		filters.add(new GPUImageContrastFilter(2.0f));
		filters.add(new GPUImageBrightnessFilter(-0.1f));
//		filters.add(new GPUImageExposureFilter(0.0f));
//		filters.add(new GPUImageHueFilter(0.0f));
		filters.add(new GPUImageSaturationFilter(1.7f));
		filters.add(new GPUImageGammaFilter(1.0f));// 0.0-1.0-3.0

		filters.add(new CustomGrayFilter()); //TODO //TODO //TODO
		filters.add(new MagicBlackCatFilter());//TODO TODO TODO
		filters.add(new GPUImageContrastFilter(2.0f));
		filters.add(new GPUImageSharpenFilter(1.0f));
		filters.add(new GPUImageGrayscaleFilter()); //TODO //TODO //TODO

		return filters;
	}

	private static List<GPUImageFilter> initFilters_MiOK(){
		// 小米手机OK
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();

		filters.add(new GPUImageGammaFilter(1.0f));// 0.0-1.0-3.0
		filters.add(new GPUImageHueFilter(3.0f));
		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
		filters.add(new GPUImageBrightnessFilter(-0.1f));
//		filters.add(new GPUImageExposureFilter(0.0f));
//		filters.add(new GPUImageHueFilter(0.0f));
		filters.add(new GPUImageSaturationFilter(1.7f));

		filters.add(new CustomGrayFilter()); //TODO //TODO //TODO
		filters.add(new MagicBlackCatFilter());//TODO TODO TODO
		filters.add(new GPUImageContrastFilter(2.0f));
//        filters.add(new GPUImageSharpenFilter(1.0f));
		filters.add(new GPUImageGrayscaleFilter()); //TODO //TODO //TODO

		return filters;
	}

	private static List<GPUImageFilter> initFilters_OPPO(){
		// OPPO手机
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();

		GPUImageLevelsFilter lFilter= new GPUImageLevelsFilter();
		lFilter.setMin(0.0f, 0.6f,1.0f);
		filters.add(lFilter);//TODO 对比黄色稍微出来一点
		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
//		filters.add(new GPUImageSaturationFilter(2.0f));
		filters.add(new CustomYellowFilter());

//        filters.add(new GPUImageSharpenFilter(2.0f));


//		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
////		filters.add(new GPUImageGammaFilter(3.0f));// 0.0-1.0-3.0
//		filters.add(new GPUImageHueFilter(2.0f));
////		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
//		filters.add(new GPUImageBrightnessFilter(0.0f));
////		filters.add(new GPUImageSaturationFilter(2.0f));
//
////		filters.add(new CustomGrayFilter()); //TODO //TODO //TODO
////		filters.add(new MagicBlackCatFilter());//TODO TODO TODO
////		filters.add(new GPUImageContrastFilter(2.0f));
////		filters.add(new GPUImageGrayscaleFilter()); //TODO //TODO //TODO
////		filters.add(new MagicSketchFilter());

		return filters;
	}

	private static List<GPUImageFilter> initFilters_HW(){
		// HONOR荣耀手机
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();

//		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
//		filters.add(new GPUImageBrightnessFilter(-0.1f));
//		filters.add(new GPUImageSaturationFilter(1.3f));
//
//		filters.add(new CustomGrayFilter()); //TODO //TODO //TODO
//		filters.add(new MagicBlackCatFilter());//TODO TODO TODO
//		filters.add(new GPUImageGrayscaleFilter()); //TODO //TODO //TODO


		// 新调试
		filters.add(new GPUImageGammaFilter(1.0f));// 0.0-1.0-3.0
//		filters.add(new GPUImageHueFilter(3.0f));
		filters.add(new GPUImageContrastFilter(2.0f));//3.0以上好像会变太亮了
		filters.add(new GPUImageBrightnessFilter(-0.2f));
//		filters.add(new GPUImageExposureFilter(0.0f));
//		filters.add(new GPUImageHueFilter(0.0f));
		filters.add(new GPUImageSaturationFilter(1.7f));

		filters.add(new CustomGrayFilter()); //TODO //TODO //TODO
//		filters.add(new MagicBlackCatFilter());//TODO TODO TODO
//		filters.add(new GPUImageContrastFilter(2.0f));
		filters.add(new GPUImageSharpenFilter(2.0f));
		filters.add(new GPUImageGrayscaleFilter()); //TODO //TODO //TODO

		return filters;
	}

	public void setSharpness(final float range){
		((GPUImageSharpenFilter) filters.get(5)).setSharpness(range);
	}

	public void setHue(final float range){
		((GPUImageHueFilter) filters.get(3)).setHue(range);
	}

	public void setBrightness(final float range){
		((GPUImageBrightnessFilter) filters.get(1)).setBrightness(range);
	}

	public void setContrast(final float range){
		((GPUImageContrastFilter) filters.get(0)).setContrast(range);
	}

	public void setSaturation(final float range){
		((GPUImageSaturationFilter) filters.get(4)).setSaturation(range);
	}

	public void setExposure(final float range){
		((GPUImageExposureFilter) filters.get(2)).setExposure(range);
	}


	public static final String SKETCH_FRAGMENT_SHADER = "" +
			"precision mediump float;\n" +
			"\n" +
			"varying vec2 textureCoordinate;\n" +
			"varying vec2 leftTextureCoordinate;\n" +
			"varying vec2 rightTextureCoordinate;\n" +
			"\n" +
			"varying vec2 topTextureCoordinate;\n" +
			"varying vec2 topLeftTextureCoordinate;\n" +
			"varying vec2 topRightTextureCoordinate;\n" +
			"\n" +
			"varying vec2 bottomTextureCoordinate;\n" +
			"varying vec2 bottomLeftTextureCoordinate;\n" +
			"varying vec2 bottomRightTextureCoordinate;\n" +
			"\n" +
			"uniform sampler2D inputImageTexture;\n" +
			"\n" +
			"void main()\n" +
			"{\n" +
			"float bottomLeftIntensity = texture2D(inputImageTexture, bottomLeftTextureCoordinate).r;\n" +
			"float topRightIntensity = texture2D(inputImageTexture, topRightTextureCoordinate).r;\n" +
			"float topLeftIntensity = texture2D(inputImageTexture, topLeftTextureCoordinate).r;\n" +
			"float bottomRightIntensity = texture2D(inputImageTexture, bottomRightTextureCoordinate).r;\n" +
			"float leftIntensity = texture2D(inputImageTexture, leftTextureCoordinate).r;\n" +
			"float rightIntensity = texture2D(inputImageTexture, rightTextureCoordinate).r;\n" +
			"float bottomIntensity = texture2D(inputImageTexture, bottomTextureCoordinate).r;\n" +
			"float topIntensity = texture2D(inputImageTexture, topTextureCoordinate).r;\n" +
			"float h = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n" +
			"float v = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n" +
			"\n" +
			"float mag = 1.0 - length(vec2(h, v));\n" +
			"\n" +
			"gl_FragColor = vec4(vec3(mag), 1.0);\n" +
			"}\n";
}
