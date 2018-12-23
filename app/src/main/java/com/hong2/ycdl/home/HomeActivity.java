package com.hong2.ycdl.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.user.KakaoMeDto;
import com.hong2.ycdl.common.widget.KakaoToast;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showHelloKakaoToast();
    }

    private void showHelloKakaoToast() {
        Intent intent = getIntent();
        KakaoMeDto kakaoMeDto = (KakaoMeDto) intent.getSerializableExtra("kakaoMe");
        KakaoToast.makeToast(getApplicationContext(), "Welcome Login "+ kakaoMeDto.getNickname(), Toast.LENGTH_SHORT).show();
    }
}
