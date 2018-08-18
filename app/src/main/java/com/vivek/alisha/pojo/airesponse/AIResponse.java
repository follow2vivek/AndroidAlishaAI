package com.vivek.alisha.pojo.airesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vivek on 19-09-2017.
 */

public class AIResponse {

    @SerializedName("reply")
    @Expose
    private String reply;
    @SerializedName("app")
    @Expose
    private App app;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

}