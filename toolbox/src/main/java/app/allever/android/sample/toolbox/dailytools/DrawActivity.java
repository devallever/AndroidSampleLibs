package app.allever.android.sample.toolbox.dailytools;

import static app.allever.android.sample.toolbox.util.CommonUtils.LoadingDialog;
import static app.allever.android.sample.toolbox.util.CommonUtils.SaveImage;
import static app.allever.android.sample.toolbox.util.CommonUtils.loadDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tapadoo.alerter.Alerter;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.allever.android.lib.common.BaseActivity;
import app.allever.android.lib.core.util.StatusBarCompat;
import app.allever.android.lib.mvvm.base.BaseViewModel;
import app.allever.android.sample.toolbox.R;
import app.allever.android.sample.toolbox.databinding.ActivityDrawBinding;
import app.allever.android.sample.toolbox.widget.PaletteView;

public class DrawActivity extends BaseActivity<ActivityDrawBinding, BaseViewModel> {

    private String hbcolor = "#FF000000";
    private int hbdx = 6;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_draw, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        final String title = (String) menuItem.getTitle();
        if (title.equals("画笔颜色")) {
            ColorPickerDialogBuilder
                    .with(DrawActivity.this)
                    .setTitle("画笔颜色")
                    .initialColor(Color.parseColor(hbcolor))
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener(selectedColor -> {
                    })
                    .setPositiveButton("确定", (dialog, selectedColor, allColors) -> {
                        hbcolor = "#" + Integer.toHexString(selectedColor);
                        binding.paletteView.setPenColor(selectedColor);
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                    })
                    .showColorEdit(true)
                    .showAlphaSlider(false)
                    .setColorEditTextColor(getResources().getColor(R.color.editTextColor))
                    .build()
                    .show();
        }
        if (title.equals("画笔大小")) {
            final AlertDialog mDialog = new AlertDialog.Builder(DrawActivity.this)
                    .setPositiveButton("确定", null)
                    .setNegativeButton("取消", null)
                    .create();
            mDialog.setTitle("画笔大小");
            final View contentView = getLayoutInflater().inflate(R.layout.dialog_hbdx, null);
            mDialog.setView(contentView);
            final DiscreteSeekBar discreteSeekBar = contentView.findViewById(R.id.discreteSeekBar);
            discreteSeekBar.setProgress(hbdx);
            mDialog.setOnShowListener(dialog -> {
                Button positiveButton = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveButton.setOnClickListener(v -> {
                    mDialog.dismiss();
                    hbdx = discreteSeekBar.getProgress();
                    binding.paletteView.setPenRawSize(hbdx);
                });
                negativeButton.setOnClickListener(v -> mDialog.dismiss());
            });
            mDialog.show();
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.width = getResources().getDisplayMetrics().widthPixels / 10 * 9;
            mDialog.getWindow().setAttributes(layoutParams);
        }
        if (title.equals("保存为图片")) {
            LoadingDialog(DrawActivity.this);
            new Thread((Runnable) () -> {
                @SuppressLint("SimpleDateFormat")
                String savedFile = SaveImage(DrawActivity.this, binding.paletteView.buildBitmap(), "/噬心工具箱/简易画板/", "Image-" + new SimpleDateFormat("HH-mm-ss").format(new Date()) + ".png");
                if (savedFile != null) {
                    MediaScannerConnection.scanFile((Activity) DrawActivity.this, new String[]{savedFile}, null, (MediaScannerConnection.OnScanCompletedListener) (str, uri) -> {
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(uri);
                        ((Activity) DrawActivity.this).sendBroadcast(intent);
                        loadDialog.dismiss();
                        Alerter.create((Activity) DrawActivity.this)
                                .setTitle("保存成功")
                                .setText("已保存到" + savedFile)
                                .setBackgroundColorInt(getResources().getColor(R.color.success))
                                .show();
                    });
                } else {
                    loadDialog.dismiss();
                }
            }).start();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void init() {
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"));
        StatusBarCompat.changeToLightStatusBar(this);
        binding.toolbar.setTitle("简易画板");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.card3.setCardBackgroundColor(getResources().getColor(R.color.SelectedBackColor));
        binding.card1.setOnClickListener(v -> binding.paletteView.undo());
        binding.card2.setOnClickListener(v -> binding.paletteView.redo());
        binding.card3.setOnClickListener(v -> {
            binding.card3.setCardBackgroundColor(getResources().getColor(R.color.SelectedBackColor));
            binding.card4.setCardBackgroundColor(getResources().getColor(R.color.appbarColor));
            binding.paletteView.setMode(PaletteView.Mode.DRAW);
        });
        binding.card4.setOnClickListener(v -> {
            binding.card4.setCardBackgroundColor(getResources().getColor(R.color.SelectedBackColor));
            binding.card3.setCardBackgroundColor(getResources().getColor(R.color.appbarColor));
            binding.paletteView.setMode(PaletteView.Mode.ERASER);
        });
        binding.card5.setOnClickListener(v -> binding.paletteView.clear());
    }

    @NonNull
    @Override
    public ActivityDrawBinding inflateChildBinding() {
        return ActivityDrawBinding.inflate(getLayoutInflater());
    }

    @Override
    protected boolean showTopBar() {
        return false;
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }
}