package com.mervynlam.calctwentyfour;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Mervynlam on 2017/8/7.
 */

public class GetVersion {
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName + "(" + info.versionCode + ")";
			return version;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
