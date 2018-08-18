package com.vivek.alisha.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vivek.alisha.R;
import com.vivek.alisha.utils.Global;

import java.util.List;

public class SplashScreenActivity extends BaseActivity {

    String[] permissionArray = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initView();
    }

    private void initView() {

        Dexter.withActivity(this)
                .withPermissions(permissionArray)
                .withListener(permissionsListener)
                .check();

    }

    MultiplePermissionsListener permissionsListener = new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            permissionToken.continuePermissionRequest();
        }
    };
}
