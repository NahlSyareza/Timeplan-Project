package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Adds a new bidang to the database
 *
 */
public class RegisterBidangActivity extends TemplateActivity {

    private EditText namaBidangField, passwordBidangField, namaKetuaBidangField, namaPengurusBidangField;
    private Button registerButton;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bidang);

        apiService = UtilsApi.getApiService();
        namaBidangField = findViewById(R.id.RegisterBidang_namaBidangField);
        passwordBidangField = findViewById(R.id.RegisterBidang_passwordBidangField);
        namaKetuaBidangField = findViewById(R.id.RegisterBidang_namaKetuaBidangField);
        namaPengurusBidangField = findViewById(R.id.RegisterBidang_namaPengurusBidangField);
        registerButton = findViewById(R.id.RegisterBidang_registerButton);

        registerButton.setOnClickListener(view -> {
            handleAddBidang();
        });
    }

    /**
     * Backend handler for adding the data
     *
     */
    public void handleAddBidang() {
        String nama_bidang = namaBidangField.getText().toString();
        String password_bidang = passwordBidangField.getText().toString();
        String nama_ketua_bidang = namaKetuaBidangField.getText().toString();
        String[] nama_pengurus_bidang = namaPengurusBidangField.getText().toString().split("\n");

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