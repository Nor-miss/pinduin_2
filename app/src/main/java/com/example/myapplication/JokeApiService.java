package com.example.myapplication;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
public class JokeApiService {
    private static final String BASE_URL = "https://official-joke-api.appspot.com/";

    public interface ApiInterface {
        @GET("random_joke")
        Call<Joke> getRandomJoke();
    }

    public static ApiInterface getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    public static class Joke {
        public String setup;
        public String punchline;
    }
}
