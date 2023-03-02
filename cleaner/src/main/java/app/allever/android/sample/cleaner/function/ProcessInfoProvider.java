package app.allever.android.sample.cleaner.function;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import app.allever.android.lib.core.ext.LoggerKt;
import app.allever.android.sample.cleaner.R;


public class ProcessInfoProvider {

    //获取正在运行的进程总数
    public static int getProcessCount(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        return runningAppProcesses.size();
    }

    //获取可用内存空间大小，单位byte
    public static long getAvailSpace(Context ctx) {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    //获取全部内存空间大小，单位byte
    public static long getTotalSpace() {
        try {
            FileReader fileReader = new FileReader("proc/meminfo");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readLine = bufferedReader.readLine();
            char[] charArray = readLine.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : charArray) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                }
            }
            bufferedReader.close();

            return Long.parseLong(sb.toString()) * 1024;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //获取所有正在运行的进程的集合
    public static List<ProcessInfo> getProcessList(Context context) {
        List<ProcessInfo> processInfoList = new ArrayList<>();

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();

        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {



                    ProcessInfo processInfo = new ProcessInfo();

                    processInfo.packageName = runningAppProcessInfo.processName;

                    android.os.Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
                    android.os.Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
                    processInfo.itemSize = memoryInfo.getTotalPrivateDirty() * 1024;  //单位byte

                    try {
                        ApplicationInfo applicationInfo = pm.getApplicationInfo(processInfo.packageName, 0);
                        processInfo.itemName = applicationInfo.loadLabel(pm).toString();
                        processInfo.itemIcon = applicationInfo.loadIcon(pm);

                        //判读是否系统应用
                        if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM){
                            processInfo.isSystem = true;
                        }else{
                            processInfo.isSystem = false;
                        }

                    } catch (NameNotFoundException e) {
                        e.printStackTrace();
                        processInfo.itemName = processInfo.packageName;
                        processInfo.itemIcon = context.getResources().getDrawable(R.drawable.ic_back);
                    }

                    if (!processInfo.isSystem){
                        processInfoList.add(processInfo);

                        LoggerKt.log("================" + processInfoList.size());
                    }
                }
            }
        return processInfoList;
    }

    //杀死进程
    public static void killProcess(Context context) {
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runProcessList = activityManger.getRunningAppProcesses();
        if (runProcessList != null) {
            for (RunningAppProcessInfo runnProcessInfo : runProcessList) {
                if (runnProcessInfo.importance > RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    String packageName = runnProcessInfo.processName;
                    if (packageName.equals(context.getPackageName())) {
                        continue;
                    }
                    activityManger.killBackgroundProcesses(packageName);
                }
            }
        }

    }
}