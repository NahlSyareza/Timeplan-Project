package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginBidangActivity extends TemplateActivity {

    private TextView registerBidangText;
    private EditText namaBidangField, passwordBidangField;
    private Button loginButton;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bidang);

        apiService = UtilsApi.getApiService();
        registerBidangText = findViewById(R.id.LoginBidang_registerBidangText);
        namaBidangField = findViewById(R.id.LoginBidang_namaBidangField);
        passwordBidangField = findViewById(R.id.LoginBidang_passwordBidangField);
        loginButton = findViewById(R.id.LoginBidang_loginButton);

        registerBidangText.setOnClickListener(view -> {
            moveActivity(RegisterBidangActivity.class);
        });

        loginButton.setOnClickListener(view -> {
            handleLoginBidang();
        });
    }

    public void handleLoginBidang() {
        String namaBidang = namaBidangField.getText().toString().toUpperCase();
        String passwordBidang = passwordBidangField.getText().toString();

        apiService.loginBidang(namaBidang, passwordBidang).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();

                viewToast(res.message);

                if (res.state) {
                    StaticUtils.LOGGED_BIDANG = namaBidang;
                    switch (namaBidang) {
                        case "PIPTEK":
                            MediaPlayer youHave = MediaPlayer.create(ctx, R.raw.you_have_good_taste);
                            youHave.start();
                            break;

                        case "TOUR DE FORCE":
                            MediaPlayer youWant = MediaPlayer.create(ctx, R.raw.you_want_to_play);
                            youWant.start();
                            break;

                        default:
                            break;

                    }
                    moveActivity(MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}