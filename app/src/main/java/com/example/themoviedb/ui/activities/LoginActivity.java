package com.example.themoviedb.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.themoviedb.R;
import com.example.themoviedb.api.ApiAdapter;
import com.example.themoviedb.model.CreateSessionDTO;
import com.example.themoviedb.model.CreateSessionResponseDTO;
import com.example.themoviedb.model.LoginSessionDTO;
import com.example.themoviedb.model.TokenDTO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextInputEditText etLoginUserEdittext, etLoginPasswordEdittext;
    MaterialTextView tvSignUp;

    private LoginSessionDTO loginSessionDTO;
    private CreateSessionDTO createSessionDTO;

    public String requestSessionToken;
    public String sessionId;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = this.getPreferences(Context.MODE_PRIVATE);
        btnLogin = findViewById(R.id.btnLogin);
        etLoginUserEdittext = findViewById(R.id.etLoginUserEdittext);
        etLoginPasswordEdittext = findViewById(R.id.etLoginPasswordEdittext);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(v -> {
            String url = "https://www.themoviedb.org/signup";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    public void callValidateWithLogin(View view) {
        loginSessionDTO = new LoginSessionDTO();

        if (!etLoginUserEdittext.getText().toString().equals("") && !etLoginPasswordEdittext.getText().toString().equals("")) {
            loginSessionDTO.setUsername(etLoginUserEdittext.getText().toString());
            loginSessionDTO.setPassword(etLoginPasswordEdittext.getText().toString());
            if (getIntent().getExtras().getString("REQUEST_TOKEN") != null)
                loginSessionDTO.setRequestToken(getIntent().getExtras().getString("REQUEST_TOKEN"));

            Call<TokenDTO> call = ApiAdapter.getApiService().validateWithLogin(loginSessionDTO, getString(R.string.api_key));
            call.enqueue(new Callback<TokenDTO>() {
                @Override
                public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                    if (response != null && response.body() != null) {
                        requestSessionToken = response.body().getRequestToken();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("REQUEST_SESSION_TOKEN", requestSessionToken);
                        editor.commit();
                        Log.d("ÉXITO", requestSessionToken);
                        callCreateSession();
                    } else {
                        if (response.raw().code() == 401){
                            showAlertDialog("Usuario y/o contraseña incorrectos");
                        } else {
                            showAlertDialog(response.raw().message());
                        }

                    }
                }

                @Override
                public void onFailure(Call<TokenDTO> call, Throwable t) {
                    showAlertDialog(t.getMessage());
                    Log.d("ERROR", t.getMessage());
                }
            });
        } else {
            showAlertDialog("Debe rellenar todos los campos");
        }
    }

    public void callCreateSession() {
        createSessionDTO = new CreateSessionDTO();

        createSessionDTO.setRequestToken(requestSessionToken);
        Call<CreateSessionResponseDTO> call = ApiAdapter.getApiService().createSessionId(createSessionDTO, getString(R.string.api_key));
        call.enqueue(new Callback<CreateSessionResponseDTO>() {
            @Override
            public void onResponse(Call<CreateSessionResponseDTO> call, Response<CreateSessionResponseDTO> response) {
                if (response != null && response.body() != null) {
                    sessionId = response.body().getSessionId();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("SESSION_ID", sessionId);
                    editor.commit();
                    Log.d("SESSION_ID",sessionId);
                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showAlertDialog(response.raw().message());
                }

            }

            @Override
            public void onFailure(Call<CreateSessionResponseDTO> call, Throwable t) {
                showAlertDialog(t.getMessage());
            }
        });
    }

    public void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atención");
        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}