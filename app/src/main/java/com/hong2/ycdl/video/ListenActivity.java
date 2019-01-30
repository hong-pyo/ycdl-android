package com.hong2.ycdl.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.hong2.ycdl.R;

import java.util.ArrayList;
import java.util.List;

public class ListenActivity extends Activity {
    /* Xml Control */
    private ListView listView;
    private TextView outPut;
    private String data;
    private List<String> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        videoList = new ArrayList<>();
        listView = findViewById(R.id.listen_listView);
        videoList.add("가족");
        videoList.add("몸");
        videoList.add("음식");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1,videoList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setAdapter(adapter);
    }
}
