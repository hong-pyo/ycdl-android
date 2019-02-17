package com.hong2.ycdl.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.global.IntentConstant;
import com.hong2.ycdl.common.user.UserInfo;
import com.hong2.ycdl.common.util.VideoUtil;

public class VideoPlayActivity extends Activity {
    /* Xml Control */
    private Button manageButton;
    private TextView videoCateogy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        final VideoCategory category = VideoUtil.getCategory(getIntent());

        manageButton = findViewById(R.id.manage_button);
        videoCateogy = findViewById(R.id.video_text_View);

        videoCateogy.setText(category.getDisplayTitle());

        if (UserInfo.getIdx() == 967392860L) {
            manageButton.setVisibility(View.VISIBLE);
        }
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manageMode = new Intent(getApplicationContext(), VideoManageActivity.class);
                manageMode.putExtra(IntentConstant.VIDEO.CATEGORY, category);
                startActivity(manageMode);
            }
        });
    }
}
