package app.allever.android.sample.function.im.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import app.allever.android.sample.function.R;

/**
 * 底部编辑弹框
 */
public class InputBarDialog extends Dialog {

    private final InputBar inputBar;
    boolean showEmo;

    public InputBarDialog(Context context, boolean showEmo, String defaultContent, InputBar.InputBarListener inputBarListener) {
        super(context, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);
        this.showEmo = showEmo;
        inputBar = new InputBar(context);
        inputBar.setInputContent(defaultContent);
        inputBar.setInputBarListener(new InputBar.InputBarListener() {
            @Override
            public void onClickSend(String message) {
                dismiss();
                if (inputBarListener != null) {
                    inputBarListener.onClickSend(message);
                }
            }

            @Override
            public void onClickAdd() {
                if (inputBarListener != null) {
                    inputBarListener.onClickAdd();
                }
            }

            @Override
            public boolean onClickEmoji() {
                if (inputBarListener != null) {
                    return inputBarListener.onClickEmoji();
                }
                return false;
            }

            @Override
            public void inputTextChanged(String content) {
                if (inputBarListener != null) {
                    inputBarListener.inputTextChanged(content);
                }
            }
        });
        setContentView(inputBar);
        Window window = getWindow();
        if (window != null) {
            //获取对话框当前的参数值
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    @Override
    public void dismiss() {
        inputBar.hideInputBar();
        super.dismiss();
    }

    @Override
    public void show() {
        inputBar.showInputBar(showEmo);
        super.show();
    }
}