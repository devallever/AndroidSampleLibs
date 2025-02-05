package app.allever.android.sample.microsoft.speech;

import android.util.Log;

import com.microsoft.cognitiveservices.speech.CancellationDetails;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.PropertyId;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SessionEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionEventArgs;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.util.EventHandler;

public class SpeechSdkHelper {
    private static final String TAG = SpeechSdkHelper.class.getSimpleName();

    private final String SPEECH_KEY = "7d5c059df6b84c42bb78e8b1d7eef047";
    private final String REGION = "centralus";

    private final SpeechConfig mSpeechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, REGION);

    private MicrophoneStream microphoneStream;

    private MicrophoneStream createMicrophoneStream() {
        this.releaseMicrophoneStream();

        microphoneStream = new MicrophoneStream();
        return microphoneStream;
    }

    private void releaseMicrophoneStream() {
        if (microphoneStream != null) {
            microphoneStream.close();
            microphoneStream = null;
        }
    }

    /**
     * 每次启动录音都要创建??
     */
//    private final SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, REGION);

    static class Holder {
        static final SpeechSdkHelper INS = new SpeechSdkHelper();
    }

    public static SpeechSdkHelper getIns() {
        return Holder.INS;
    }

    private SpeechSdkHelper() {
//        speechConfig.close();
    }

    public void recognizeOnceFromMicrophone(ResultCallback callback) {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_KEY, REGION);
        SpeechRecognizer recognizer = null;
        try {
            recognizer = new SpeechRecognizer(speechConfig);
            Log.d(TAG, "recognizeOnceFromMicrophone: ");
            // Starts recognition. It returns when the first utterance has been recognized.
            SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();
            // Checks result.
            if (result.getReason() == ResultReason.RecognizedSpeech) {
                String resultText = result.getText();
                Log.d(TAG, "recognizeOnceFromMicrophone: " + "RECOGNIZED: Text=" + resultText);
                callback.onResultText(resultText);
                result.close();
            } else if (result.getReason() == ResultReason.NoMatch) {
                log("NOMATCH: Speech could not be recognized.");
                callback.onError("NOMATCH: Speech could not be recognized.");
                result.close();
            } else if (result.getReason() == ResultReason.Canceled) {
                StringBuilder stringBuilder = new StringBuilder();
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                stringBuilder.append("CANCELED: Reason=" + cancellation.getReason()).append("\n");

                if (cancellation.getReason() == CancellationReason.Error) {
                    stringBuilder.append("CANCELED: ErrorCode=" + cancellation.getErrorCode()).append("\n");
                    stringBuilder.append("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    log("CANCELED: Did you update the subscription info?");
                }
                callback.onError(stringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        }

        if (recognizer != null) {
            recognizer.close();
            speechConfig.close();
        }

        speechConfig.close();

    }

    private SpeechRecognizer speechRecognizer;

    public void pronunciationAssessmentFromMicrophoneStream(ResultCallback callback) {

        try {
            final AudioConfig audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream());
            speechRecognizer = new SpeechRecognizer(mSpeechConfig, "en-US", audioConfig);
            PronunciationAssessmentConfig pronunciationAssessmentConfig = new PronunciationAssessmentConfig(
                    "",
                    PronunciationAssessmentGradingSystem.HundredMark,
                    PronunciationAssessmentGranularity.Phoneme);
            pronunciationAssessmentConfig.applyTo(speechRecognizer);

            speechRecognizer.recognizing.addEventListener(new EventHandler<SpeechRecognitionEventArgs>() {
                @Override
                public void onEvent(Object sender, SpeechRecognitionEventArgs e) {

                }
            });
            speechRecognizer.recognized.addEventListener(new EventHandler<SpeechRecognitionEventArgs>() {
                @Override
                public void onEvent(Object o, SpeechRecognitionEventArgs speechRecognitionResultEventArgs) {
                    StringBuilder stringBuilder = new StringBuilder();
                    final SpeechRecognitionResult speechRecognitionResult = speechRecognitionResultEventArgs.getResult();
                    final String s = speechRecognitionResult.getText();



                    final String resultJson = speechRecognitionResultEventArgs.getResult().getProperties().getProperty(PropertyId.SpeechServiceResponse_JsonResult);

                    stringBuilder.append("Final result received: " + resultJson).append("\n");
                    stringBuilder.append("text: " + s).append("\n");

                    PronunciationAssessmentResult pronResult = PronunciationAssessmentResult.fromResult(speechRecognitionResult);
                    stringBuilder.append("accuracy score: " + pronResult.getAccuracyScore()).append("\n");
                    stringBuilder.append("pronunciation score: " +  pronResult.getPronunciationScore()).append("\n");
                    stringBuilder.append("completeness score: " + pronResult.getCompletenessScore()).append("\n");
                    stringBuilder.append( "fluency score: " + pronResult.getFluencyScore());

                    speechRecognitionResult.close();

                    callback.onResultText(stringBuilder.toString());
                }
            });

            speechRecognizer.sessionStarted.addEventListener(new EventHandler<SessionEventArgs>() {
                @Override
                public void onEvent(Object sender, SessionEventArgs e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("sessionStarted");
                    callback.onResultText(stringBuilder.toString());
                }
            });

            speechRecognizer.sessionStopped.addEventListener(new EventHandler<SessionEventArgs>() {
                @Override
                public void onEvent(Object sender, SessionEventArgs e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    releaseMicrophoneStream();
                    stringBuilder.append("sessionStopped");
                    callback.onResultText(stringBuilder.toString());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        }

        speechRecognizer.startContinuousRecognitionAsync();
    }

    public void stopPronunciationAssessmentFromMicrophoneStream() {
        if (speechRecognizer != null) {
            speechRecognizer.stopContinuousRecognitionAsync();
        }
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
