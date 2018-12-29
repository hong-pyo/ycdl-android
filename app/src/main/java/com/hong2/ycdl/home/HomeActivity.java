package com.hong2.ycdl.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.user.KakaoMeDto;
import com.hong2.ycdl.common.widget.KakaoToast;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends Activity {

    private RequestQueue queue;
    private Intent intent;
    private KakaoMeDto kakaoMeDto;
    private TextView titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        titleBar = findViewById(R.id.welcome_textView);

        String url = "http://52.78.131.64:8080/welcome";
        queue = Volley.newRequestQueue(this);

        intent = getIntent();
        kakaoMeDto = (KakaoMeDto) intent.getSerializableExtra("kakaoMe");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //KakaoToast.makeToast(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
                if (kakaoMeDto.getNickname() != null) {
                    nickName = kakaoMeDto.getNickname();
                }
                params.put("memberNickName", nickName);
                return params;
            }
        };

        stringRequest.setTag("MAIN");
        queue.add(stringRequest);
    }
}
