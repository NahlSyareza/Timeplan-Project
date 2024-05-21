package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterBidangActivity extends TemplateActivity {

    private EditText namaBidangFieldBidang;
    private EditText passwordBidangFieldBidang;
    private EditText namaKetuaBidangFieldBidang;
    private EditText namaPengurusBidangFieldBidang;
    private Button registerButtonRegisterBidang;
    private ImageView backImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bidang);

        apiService = UtilsApi.getApiService();
        namaBidangFieldBidang = findViewById(R.id.namaBidangFieldRegisterBidang);
        passwordBidangFieldBidang = findViewById(R.id.passwordBidangFieldRegisterBidang);
        namaKetuaBidangFieldBidang = findViewById(R.id.namaKetuaBidangFieldRegisterBidang);
        namaPengurusBidangFieldBidang = findViewById(R.id.namaPengurusBidangFieldRegisterBidang);
        registerButtonRegisterBidang = findViewById(R.id.registerButtonRegisterBidang);
        backImage = findViewById(R.id.backImage);

        backImage.setOnClickListener(view -> {
            moveActivity(LoginBidangActivity.class);
        });

        registerButtonRegisterBidang.setOnClickListener(view -> {
            handleAddBidang();
        });
    }

    public void handleAddBidang() {
        String nama_bidang = namaBidangFieldBidang.getText().toString();
        String password_bidang = passwordBidangFieldBidang.getText().toString();
        String nama_ketua_bidang = namaKetuaBidangFieldBidang.getText().toString();
        String[] nama_pengurus_bidang = namaPengurusBidangFieldBidang.getText().toString().split("\n");

        apiService.registerBidang(nama_bidang, password_bidang, nama_ketua_bidang, nama_pengurus_bidang).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();

                viewToast(res.message);
                moveActivity(MainActivity.class);
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}