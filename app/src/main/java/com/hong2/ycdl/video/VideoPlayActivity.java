package com.hong2.ycdl.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.user.UserInfo;

public class VideoPlayActivity extends Activity {
    /* Xml Control */
    private Button manageButton;
    private TextView videoCateogy;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        VideoCategory categoryList = getCategory();

        manageButton = findViewById(R.id.manage_button);
        videoCateogy = findViewById(R.id.video_text_View);

        videoCateogy.setText(categoryList.getDisplayTitle());

        if (UserInfo.getIdx() == 967392860L) {
            manageButton.setVisibility(View.VISIBLE);
        }
    }

    private VideoCategory getCategory() {
        intent = getIntent();
        return (VideoCategory) intent.getSerializableExtra("videoPosition");
    }
}
