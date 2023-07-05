package com.example.tamago;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class PauseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);

        Button pmm = findViewById(R.id.continue_Button);
        pmm.setOnClickListener(view -> {
            Intent intent = new Intent(PauseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


    }
}
