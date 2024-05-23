package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProkerBidangActivity extends TemplateActivity {

    private BaseApiService apiService;
    private List<Proker> prokerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_proker_bidang);

        apiService = UtilsApi.getApiService();

        handleGetBidangProker();

        viewToast("Outside size : " + prokerList.size());
    }

    public void handleGetBidangProker() {
        apiService.getBidangProker(StaticUtils.LOGGED_BIDANG).enqueue(new Callback<BaseResponse<List<Proker>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Proker>>> call, Response<BaseResponse<List<Proker>>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<List<Proker>> res = response.body();
                prokerList = res.payload;
                viewToast("Inside size : " + prokerList.size());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Proker>>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}