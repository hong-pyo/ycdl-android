package com.hong2.ycdl.common.user;

import com.kakao.util.OptionalBoolean;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;


public class KakaoMeDto implements Serializable {
    private Long id;
    private OptionalBoolean hasSignedUp;

    private UserAccountDto kakaoAccount;
    private Map<String, String> properties;
    private JSONObject forPartners;

    private String nickname;
    private String thumbnailImagePath;
    private String profileImagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OptionalBoolean getHasSignedUp() {
        return hasSignedUp;
    }

    public void setHasSignedUp(OptionalBoolean hasSignedUp) {
        this.hasSignedUp = hasSignedUp;
    }

    public UserAccountDto getKakaoAccount() {
        return kakaoAccount;
    }

    public void setKakaoAccount(UserAccountDto kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public JSONObject getForPartners() {
        return forPartners;
    }

    public void setForPartners(JSONObject forPartners) {
        this.forPartners = forPartners;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}