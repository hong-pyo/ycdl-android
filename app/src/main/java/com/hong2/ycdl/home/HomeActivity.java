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
import com.google.gson.Gson;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.user.KakaoMeDto;
import com.hong2.ycdl.common.widget.KakaoToast;
import com.hong2.ycdl.speak.SpeakActivity;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.hong2.ycdl.common.global.NetworkConstant.YCDL_SERVER_URL;

public class HomeActivity extends Activity {

    private RequestQueue queue;
    private Intent intent;
    private KakaoMeDto kakaoMeDto;
    private TextView titleBar;
    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        titleBar = findViewById(R.id.welcome_textView);
        btn1 = findViewById(R.id.listen_menu_button);
        btn2 = findViewById(R.id.speak_menu_button);

        String welcomeUrl = YCDL_SERVER_URL + "/welcome";
        String signUpUrl = YCDL_SERVER_URL + "/kakao/sign-up";
        queue = Volley.newRequestQueue(this);

        intent = getIntent();
        kakaoMeDto = (KakaoMeDto) intent.getSerializableExtra("kakaoMe");


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speakIntent = new Intent(getApplicationContext(), SpeakActivity.class);
                startActivity(speakIntent);
            }
        });

        StringRequest stringRequest = getWelcomeCondition(welcomeUrl);
        if (kakaoMeDto.getHasSignedUp()) {
            Gson gson = new Gson();
            String params = gson.toJson(kakaoMeDto);
            queue.add(requestSignUp(params, signUpUrl));
        }
        stringRequest.setTag("MAIN");
        queue.add(stringRequest);
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
    private StringRequest getWelcomeCondition(final String url) {
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
