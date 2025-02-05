package app.allever.android.sample.microsoft.speech;

public interface ResultCallback {
    default void onResultText(String text) { }
    default void onError(String errorText) {}
}
