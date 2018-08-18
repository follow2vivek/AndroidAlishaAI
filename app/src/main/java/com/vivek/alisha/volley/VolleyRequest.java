package com.vivek.alisha.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vivek on 20-09-2017.
 */

public class VolleyRequest {

    Context context;
    VolleyResponse volleyResponse;

    public VolleyRequest(Context context, VolleyResponse volleyResponse) {
        this.context = context;
        this.volleyResponse = volleyResponse;
    }

    public void requestAI(final String cmd) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                VolleyUrl.AI_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                volleyResponse.getVolleyResponse(true, response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                volleyResponse.getVolleyResponse(false, error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cmd", cmd);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strReq);
    }

}