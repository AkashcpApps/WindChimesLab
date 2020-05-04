package com.akashcp.windchimeslab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=(ImageView)findViewById(R.id.wind_chimes_lab_logo);
        TextView textView=(TextView)findViewById(R.id.wind_chimes_lab_text);
        imageView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom));
        textView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom));
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
},2000);
    }
}
