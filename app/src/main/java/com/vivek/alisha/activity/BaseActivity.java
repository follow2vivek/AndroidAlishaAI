package com.vivek.alisha.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vivek.alisha.R;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
