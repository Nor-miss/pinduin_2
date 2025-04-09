package com.example.myapplication;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class FloatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        ImageView penguin = findViewById(R.id.imageView);

        // Загружаем анимацию
        Animation floatingAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Настраиваем анимацию
        floatingAnimation.setDuration(2000);
        floatingAnimation.setRepeatCount(Animation.INFINITE);
        floatingAnimation.setRepeatMode(Animation.REVERSE);

        // Запускаем анимацию
        penguin.startAnimation(floatingAnimation);
    }
}