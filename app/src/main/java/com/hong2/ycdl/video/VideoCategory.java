package com.hong2.ycdl.video;


import java.io.Serializable;


public class VideoCategory implements Serializable {
    private Long idx;
    private String name;
    private String displayTitle;

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public Long getIdx() {
        return idx;
    }

    public String getName() {
        return name;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }
}
