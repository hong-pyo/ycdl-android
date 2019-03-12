package com.hong2.ycdl2.video;

import java.io.Serializable;

public class VideoContent implements Serializable {
    private String title;
    private String question;
    private String answer;
    private String link;
    private Long level;
    private VideoCategory videoCategory;

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public void setVideoCategory(VideoCategory videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getLink() {
        return link;
    }

    public Long getLevel() {
        return level;
    }

    public VideoCategory getVideoCategory() {
        return videoCategory;
    }
}
