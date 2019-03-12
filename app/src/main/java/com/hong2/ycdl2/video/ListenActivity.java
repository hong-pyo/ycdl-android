package com.hong2.ycdl2.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hong2.ycdl2.R;
import com.hong2.ycdl2.common.global.IntentConstant;
import com.hong2.ycdl2.common.global.RCodeContant;
import com.hong2.ycdl2.common.user.UserInfo;
import com.hong2.ycdl2.util.HongGsonUtil;
import com.hong2.ycdl2.video.dto.VideoCategoryDto;
import com.hong2.ycdl2.video.dto.VideoContentDto;

import java.util.ArrayList;
import java.util.List;

import static com.hong2.ycdl2.common.global.NetworkConstant.YCDL_SERVER_URL;

public class ListenActivity extends Activity {
    /* Xml Control */
    private ListView listView;
    private List<String> videoList;
    private Intent intent;
    private RequestQueue queue;
    private VideoContentDto videoContentDto;

    private String VIDEO_URL = YCDL_SERVER_URL + "/video/link/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
        videoList = new ArrayList<>();
        listView = findViewById(R.id.listen_listView);
        queue = Volley.newRequestQueue(this);
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
                StringRequest videoRequest = getVideoLink(VIDEO_URL + categories.get(position).getIdx(), categories.get(position));
                videoRequest.setTag("VIDEO_LINK");
                queue.add(videoRequest);
            }
        });
        listView.setAdapter(adapter);
    }

    @NonNull
    private StringRequest getVideoLink(String url, final VideoCategory videoCategory) {
        return new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                videoContentDto = HongGsonUtil.fromJson(response, VideoContentDto.class);
                if (hasVideoLink(videoContentDto) || UserInfo.checkManage()) {
                    Intent videoPlayActivity = new Intent(getApplicationContext(), VideoPlayActivity.class);
                    videoPlayActivity.putExtra(IntentConstant.VIDEO.CONTENTS, videoContentDto.getrData());
                    videoPlayActivity.putExtra(IntentConstant.VIDEO.CATEGORY, videoCategory);
                    startActivity(videoPlayActivity);
                }
                queue.cancelAll("VIDEO_LINK");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private boolean hasVideoLink(VideoContentDto videoContent) {
        return RCodeContant.CODE.SUCCESS.equals(videoContent.getrCode()) && videoContent.getrData() != null;
    }

    private boolean isAvailableInputData(VideoCategoryDto categoryList, List<VideoCategory> categories) {
        return RCodeContant.CODE.SUCCESS.equals(categoryList.getrCode()) && categories != null;
    }
}
