package com.hong2.ycdl2.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.hong2.ycdl2.BuildConfig;
import com.hong2.ycdl2.R;
import com.hong2.ycdl2.common.global.IntentConstant;
import com.hong2.ycdl2.common.user.KakaoMeDto;
import com.hong2.ycdl2.common.widget.KakaoToast;
import com.hong2.ycdl2.speak.SpeakActivity;
import com.hong2.ycdl2.util.HongGsonUtil;
import com.hong2.ycdl2.video.ListenActivity;
import com.hong2.ycdl2.video.dto.VideoCategoryDto;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.*;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.hong2.ycdl2.common.global.NetworkConstant.YCDL_SERVER_URL;

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
        btn3 = findViewById(R.id.temp_button);
        btn4 = findViewById(R.id.etc_menu_button);

        String welcomeUrl = YCDL_SERVER_URL + "/welcome";
        String signUpUrl = YCDL_SERVER_URL + "/kakao/sign-up";
        final String videoListUrl = YCDL_SERVER_URL + "/video/category/all";
        final String linkMessageURl = YCDL_SERVER_URL + "/kakao/link";
        final String alertMessageUrl = YCDL_SERVER_URL + "/alertMessage";
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

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String params = HongGsonUtil.getGsonString(Double.valueOf(BuildConfig.VERSION_NAME));
                queue.add(requestAlertMessage(params, alertMessageUrl));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kakaoMeDto.getHasSignedUp()) {
                    String params = HongGsonUtil.getGsonString(kakaoMeDto);
                    queue.add(requestLinkMessage(params, linkMessageURl));
                }
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

    private FeedTemplate getKakaoLinkFeedType(LinkMessage linkMessage) {
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(linkMessage.getTitle(),
                        linkMessage.getImageUrl(),
                        LinkObject.newBuilder()
                                .setWebUrl(linkMessage.getWebUrl())
                                .setMobileWebUrl(linkMessage.getAppUrl()).build())
                        .setDescrption(linkMessage.getDescription())
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

    private JsonObjectRequest requestAlertMessage(String params, final String url) {
        return new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("rData");
                    AlertMessage alertMessage = HongGsonUtil.fromJson(result, AlertMessage.class);
                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                    alertDialog.setTitle(alertMessage.getTitle());
                    alertDialog.setMessage(alertMessage.getMessage());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @NonNull
    private JsonObjectRequest requestLinkMessage(String params, final String url) {
        return new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("rData");
                    LinkMessage linkMessage = HongGsonUtil.fromJson(result, LinkMessage.class);
                    KakaoLinkService.getInstance().sendDefault(getApplicationContext(), getKakaoLinkFeedType(linkMessage),
                            null, new ResponseCallback<KakaoLinkResponse>() {
                                @Override
                                public void onFailure(ErrorResult errorResult) {

                                }

                                @Override
                                public void onSuccess(KakaoLinkResponse result) {
                                    result.getTemplateId();
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
