package woniu.cn.libcamera.camera.filter.helper;

import woniu.cn.libcamera.camera.filter.advanced.MagicAmaroFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicAntiqueFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicBlackCatFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicBrannanFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicBrooklynFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicCalmFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicCoolFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicCrayonFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicEarlyBirdFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicEmeraldFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicEvergreenFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicFairytaleFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicFreudFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicHealthyFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicHefeFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicHudsonFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicImageAdjustFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicInkwellFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicKevinFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicLatteFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicLomoFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicN1977Filter;
import woniu.cn.libcamera.camera.filter.advanced.MagicNashvilleFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicNostalgiaFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicPixarFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicRiseFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicRomanceFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSakuraFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSierraFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSketchFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSkinWhitenFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSunriseFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSunsetFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSutroFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicSweetsFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicTenderFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicToasterFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicValenciaFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicWaldenFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicWarmFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicWhiteCatFilter;
import woniu.cn.libcamera.camera.filter.advanced.MagicXproIIFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageBrightnessFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageContrastFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageExposureFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageHueFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSaturationFilter;
import woniu.cn.libcamera.camera.filter.base.gpuimage.GPUImageSharpenFilter;

public class MagicFilterFactory{
	
	private static MagicFilterType filterType = MagicFilterType.NONE;
	
	public static GPUImageFilter initFilters(MagicFilterType type){
		filterType = type;
		switch (type) {
		case WHITECAT:
			return new MagicWhiteCatFilter();
		case BLACKCAT:
			return new MagicBlackCatFilter();
		case SKINWHITEN:
			return new MagicSkinWhitenFilter();
		case ROMANCE:
			return new MagicRomanceFilter();
		case SAKURA:
			return new MagicSakuraFilter();
		case AMARO:
			return new MagicAmaroFilter();
		case WALDEN:
			return new MagicWaldenFilter();
		case ANTIQUE:
			return new MagicAntiqueFilter();
		case CALM:
			return new MagicCalmFilter();
		case BRANNAN:
			return new MagicBrannanFilter();
		case BROOKLYN:
			return new MagicBrooklynFilter();
		case EARLYBIRD:
			return new MagicEarlyBirdFilter();
		case FREUD:
			return new MagicFreudFilter();
		case HEFE:
			return new MagicHefeFilter();
		case HUDSON:
			return new MagicHudsonFilter();
		case INKWELL:
			return new MagicInkwellFilter();
		case KEVIN:
			return new MagicKevinFilter();
		case LOMO:
			return new MagicLomoFilter();
		case N1977:
			return new MagicN1977Filter();
		case NASHVILLE:
			return new MagicNashvilleFilter();
		case PIXAR:
			return new MagicPixarFilter();
		case RISE:
			return new MagicRiseFilter();
		case SIERRA:
			return new MagicSierraFilter();
		case SUTRO:
			return new MagicSutroFilter();
		case TOASTER2:
			return new MagicToasterFilter();
		case VALENCIA:
			return new MagicValenciaFilter();
		case XPROII:
			return new MagicXproIIFilter();
		case EVERGREEN:
			return new MagicEvergreenFilter();
		case HEALTHY:
			return new MagicHealthyFilter();
		case COOL:
			return new MagicCoolFilter();
		case EMERALD:
			return new MagicEmeraldFilter();
		case LATTE:
			return new MagicLatteFilter();
		case WARM:
			return new MagicWarmFilter();
		case TENDER:
			return new MagicTenderFilter();
		case SWEETS:
			return new MagicSweetsFilter();
		case NOSTALGIA:
			return new MagicNostalgiaFilter();
		case FAIRYTALE:
			return new MagicFairytaleFilter();
		case SUNRISE:
			return new MagicSunriseFilter();
		case SUNSET:
			return new MagicSunsetFilter();
		case CRAYON:
			return new MagicCrayonFilter();
		case SKETCH:
			return new MagicSketchFilter();
		//image adjust
		case BRIGHTNESS:
			return new GPUImageBrightnessFilter();
		case CONTRAST:
			return new GPUImageContrastFilter();
		case EXPOSURE:
			return new GPUImageExposureFilter();
		case HUE:
			return new GPUImageHueFilter();
		case SATURATION:
			return new GPUImageSaturationFilter();
		case SHARPEN:
			return new GPUImageSharpenFilter();
		case IMAGE_ADJUST:
			return new MagicImageAdjustFilter();
		default:
			return null;
		}
	}
	
	public MagicFilterType getCurrentFilterType(){
		return filterType;
	}
}
