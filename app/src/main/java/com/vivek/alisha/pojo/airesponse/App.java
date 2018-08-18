package com.vivek.alisha.pojo.airesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vivek on 19-09-2017.
 */

public class App {

    @SerializedName("cmd")
    @Expose
    private String cmd;
    @SerializedName("action_code")
    @Expose
    private Integer actionCode;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

}