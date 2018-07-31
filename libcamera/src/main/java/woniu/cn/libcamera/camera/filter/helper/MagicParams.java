package woniu.cn.libcamera.camera.filter.helper;

import android.content.Context;
import android.os.Environment;

import woniu.cn.libcamera.widget.MagicBaseView;

/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicParams {
    public static Context context;
    public static MagicBaseView magicBaseView;

    public static String videoPath = Environment.getExternalStorageDirectory().getPath();
    public static String videoName = "MagicCamera_test.mp4";

    public static int beautyLevel = 0; // TODO Mao 修改为 默认不接受美化, 原始默认值是5

    public MagicParams() {

    }
}
