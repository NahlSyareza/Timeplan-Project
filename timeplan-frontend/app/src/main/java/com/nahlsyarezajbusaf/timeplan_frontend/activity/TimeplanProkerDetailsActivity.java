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

public class TimeplanProkerDetailsActivity extends TemplateActivity {
    private TextView namaProkerDesc, steeringComitteeDesc;
    private ImageView milestoneProkerImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_proker_details);

        apiService = UtilsApi.getApiService();
        namaProkerDesc = findViewById(R.id.TimeplanProkerDetails_namaProkerDesc);
        steeringComitteeDesc = findViewById(R.id.TimeplanProkerDetails_steeringComitteeDesc);
        milestoneProkerImage = findViewById(R.id.TimeplanProkerDetails_milestoneProkerImage);

        getNamaProker();

        milestoneProkerImage.setOnClickListener(view -> {
            moveActivity(TimeplanProkerMilestoneActivity.class);
        });
    }

    public void getNamaProker() {
        String namaProker = StaticUtils.SELECTED_PROKER;

        apiService.getNamaProker(namaProker).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                Proker proker = res.payload;

                namaProkerDesc.setText(proker.nama_proker);
                steeringComitteeDesc.setText(proker.steering_comittee);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}