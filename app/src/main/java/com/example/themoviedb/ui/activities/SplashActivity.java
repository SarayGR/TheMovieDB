package com.example.themoviedb.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.themoviedb.R;
import com.example.themoviedb.api.ApiAdapter;
import com.example.themoviedb.model.TokenDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Callback<TokenDTO> {

    private final int DURATION_SPLASH = 5000;
    private TokenDTO tokenDTO;

    public String requestToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        callRequestTokenJob();

    }

    public void callRequestTokenJob() {
        tokenDTO = new TokenDTO();
        Call<TokenDTO> call = ApiAdapter.getApiService().getRequestToken(getString(R.string.api_key));
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
        if (response != null && response.body() != null) {
            requestToken = response.body().getRequestToken();
            Log.d("ÉXITO", requestToken);
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra("REQUEST_TOKEN", requestToken);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onFailure(Call<TokenDTO> call, Throwable t) {
        t.printStackTrace();
        Log.d("ERROR", t.getMessage());
    }


}