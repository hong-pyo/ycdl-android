package com.hong2.ycdl.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.global.IntentConstant;
import com.hong2.ycdl.common.global.RCodeContant;
import com.hong2.ycdl.common.user.UserInfo;
import com.hong2.ycdl.common.util.VideoUtil;
import com.hong2.ycdl.common.widget.KakaoToast;
import com.hong2.ycdl.util.HongGsonUtil;
import com.hong2.ycdl.video.dto.VideoContentDto;

import static com.hong2.ycdl.common.global.NetworkConstant.YCDL_SERVER_URL;

public class VideoPlayActivity extends YouTubeBaseActivity {
    /* Xml Control */
    private Button manageButton;
    private Button playButton;
    private YouTubePlayerView youTubeView;
    private YouTubePlayer.OnInitializedListener listener;
    private
    private TextView videoCategory;
    private EditText answerEditView;

    private RequestQueue queue;
    private String videoUrl;
    private VideoContentDto contentDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        final VideoCategory category = VideoUtil.getCategory(getIntent());
        /**
         *  xml view
         * */
        manageButton = findViewById(R.id.manage_button);
        videoCategory = findViewById(R.id.video_text_View);
        playButton = findViewById(R.id.video_play_Button);
        videoCategory.setText(category.getDisplayTitle());
        youTubeView = findViewById(R.id.youTubeView);
        answerEditView = findViewById(R.id.video_text_edit);

        queue = Volley.newRequestQueue(this);
        videoUrl = YCDL_SERVER_URL + "/video/link/";


        goToManage(category);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest videoRequest = getVideoLink(videoUrl + category.getIdx());
                videoRequest.setTag("VIDEO_LINK");
                queue.add(videoRequest);
            }
        });
    }

    @NonNull
    private YouTubePlayer.OnInitializedListener getInitVideoListener(final String link) {
        return new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(link);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
    }

    @NonNull
    private StringRequest getVideoLink(String url) {
        return new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                contentDto = HongGsonUtil.fromJson(response, VideoContentDto.class);
                if (hasVideoLink(contentDto)) {
                    YouTubePlayer.OnInitializedListener listener = getInitVideoListener(contentDto.getrData().getLink());

                    youTubeView.initialize(String.valueOf(R.string.youtube_key), listener);
                }
                queue.cancelAll("VIDEO_LINK");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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

    private boolean hasVideoLink(VideoContentDto videoContent) {
        return RCodeContant.CODE.SUCCESS.equals(videoContent.getrCode()) && videoContent.getrData() != null;
    }

    private void goToManage(final VideoCategory category) {
        if (UserInfo.getIdx() == 967392860L) {
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
