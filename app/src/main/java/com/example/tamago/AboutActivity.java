package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;


public class AboutActivity extends Activity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }


}
