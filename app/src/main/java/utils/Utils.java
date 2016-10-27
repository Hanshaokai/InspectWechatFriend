package utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by ${han} on 2016/10/27.
 */

public class Utils {
    /**
     *
     *获取微信的版本号
     */
    public static String getVersion(Context context){

        PackageManager packageManager=context.getPackageManager();
        List<PackageInfo> packageInfoList=packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo:packageInfoList){
            if ("com.tencent.m".equals(packageInfo.packageName)){

                return  packageInfo.versionName;
            }

        }
        return  "5.3.25";
    }

}
