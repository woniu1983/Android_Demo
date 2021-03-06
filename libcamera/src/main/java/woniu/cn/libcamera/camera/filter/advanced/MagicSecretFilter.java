package woniu.cn.libcamera.camera.filter.advanced;

import java.util.ArrayList;
import java.util.List;

import woniu.cn.libcamera.camera.filter.base.MagicBaseGroupFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageBrightnessFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageContrastFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageExposureFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageHueFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSaturationFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSharpenFilter;

public class MagicSecretFilter extends MagicBaseGroupFilter {

	public MagicSecretFilter() {
		super(initFilters());
	}
	
	private static List<GPUImageFilter> initFilters(){
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();
		filters.add(new GPUImageContrastFilter());
		filters.add(new GPUImageBrightnessFilter());
		filters.add(new GPUImageExposureFilter());
		filters.add(new GPUImageHueFilter());
		filters.add(new GPUImageSaturationFilter());
		filters.add(new GPUImageSharpenFilter());
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
}
