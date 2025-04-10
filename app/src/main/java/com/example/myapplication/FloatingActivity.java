package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class FloatingActivity extends AppCompatActivity {
    private boolean isPenguin = true; // Флаг для отслеживания текущей анимации

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout); // Начинаем с пингвина

        ImageView animalView = findViewById(R.id.imageView);
        ImageButton leftArrow = findViewById(R.id.leftArrow);
        ImageButton rightArrow = findViewById(R.id.rightArrow);

        // Инициализация анимации
        Animation floatingAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        floatingAnimation.setDuration(2000);
        floatingAnimation.setRepeatCount(Animation.INFINITE);
        floatingAnimation.setRepeatMode(Animation.REVERSE);
        animalView.startAnimation(floatingAnimation);

        // Обработчики для кнопок-стрелок
        leftArrow.setOnClickListener(v -> switchAnimal(false));
        rightArrow.setOnClickListener(v -> switchAnimal(true));
    }

    private void switchAnimal(boolean moveRight) {
        if ((moveRight && isPenguin) || (!moveRight && !isPenguin)) {
            // Переключаемся между макетами
            setContentView(isPenguin ? R.layout.new_layout1 : R.layout.new_layout);

            // Обновляем флаг
            isPenguin = !isPenguin;

            // Находим новые элементы
            ImageView animalView = findViewById(R.id.imageView);
            ImageButton leftArrow = findViewById(R.id.leftArrow);
            ImageButton rightArrow = findViewById(R.id.rightArrow);

            // Применяем анимацию к новому изображению
            Animation floatingAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
            floatingAnimation.setDuration(2000);
            floatingAnimation.setRepeatCount(Animation.INFINITE);
            floatingAnimation.setRepeatMode(Animation.REVERSE);
            animalView.startAnimation(floatingAnimation);

            // Устанавливаем обработчики снова
            leftArrow.setOnClickListener(v -> switchAnimal(false));
            rightArrow.setOnClickListener(v -> switchAnimal(true));
        }
    }
}