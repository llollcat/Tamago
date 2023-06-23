package com.example.tamago;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PauseActivity extends AppCompatActivity {
        @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.pause);
        Button pmm = findViewById(R.id.PMM);

        pmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PauseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
