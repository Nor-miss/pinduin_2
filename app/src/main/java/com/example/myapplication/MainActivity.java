package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Настройка отступов для системных окон
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация элементов UI
        jokeTextView = findViewById(R.id.jokeTextView);
        ImageButton mapButton = findViewById(R.id.imageButton);
        ImageButton notesButton = findViewById(R.id.notesButton);
        ImageButton startButton = findViewById(R.id.button); // Объявление кнопки Start

        // Загрузка анекдота дня
        loadDailyJoke();

        // Обработчик для кнопки карты
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Обработчик для кнопки заметок
        notesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        });

        // Обработчик для кнопки Start
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
            startActivity(intent);
        });
    }

    private void loadDailyJoke() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApiService service = retrofit.create(JokeApiService.class);
        Call<Joke> call = service.getRandomJoke();

        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Joke joke = response.body();
                    String jokeText = joke.setup + "\n\n" + joke.punchline;
                    jokeTextView.setText(jokeText);
                } else {
                    jokeTextView.setText("Не удалось загрузить анекдот. Попробуйте позже.");
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                jokeTextView.setText("Ошибка подключения. Проверьте интернет.");
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Интерфейс для API анекдотов
    public interface JokeApiService {
        @GET("random_joke")
        Call<Joke> getRandomJoke();
    }

    // Модель данных для анекдота
    public static class Joke {
        private String setup;
        private String punchline;

        public String getSetup() {
            return setup;
        }

        public String getPunchline() {
            return punchline;
        }
    }
}