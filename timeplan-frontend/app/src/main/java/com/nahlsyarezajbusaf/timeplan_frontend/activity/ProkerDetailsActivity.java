package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProkerDetailsActivity extends TemplateActivity {
    private TextView namaProkerDesc, steeringComitteeDesc;
    private ImageView milestoneProkerImage, deleteProkerImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proker_details);

        apiService = UtilsApi.getApiService();
        namaProkerDesc = findViewById(R.id.ProkerDetails_namaProkerDesc);
        steeringComitteeDesc = findViewById(R.id.ProkerDetails_steeringComitteeDesc);
        milestoneProkerImage = findViewById(R.id.ProkerDetails_milestoneProkerImage);
        deleteProkerImage = findViewById(R.id.ProkerDetails_deleteProkerImage);

        getNamaProker();

        deleteProkerImage.setOnClickListener(view -> {
            handleDeleteProker();
            moveActivity(MainActivity.class);
        });

        milestoneProkerImage.setOnClickListener(view -> {
            moveActivity(ProkerMilestoneActivity.class);
        });
    }

    public void handleDeleteProker() {
        String namaBidang = StaticUtils.LOGGED_BIDANG;
        String namaProker = StaticUtils.SELECTED_PROKER_NAMA;
        apiService.deleteProker(namaBidang, namaProker).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }


    public void getNamaProker() {
        String namaProker = StaticUtils.SELECTED_PROKER_NAMA;

        apiService.getNamaProker(namaProker).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                Proker proker = res.payload;

                namaProkerDesc.setText(proker.namaProker);
                steeringComitteeDesc.setText(proker.steeringComittee);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}