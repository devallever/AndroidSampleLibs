package app.allever.android.sample.toolbox.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.allever.android.sample.toolbox.R;

public class CommonUtils {

    public static AlertDialog loadDialog;
    //加载弹窗
    public static void LoadingDialog(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch(Exception e) {
        }
        loadDialog = new MaterialAlertDialogBuilder(context)
                .create();
        final View contentView = View.inflate(context, R.layout.loading, null);
        loadDialog.setView(contentView);
        loadDialog.show();
        loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        WindowManager.LayoutParams layoutParams = loadDialog.getWindow().getAttributes();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels / 5 * 4;
        layoutParams.height = context.getResources().getDisplayMetrics().widthPixels / 5 * 4;
        loadDialog.getWindow().setAttributes(layoutParams);
    }

    //保存图片
    public static String SaveImage(Context context, Bitmap bitmap, String path, String name) {
        if (bitmap == null) {
            return null;
        }
        @SuppressLint("SimpleDateFormat")
        final String time = new SimpleDateFormat("HH-mm-ss").format(new Date());
        if (!FileUtil.isExistFile(FileUtil.getExternalStorageDir().concat(path))) {
            FileUtil.makeDir(FileUtil.getExternalStorageDir().concat(path));
        }
        File appDir = new File(FileUtil.getExternalStorageDir().concat(path));
        File file = new File(appDir, name);
        java.io.FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
