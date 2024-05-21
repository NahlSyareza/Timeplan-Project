package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBidangActivity extends TemplateActivity {

    private EditText namaBidangField, passwordBidangField, namaKetuaBidangField, namaPengurusBidangField;
    private Button editButton;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bidang);

        apiService = UtilsApi.getApiService();
        namaBidangField = findViewById(R.id.EditBidang_namaBidangField);
        passwordBidangField = findViewById(R.id.EditBidang_passwordBidangField);
        namaKetuaBidangField = findViewById(R.id.EditBidang_namaKetuaBidangField);
        namaPengurusBidangField = findViewById(R.id.EditBidang_namaPengurusBidang);
        editButton = findViewById(R.id.EditBidang_editButton);

        handleGetBidang();

        editButton.setOnClickListener(view -> {
            handleEditBidang();
            handleEditProker();
            StaticUtils.LOGGED_BIDANG = namaBidangField.getText().toString();
            moveActivity(MainActivity.class);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveActivity(MainActivity.class);
    }

    public void handleEditProker() {
        apiService.editProker(StaticUtils.LOGGED_BIDANG, namaBidangField.getText().toString()).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                Proker proker = res.payload;

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void handleEditBidang() {
        String nama_bidang = namaBidangField.getText().toString();
        String password_bidang = passwordBidangField.getText().toString();
        String nama_ketua_bidang = namaKetuaBidangField.getText().toString();
        String[] nama_pengurus_bidang = namaPengurusBidangField.getText().toString().split("\n");

        apiService.editBidang(StaticUtils.LOGGED_BIDANG, nama_bidang, password_bidang, nama_ketua_bidang, nama_pengurus_bidang).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();
                Bidang bidang = res.payload;

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void handleGetBidang() {
        apiService.getBidang(StaticUtils.LOGGED_BIDANG).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();
                Bidang bidang = res.payload;

                namaBidangField.setText(bidang.nama_bidang);
                passwordBidangField.setText(bidang.password_bidang);
                namaKetuaBidangField.setText(bidang.nama_ketua_bidang);
                StringBuilder sb = new StringBuilder();
                for(String s : bidang.nama_pengurus_bidang) {
                    sb.append(s).append("\n");
                }
                sb.delete(sb.length() - 2, sb.length());
                namaPengurusBidangField.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}
