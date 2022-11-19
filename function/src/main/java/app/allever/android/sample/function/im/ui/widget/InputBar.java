package app.allever.android.sample.function.im.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vanniktech.emoji.EmojiPopup;

import app.allever.android.lib.core.helper.DisplayHelper;
import app.allever.android.lib.core.util.KeyboardUtil;
import app.allever.android.lib.core.util.ResUtils;
import app.allever.android.lib.core.util.SoftKeyboardUtils;
import app.allever.android.lib.widget.ClickListener;
import app.allever.android.sample.function.R;


public class InputBar extends LinearLayout {

    private EditText etInput;
    private ImageView ivEmoji;
    private ImageView ivBottomView;
    private ImageView ivAdd;
    private TextView tvSend;
    private InputBarListener inputBarListener;

    /**
     * emoji选择框
     */
    private EmojiPopup mEmojiPopup;

    public InputBar(Context context) {
        this(context, null);
    }

    public InputBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_group_inputbar, this);
        initView();
    }


    public void initView() {
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(ResUtils.getColor(R.color.white));
        this.setMinimumHeight(DisplayHelper.INSTANCE.dip2px(50));
        this.setGravity(Gravity.CENTER_VERTICAL);
//        this.setPadding(UiUtils.dp2px(12), UiUtils.dp2px(7), UiUtils.dp2px(12), UiUtils.dp2px(7));
        etInput = (EditText) findViewById(R.id.et_input);
        ivEmoji = (ImageView) findViewById(R.id.iv_emoji);
        ivBottomView = (ImageView) findViewById(R.id.ivBottomView);
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        tvSend = (TextView) findViewById(R.id.tv_send);

        mEmojiPopup = EmojiPopup
                .Builder
                .fromRootView(this)
                .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
                .setOnEmojiPopupShownListener(() -> {
                    ivEmoji.setImageResource(R.drawable.ic_keybroad);
                })
                .setOnEmojiPopupDismissListener(() -> {
                    ivEmoji.setImageResource(R.drawable.bottom_input_emo);
                }).build(etInput);
        ivEmoji.setOnClickListener(v -> {
            ivBottomView.setVisibility(View.GONE);
            if (inputBarListener != null) {
                boolean intercept = inputBarListener.onClickEmoji();
                if (!intercept) {
                    mEmojiPopup.toggle();
                }
            }
        });
        ivAdd.setOnClickListener(view -> {
            KeyboardUtil.openKeyboard(getContext(), etInput);
            if (inputBarListener != null) {
                inputBarListener.onClickAdd();
            }
//            if (ivBottomView.getVisibility() == View.VISIBLE) {
//                ivBottomView.setVisibility(View.GONE);
//                KeyboardUtil.openKeyboard(getContext(),etInput);
//            } else {
//                closeKeybord(etInput,getContext());
//                postDelayed(() -> {
//                    ivBottomView.setVisibility(View.VISIBLE);
//                },200);
//            }
        });
        tvSend.setOnClickListener(new ClickListener() {
            @Override
            public void click(View v) {
                send();
            }
        });
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    tvSend.setVisibility(View.VISIBLE);
                    ivAdd.setVisibility(View.GONE);
                } else {
                    tvSend.setVisibility(View.GONE);
//                    ivAdd.setVisibility(View.VISIBLE);
                }
                if (inputBarListener != null) {
                    inputBarListener.inputTextChanged(editable.length() > 0 ? editable.toString() : "");
                }
            }
        });
        etInput.setOnEditorActionListener((v, actionId, event) -> {
            send();
            return false;
        });
    }

    public void send() {
        String message = "";
        if (etInput.getText() != null) {
            message = etInput.getText().toString().trim();
        }
        etInput.setText("");

        if (inputBarListener != null) {
            inputBarListener.onClickSend(message);
        }
    }

    public void showInputBar(boolean showEmo) {
        this.setVisibility(VISIBLE);
        if (showEmo) {
            ivEmoji.postDelayed(() -> {
                ivEmoji.performClick();
            }, 200);
        } else {
            etInput.requestFocus();
            SoftKeyboardUtils.showSoftKeyboard(etInput, 200);
        }
    }

    public void hideInputBar() {
//        mEmojiPopup.dismiss();
        this.setVisibility(GONE);
        etInput.clearFocus();
        SoftKeyboardUtils.hideSoftKeyboard(etInput);
    }

    public void setInputContent(String content) {
        etInput.setText(content);
    }

    public void setInputBarListener(InputBarListener inputBarListener) {
        this.inputBarListener = inputBarListener;
    }

    //关闭软键盘
    public void closeKeybord(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public interface InputBarListener {

        void onClickSend(String message);

        void onClickAdd();

        boolean onClickEmoji();

        void inputTextChanged(String content);
    }


}
