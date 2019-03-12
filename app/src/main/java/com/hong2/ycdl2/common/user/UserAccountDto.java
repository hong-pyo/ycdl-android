package com.hong2.ycdl2.common.user;

import com.kakao.network.response.ResponseBody;
import com.kakao.usermgmt.StringSet;
import com.kakao.usermgmt.response.model.AgeRange;
import com.kakao.usermgmt.response.model.Gender;
import com.kakao.util.OptionalBoolean;
import org.json.JSONObject;

public class UserAccountDto {
    private OptionalBoolean hasEmail;
    private OptionalBoolean isEmailVerified;
    private String email;

    private OptionalBoolean hasPhoneNumber;
    private String phoneNumber;

    private OptionalBoolean hasAgeRange;
    private AgeRange ageRange;

    private OptionalBoolean hasBirthday;
    private String birthday;

    private OptionalBoolean hasGender;
    private Gender gender;

    private OptionalBoolean isKakaoTalkUser;
    private String displayId;

    private JSONObject response;

    public UserAccountDto(ResponseBody body) {
        hasEmail = body.has(StringSet.has_email) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_email)) :
                OptionalBoolean.NONE;
        isEmailVerified = body.has(StringSet.is_email_verified) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.is_email_verified)) :
                OptionalBoolean.NONE;
        hasPhoneNumber = body.has(StringSet.has_phone_number) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_phone_number)) :
                OptionalBoolean.NONE;
        hasAgeRange = body.has(StringSet.has_age_range) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_age_range)) :
                OptionalBoolean.NONE;
        hasBirthday = body.has(StringSet.has_birthday) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_birthday)) :
                OptionalBoolean.NONE;
        hasGender = body.has(StringSet.has_gender) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.has_gender)) :
                OptionalBoolean.NONE;
        isKakaoTalkUser = body.has(StringSet.is_kakaotalk_user) ?
                OptionalBoolean.getOptionalBoolean(body.getBoolean(StringSet.is_kakaotalk_user)) :
                OptionalBoolean.NONE;


        if (body.has(StringSet.email)) email = body.getString(StringSet.email);
        if (body.has(StringSet.phone_number)) phoneNumber = body.getString(StringSet.phone_number);
        if (body.has(StringSet.age_range)) ageRange = AgeRange.getRange(body.getString(StringSet.age_range));
        if (body.has(StringSet.birthday)) birthday = body.getString(StringSet.birthday);
        if (body.has(StringSet.gender)) gender = Gender.getGender(body.getString(StringSet.gender));
        if (body.has(StringSet.display_id)) displayId = body.getString(StringSet.display_id);

        response = body.getJson();
    }
}
