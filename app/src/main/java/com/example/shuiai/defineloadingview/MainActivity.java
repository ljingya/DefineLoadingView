package com.example.shuiai.defineloadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DefineLoadingView defineLoadingView = (DefineLoadingView) findViewById(R.id.defineView);
        defineLoadingView.setFinnish(80);
    }
}
