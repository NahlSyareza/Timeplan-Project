package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.ProkerDisplayAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
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
    private ListView listProkerList;
    private Context ctx = this;
    private List<ProkerDisplay> prokerDisplayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_proker_bidang);

        apiService = UtilsApi.getApiService();
        listProkerList = findViewById(R.id.ListProkerBidang_listProkerList);

        handleGetBidangProker();
    }

    public void handleGetBidangProker() {
        apiService.getBidangProker(StaticUtils.LOGGED_BIDANG).enqueue(new Callback<BaseResponse<List<ProkerDisplay>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ProkerDisplay>>> call, Response<BaseResponse<List<ProkerDisplay>>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<List<ProkerDisplay>> res = response.body();
                prokerDisplayList = res.payload;

                listProkerList.setAdapter(new ProkerDisplayAdapter(ctx, prokerDisplayList));
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ProkerDisplay>>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}