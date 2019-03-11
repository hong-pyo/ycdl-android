package com.hong2.ycdl.speak;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hong2.ycdl.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeakActivity extends Activity {

    private SpeechRecognizer mRecognizer;
    private Intent intent;
    private TextView speak_view, policy_text_view;
    private Button voice_btn;
    private LinearLayout backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);
        policy_text_view = findViewById(R.id.audio_policy);
        speak_view = findViewById(R.id.speak_view_text);
        backgroundColor = findViewById(R.id.speak_background_image_view);
        voice_btn = findViewById(R.id.speak_voice_btn);

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Pattern pattern = Pattern.compile("오디오 권한 정책");

        Linkify.addLinks(policy_text_view, pattern, "http://52.78.131.64:8080/policy/audio", null, transformFilter);

        voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

                mRecognizer = SpeechRecognizer.createSpeechRecognizer(SpeakActivity.this);
                mRecognizer.setRecognitionListener(listener);
                mRecognizer.startListening(intent);
            }
        });
    }
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            pinkColor();
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
            blueColor();
        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {
            setSpeakText(results);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    };

    private void setSpeakText(Bundle results) {
        String key= SpeechRecognizer.RESULTS_RECOGNITION;
        ArrayList<String> mResult = results.getStringArrayList(key);
        String[] rs = new String[mResult.size()];
        mResult.toArray(rs);
        speak_view.setText(rs[0]);
    }

    public void pinkColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundColor.setBackground(getResources().getDrawable(R.drawable.backpink));
            speak_view.setText("말하세요");
        }
    }

    public void blueColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundColor.setBackground(getResources().getDrawable(R.drawable.blueback));
        }
    }
}
