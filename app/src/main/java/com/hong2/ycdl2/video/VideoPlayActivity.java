package com.hong2.ycdl2.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.hong2.ycdl2.R;
import com.hong2.ycdl2.common.global.IntentConstant;
import com.hong2.ycdl2.common.user.UserInfo;
import com.hong2.ycdl2.util.VideoUtil;
import com.hong2.ycdl2.common.widget.KakaoToast;
import com.hong2.ycdl2.util.HongStringUtil;

public class VideoPlayActivity extends YouTubeBaseActivity {
    /* Xml Control */
    private Button manageButton;
    private Button playButton;
    private Button checkAnswerButton;
    private YouTubePlayerView youTubeView;
    private YouTubePlayer.OnInitializedListener listener;
    private TextView videoCategoryTextView;
    private TextView answerResultTextView;
    private EditText answerEditView;

    private VideoContent videoContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        videoContent = VideoUtil.getContent(getIntent());
        VideoCategory videoCategory = VideoUtil.getCategory(getIntent());

        /**
         *  xml view
         * */
        manageButton = findViewById(R.id.manage_button);
        videoCategoryTextView = findViewById(R.id.video_text_View);
        playButton = findViewById(R.id.video_play_Button);
        youTubeView = findViewById(R.id.youTubeView);
        answerEditView = findViewById(R.id.video_text_edit);
        checkAnswerButton = findViewById(R.id.check_answer_button);
        answerResultTextView = findViewById(R.id.video_answer_textview);

        videoCategoryTextView.setText(videoCategory.getDisplayTitle());

        InitVideoView();
        goToManage(videoCategory);

        playButton.setVisibility(View.GONE);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEditView.getText().toString();
                answerResultTextView.setText(HongStringUtil.howSimilarity(videoContent.getAnswer(), answer));
            }
        });
    }

    private void InitVideoView() {
        if (videoContent != null) {
            initVideoLink(videoContent.getLink());
            youTubeView.initialize(String.valueOf(R.string.youtube_key), listener);
        }
    }

    private void initVideoLink(final String link) {
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cuePlaylist(link);
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                KakaoToast.makeToast(getApplicationContext(), "init Video Fail", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void goToManage(final VideoCategory category) {
        if (UserInfo.checkManage()) {
            manageButton.setVisibility(View.VISIBLE);
        }
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageMode = new Intent(getApplicationContext(), VideoManageActivity.class);
                manageMode.putExtra(IntentConstant.VIDEO.CATEGORY, category);
                startActivity(manageMode);
            }
        });
    }
}
