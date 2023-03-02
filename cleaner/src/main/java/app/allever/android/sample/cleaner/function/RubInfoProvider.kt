package app.allever.android.sample.cleaner.function;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//获取某个后缀的垃圾文件
//获取名称包含（download、temp）等的文件，并删除其中内容
//获取长度为0的文件，或者没有任何子目录的文件夹，并删除

public class RubInfoProvider {

    public List<File> getLogInfo() {
        ArrayList<File> rubList = new ArrayList<>();

        // 扫描sd卡目录中的log【应用日志】
        File sdCard = Environment.getExternalStorageDirectory();

        List<File> logFiles = GetFiles(sdCard, ".log");
        List<File> tempFiles = GetFiles(sdCard, ".temp");
        List<File> tmpFiles = GetFiles(sdCard, ".tmp");

        rubList.addAll(logFiles);
        rubList.addAll(tempFiles);
        rubList.addAll(tmpFiles);

        return rubList;
    }

    public List<ApkInfo> getApkInfo(Context context) {
        List<ApkInfo> apkInfoList = new ArrayList<>();

        //扫描APK【扫描sd卡所有目录】
        File sdCard = Environment.getExternalStorageDirectory();
        List<File> apkFiles = GetFiles(sdCard, ".apk");

        if (apkFiles != null) {
            //遍历apk文件的集合
            for (File file : apkFiles) {
                ApkInfo apkInfo = new ApkInfo();

                String apkPath = file.getAbsolutePath();

                apkInfo.path = apkPath;
                apkInfo.size = file.length();

                PackageManager pm = context.getPackageManager();
                PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
                if (packageInfo != null) {
                    ApplicationInfo appInfo = packageInfo.applicationInfo;

                    appInfo.sourceDir = apkPath;
                    appInfo.publicSourceDir = apkPath;

                    apkInfo.icon = appInfo.loadIcon(pm);
                    apkInfo.packageName = appInfo.packageName;
                    apkInfo.name = appInfo.loadLabel(pm).toString();

                    apkInfoList.add(apkInfo);
                }

            }
        }

        return apkInfoList;
    }


    private List<File> fileList = new ArrayList<>();

    public List<File> GetFiles(File file, String extension) {
        //在某个目录下查找某个后缀结尾的文件路径
        if (file.isFile()) {
            String fileName = file.getName();
            //判断后缀
            if (fileName.toLowerCase().endsWith(extension)) {
                fileList.add(file);
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles(); //获取下级子目录

            if (files != null && files.length > 0) {
                for (File sFile : files) {
                    GetFiles(sFile, extension);
                }
            }
        }
        return fileList;
    }
}