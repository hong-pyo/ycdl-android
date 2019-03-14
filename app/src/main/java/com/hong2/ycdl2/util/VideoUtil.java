package com.hong2.ycdl2.util;

import android.content.Intent;
import com.hong2.ycdl2.common.global.IntentConstant;
import com.hong2.ycdl2.video.VideoCategory;
import com.hong2.ycdl2.video.VideoContent;

public class VideoUtil {
    public static VideoCategory getCategory(Intent intent) {
        return (VideoCategory) intent.getSerializableExtra(IntentConstant.VIDEO.CATEGORY);
    }

    public static VideoContent getContent(Intent intent) {
        return (VideoContent) intent.getSerializableExtra(IntentConstant.VIDEO.CONTENTS);
    }
}
