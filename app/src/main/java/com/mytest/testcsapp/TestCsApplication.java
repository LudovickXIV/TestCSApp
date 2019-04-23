package com.mytest.testcsapp;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

public class TestCsApplication extends Application {

    protected FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.admob_app_id));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
