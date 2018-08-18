package com.vivek.alisha.utils;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Vivek on 20-09-2017.
 */

public class Common {

    public static Gson getGson() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
    public static  void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT)
        .show();
    }
}
