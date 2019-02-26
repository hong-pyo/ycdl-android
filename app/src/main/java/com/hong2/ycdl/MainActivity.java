package com.hong2.ycdl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.hong2.ycdl.common.global.IntentConstant;
import com.hong2.ycdl.common.user.KakaoMeDto;
import com.hong2.ycdl.common.user.UserInfo;
import com.hong2.ycdl.home.HomeActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;


public class MainActivity extends Activity {

    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callback = new SessionCallback();
        //getAppKeyHash();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e("login error",exception);
            }
        }
    }

    protected void redirectSignupActivity() {
        requestPersonalInfo(this, HomeActivity.class);
    }
    private void requestPersonalInfo(final Context from, final Class<?> to) {
        final KakaoMeDto kakaoMeDto = new KakaoMeDto();
        UserManagement.getInstance().me(new MeV2ResponseCallback() {

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("kakao", "fail kakao request PersonalInfo");
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Intent intent = new Intent(from, to);
                kakaoMeDto.setNickName(result.getNickname());
                kakaoMeDto.setId(result.getId());
                if (isNotNullImage(result)) {
                    kakaoMeDto.setProfileImagePath(result.getProfileImagePath());
                    kakaoMeDto.setThumbnailImagePath(result.getThumbnailImagePath());
                }
                kakaoMeDto.setHasSignedUp(result.hasSignedUp());
                UserInfo.setIdx(kakaoMeDto.getId());
                UserInfo.setNickName(kakaoMeDto.getNickName());
                intent.putExtra(IntentConstant.MEMBER.KAKAO_REQUEST, kakaoMeDto);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isNotNullImage(MeV2Response result) {
        return result.getProfileImagePath() != null || result.getThumbnailImagePath() != null;
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
}
