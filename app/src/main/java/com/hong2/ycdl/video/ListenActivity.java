package com.hong2.ycdl.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.global.IntentConstant;
import com.hong2.ycdl.common.global.RCodeContant;
import com.hong2.ycdl.video.dto.VideoCategoryDto;

import java.util.ArrayList;
import java.util.List;

public class ListenActivity extends Activity {
    /* Xml Control */
    private ListView listView;
    private List<String> videoList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        videoList = new ArrayList<>();
        listView = findViewById(R.id.listen_listView);

        intent = getIntent();
        VideoCategoryDto categoryList = (VideoCategoryDto) intent.getSerializableExtra(IntentConstant.VIDEO.VIDEO_LIST);
        final List<VideoCategory> categories = categoryList.getrData();
        if (isAvailableInputData(categoryList, categories)) {
            for (int i = 0; i<categories.size(); i++) {
                videoList.add(categories.get(i).getDisplayTitle());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1, videoList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent videoPlayActivity = new Intent(getApplicationContext(), VideoPlayActivity.class);
                videoPlayActivity.putExtra(IntentConstant.VIDEO.CATEGORY, categories.get(position));
                startActivity(videoPlayActivity);
            }
        });
        listView.setAdapter(adapter);
    }

    private boolean isAvailableInputData(VideoCategoryDto categoryList, List<VideoCategory> categories) {
        return RCodeContant.CODE.SUCCESS.equals(categoryList.getrCode()) && categories != null;
    }
}
