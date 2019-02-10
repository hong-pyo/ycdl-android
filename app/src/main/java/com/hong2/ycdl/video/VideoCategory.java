package com.hong2.ycdl.video;


import java.io.Serializable;


public class VideoCategory implements Serializable {
    private Long idx;
    private String name;
    private String displayTitle;

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
