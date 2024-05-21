package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private TextView registerBidangTextLoginBidang;
    private EditText namaBidangFieldLoginBidang;
    private EditText passwordBidangFieldLoginBidang;
    private Button loginButtonLoginBidang;
    private ImageView backImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bidang);

        apiService = UtilsApi.getApiService();
        registerBidangTextLoginBidang = findViewById(R.id.registerBidangTextLoginBidang);
        namaBidangFieldLoginBidang = findViewById(R.id.namaBidangFieldLoginBidang);
        passwordBidangFieldLoginBidang = findViewById(R.id.passwordBidangFieldLoginBidang);
        loginButtonLoginBidang = findViewById(R.id.loginButtonLoginBidang);
        backImage = findViewById(R.id.backImage);

        registerBidangTextLoginBidang.setOnClickListener(view -> {
            moveActivity(RegisterBidangActivity.class);
        });

        backImage.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
        });

        loginButtonLoginBidang.setOnClickListener(view -> {
            handleLoginBidang();
        });
    }

    public void handleLoginBidang() {
        String nama_bidang = namaBidangFieldLoginBidang.getText().toString().toUpperCase();
        String password_bidang = passwordBidangFieldLoginBidang.getText().toString();

        apiService.loginBidang(nama_bidang, password_bidang).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();

                viewToast(res.message);

                if (res.state) {
                    StaticUtils.LOGGED_BIDANG = nama_bidang;
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