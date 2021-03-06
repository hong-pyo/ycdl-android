package com.hong2.ycdl2.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hong2.ycdl2.R;
import com.hong2.ycdl2.common.global.UrlConstant;
import com.hong2.ycdl2.util.VideoUtil;
import com.hong2.ycdl2.util.HongGsonUtil;
import com.hong2.ycdl2.util.HongStringUtil;
import org.json.JSONException;
import org.json.JSONObject;


public class VideoManageActivity extends Activity {
    /* Xml Control */
    private EditText link_add_title;
    private EditText link_add_question;
    private EditText link_add_answer;
    private EditText link_add_url;
    private EditText link_add_level;

    private EditText displayTitle;
    private EditText displayName;

    private Button link_add_btn;
    private Button category_btn;
    private TextView link_add_result_text;
    private TextView categoryResultText;

    private RequestQueue queue;

    private VideoCategory category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_manage);
        queue = Volley.newRequestQueue(this);

        // category
        displayName = findViewById(R.id.video_category_add_name_edit);
        displayTitle = findViewById(R.id.video_category_add_displayTitle_edit);
        category_btn = findViewById(R.id.video_category_add_send_button);
        categoryResultText = findViewById(R.id.video_category_add_result_text);

        // link
        link_add_title = findViewById(R.id.video_link_add_title_edit);
        link_add_question = findViewById(R.id.video_link_add_quetion_edit);
        link_add_btn = findViewById(R.id.video_link_add_send_button);
        link_add_result_text = findViewById(R.id.video_link_add_send_result_text);
        link_add_answer = findViewById(R.id.video_link_add_answer_edit);
        link_add_url = findViewById(R.id.video_link_add_link_edit);
        link_add_level = findViewById(R.id.video_link_add_level_edit);

        category = VideoUtil.getCategory(getIntent());

        link_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoContent videoContent = new VideoContent();
                videoContent.setAnswer(HongStringUtil.getText(link_add_answer));
                videoContent.setLevel(Long.valueOf(HongStringUtil.getText(link_add_level)));
                videoContent.setQuestion(HongStringUtil.getText(link_add_question));
                videoContent.setTitle(HongStringUtil.getText(link_add_title));
                videoContent.setLink(HongStringUtil.getText(link_add_url));
                videoContent.setVideoCategory(category);

                String params = HongGsonUtil.getGsonString(videoContent);
                queue.add(addLink(UrlConstant.LINK_ADD, params));
            }
        });

        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCategory videoCategory = new VideoCategory();

                if (!isNullCheckOnCategory()) {
                    videoCategory.setDisplayTitle(displayTitle.getText().toString());
                    videoCategory.setName(displayName.getText().toString());
                }

                String params = HongGsonUtil.getGsonString(videoCategory);
                queue.add(addCategory(UrlConstant.CATEGORY_ADD, params));
            }
        });
    }

    private boolean isNullCheckOnCategory() {
        return HongStringUtil.isBlank(displayName.getText().toString()) || HongStringUtil.isBlank(displayTitle.getText().toString());
    }

    @NonNull
    private JsonObjectRequest addLink(String url, String params) {
        return new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String result = "";
                try {
                    result  = response.getString("rMessage");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                link_add_result_text.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @NonNull
    private JsonObjectRequest addCategory(String url, String params) {
        return new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String result = "";
                try {
                    result  = response.getString("rMessage");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                categoryResultText.setText(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
