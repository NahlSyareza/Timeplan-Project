package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

/**
 * Edits the name, password, leader, and pengurus of a bidang
 *
 */
public class EditBidangActivity extends TemplateActivity {

    private EditText namaBidangField, passwordBidangField, namaKetuaBidangField, namaPengurusBidangField;
    private Button editButton;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
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
            StaticUtils.LOGGED_BIDANG = namaBidangField.getText().toString().toUpperCase();
            moveActivity(MainActivity.class);
        });
    }

    /**
     * Shoots to the backend to edit
     *
     */
    public void handleEditProker() {
        String namaBidang = namaBidangField.getText().toString().toUpperCase();
        apiService.editProker(StaticUtils.LOGGED_BIDANG, namaBidang).enqueue(new Callback<BaseResponse<Proker>>() {
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
        String namaBidang = namaBidangField.getText().toString().toUpperCase();
        String passwordBidang = passwordBidangField.getText().toString();
        String namaKetuaBidang = namaKetuaBidangField.getText().toString();
        String[] namaPengurusBidang = namaPengurusBidangField.getText().toString().split("\n");

        apiService.editBidang(StaticUtils.LOGGED_BIDANG, namaBidang, passwordBidang, namaKetuaBidang, namaPengurusBidang).enqueue(new Callback<BaseResponse<Bidang>>() {
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

                namaBidangField.setText(bidang.namaBidang);
                passwordBidangField.setText(bidang.passwordBidang);
                namaKetuaBidangField.setText(bidang. namaKetuaBidang);
                StringBuilder sb = new StringBuilder();
                for(String s : bidang.namaPengurusBidang) {
                    sb.append(s).append("\n");
                }
                sb.delete(sb.length() - 1, sb.length());
                namaPengurusBidangField.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}
