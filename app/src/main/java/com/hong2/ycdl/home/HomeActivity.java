package com.hong2.ycdl.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.global.IntentConstant;
import com.hong2.ycdl.common.user.KakaoMeDto;
import com.hong2.ycdl.common.widget.KakaoToast;
import com.hong2.ycdl.speak.SpeakActivity;
import com.hong2.ycdl.util.HongGsonUtil;
import com.hong2.ycdl.video.ListenActivity;
import com.hong2.ycdl.video.dto.VideoCategoryDto;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.*;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import org.json.JSONObject;

import java.util.*;

import static com.hong2.ycdl.common.global.NetworkConstant.YCDL_SERVER_URL;

public class HomeActivity extends Activity {

    private RequestQueue queue;
    private Intent intent;
    private KakaoMeDto kakaoMeDto;
    
    private TextView titleBar;
    private Button btn1, btn2, btn3, btn4;
    private FeedTemplate feedTemplate;
    private VideoCategoryDto category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        titleBar = findViewById(R.id.welcome_textView);
        btn1 = findViewById(R.id.listen_menu_button);
        btn2 = findViewById(R.id.speak_menu_button);
        btn4 = findViewById(R.id.etc_menu_button);

        String welcomeUrl = YCDL_SERVER_URL + "/welcome";
        String signUpUrl = YCDL_SERVER_URL + "/kakao/sign-up";
        final String videoListUrl = YCDL_SERVER_URL + "/video/category/all";
        queue = Volley.newRequestQueue(this);

        intent = getIntent();
        kakaoMeDto = (KakaoMeDto) intent.getSerializableExtra(IntentConstant.MEMBER.KAKAO_REQUEST);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(requestVideoList(videoListUrl));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speakIntent = new Intent(getApplicationContext(), SpeakActivity.class);
                startActivity(speakIntent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KakaoLinkService.getInstance().sendDefault(getApplicationContext(), getKakaoLinkFeedType(),
                        null, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        result.getTemplateId();
                    }
                });
            }
        });

        StringRequest requestWelcomeParam = requestWelcome(welcomeUrl);
        if (kakaoMeDto.getHasSignedUp()) {
            String params = HongGsonUtil.getGsonString(kakaoMeDto);
            queue.add(requestSignUp(params, signUpUrl));
        }
        requestWelcomeParam.setTag("MAIN");
        queue.add(requestWelcomeParam);

    }

    private FeedTemplate getKakaoLinkFeedType() {
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("YCDL 알리기 ",
                        "http://k.kakaocdn.net/dn/4Gh9a/btqs1Q4Jn6u/zBCbKIpCaSxdqioDcZH8fk/kakaolink40_original.png",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("구화 학습 동영상")
                        .build())
                //.setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20).setSharedCount(30).setViewCount(40).build())
                //.addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                //.addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").setAndroidExecutionParams("key1=value1").setIosExecutionParams("key1=value1")                 .build()))
                .build();

        return params;
    }

    @NonNull
    private StringRequest requestVideoList(String videoListUrl) {
        return new StringRequest(Request.Method.GET, videoListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                category = HongGsonUtil.fromJson(response, VideoCategoryDto.class);

                Intent videoIntent = new Intent(getApplicationContext(), ListenActivity.class);
                videoIntent.putExtra(IntentConstant.VIDEO.VIDEO_LIST,  category);
                startActivity(videoIntent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                KakaoToast.makeToast(getApplicationContext(), "video category get fail", Toast.LENGTH_LONG).show();
            }
        });
    }

   @NonNull
   private JsonObjectRequest requestSignUp(String params, final String url) {
        return new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
   }

    @NonNull
    private StringRequest requestWelcome(final String url) {
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                titleBar.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                KakaoToast.makeToast(getApplicationContext(), "welcome request fail", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String nickName = "";
                if (kakaoMeDto.getNickName() != null) {
                    nickName = kakaoMeDto.getNickName();
                }
                params.put("memberNickName", nickName);
                return params;
            }
        };
    }
}
