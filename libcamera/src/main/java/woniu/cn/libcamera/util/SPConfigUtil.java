package woniu.cn.libcamera.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;


/**
 * 程序配置数据，保存在SharedPreference中，清除缓存时不清除
 * @author Woniu
 *
 */
public class SPConfigUtil {
	static final String TAG = "SPConfigUtil";
	static final String SP_NAME = "SPConfigUtil";

	private static Context mContext;

	/**
	 * 必须在获取实例前调用
	 * -- 调用一次即可, 推荐在Application类中实现
	 * @param context
	 */
	public static void init(Context context) {
		mContext = context;
	}

	synchronized public static long loadLong(final String key, long defVal) {
		Long cf = defVal;
		String cfStr = load(key);
		try {
			cf = Long.parseLong(cfStr);
		} catch (Exception e) {

		}
		return cf;
	}
	synchronized public static int loadInt(final String key, int defVal) {
		Integer cf = defVal;
		String cfStr = load(key);
		try {
			cf = Integer.parseInt(cfStr);
		} catch (Exception e) {

		}
		return cf;
	}

	synchronized public static boolean loadBoolean(final String key,
			boolean defVal) {
		Boolean cf = defVal;
		String cfStr = load(key);
		try {
			if(!TextUtils.isEmpty(cfStr)){
				cf = Boolean.parseBoolean(cfStr);
			}
		} catch (Exception e) {

		}
		return cf;
	}

	synchronized public static String load(final String key) {
		String cf = null;
		if (mContext == null) {
			return cf;
		}
		try {
			SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			cf = sp.getString(key, null);
		} catch (Exception e) {
			// Log.e(TAG, e);
		}
		return cf;
	}

	synchronized public static void save(final String key, final String val) {
		if (mContext == null || val == null) {
			return;
		}
		try {
			SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString(key, val);
			edit.commit();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

	}

	synchronized public static void clear(final String key) {
		try {
			SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.remove(key);
			edit.commit();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}
	
	synchronized public static void clearAll() {
		try {
			SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.clear();
			edit.commit();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}
}
