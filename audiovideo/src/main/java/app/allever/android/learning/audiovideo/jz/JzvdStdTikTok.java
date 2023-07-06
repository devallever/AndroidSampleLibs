package app.allever.android.learning.audiovideo.jz;

import static cn.jzvd.JZDataSource.URL_KEY_DEFAULT;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.danikula.videocache.HttpProxyCacheServer;

import app.allever.android.learning.audiovideo.R;
import app.allever.android.learning.audiovideo.tiktok.MyFileNameGenerator;
import app.allever.android.lib.core.app.App;
import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

public class JzvdStdTikTok extends JzvdStd {
    public JzvdStdTikTok(Context context) {
        super(context);
    }

    public JzvdStdTikTok(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
        bottomProgressBar.setVisibility(GONE);
        posterImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }


    //changeUiTo 真能能修改ui的方法
    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
    }

    @Override
    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro,
                                        int posterImg, int bottomPro, int retryLayout) {
        topContainer.setVisibility(INVISIBLE);
        bottomContainer.setVisibility(INVISIBLE);
        startButton.setVisibility(startBtn);
        loadingProgressBar.setVisibility(loadingPro);
        posterImageView.setVisibility(posterImg);
        bottomProgressBar.setVisibility(GONE);
        mRetryLayout.setVisibility(retryLayout);
    }

    @Override
    public void dissmissControlView() {
        if (state != STATE_NORMAL
                && state != STATE_ERROR
                && state != STATE_AUTO_COMPLETE) {
            post(() -> {
                bottomContainer.setVisibility(View.INVISIBLE);
                topContainer.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss();
                }
                if (screen != SCREEN_TINY) {
                    bottomProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        Log.i(TAG, "click blank");
        startButton.performClick();
        bottomContainer.setVisibility(GONE);
        topContainer.setVisibility(GONE);
    }

    public void updateStartImage() {
        if (state == STATE_PLAYING) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.ic_play);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_ERROR) {
            startButton.setVisibility(INVISIBLE);
            replayTextView.setVisibility(GONE);
        } else if (state == STATE_AUTO_COMPLETE) {
            startButton.setVisibility(VISIBLE);
            startButton.setImageResource(R.drawable.ic_play);
            replayTextView.setVisibility(VISIBLE);
        } else {
            startButton.setImageResource(R.drawable.ic_play);
            replayTextView.setVisibility(GONE);
        }
    }

    @Override
    public void setUp(String url, String title, int screen, Class mediaInterfaceClass) {
        if (url.startsWith("http")) {
            HttpProxyCacheServer proxy = getProxy(App.context);
            String proxyUrl = proxy.getProxyUrl(url);
            super.setUp(proxyUrl, title, screen, mediaInterfaceClass);
        } else {
            super.setUp(url,  title, screen, mediaInterfaceClass);
        }
    }

    //【Android 进阶】仿抖音系列之列表播放视频（三）
    //https://www.jianshu.com/p/15a70f242c4d
    @Override
    public void setUp(JZDataSource jzDataSource, int screen, Class mediaInterfaceClass) {
        String url = (String) jzDataSource.urlsMap.get(URL_KEY_DEFAULT);
        if (url != null && url.startsWith("http")) {
            HttpProxyCacheServer proxy = getProxy(App.context);
            String proxyUrl = proxy.getProxyUrl(url);
            jzDataSource.urlsMap.put(URL_KEY_DEFAULT, proxyUrl);
        }
        super.setUp(jzDataSource, screen, mediaInterfaceClass);
    }

    private static HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }

    private static HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(App.context)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .fileNameGenerator(new MyFileNameGenerator())
                .build();
    }
}
