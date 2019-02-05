package com.hong2.ycdl.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.hong2.ycdl.R;
import com.hong2.ycdl.common.global.RCodeContant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenActivity extends Activity {
    /* Xml Control */
    private ListView listView;
    private TextView outPut;
    private String data;
    private List<String> videoList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        videoList = new ArrayList<>();
        listView = findViewById(R.id.listen_listView);

        intent = getIntent();
        VideoCategory categoryList = (VideoCategory) intent.getSerializableExtra("rData");

        List<String> videoList = (List<String>) intent.getSerializableExtra("videoList");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1, videoList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);
    }

    private boolean isAvailableInputData(VideoCategory categoryList, List<String> videoList) {
        return categoryList.getrCode().equals(RCodeContant.CODE.SUCCESS) && videoList != null;
    }
}
