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
    private TextView nama_proker_desc, steering_comittee_desc;
    private ImageView back_image, milestone_proker_image;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_proker_details);

        apiService = UtilsApi.getApiService();
        back_image = findViewById(R.id.backImage);
        nama_proker_desc = findViewById(R.id.namaProkerDescTextTimeplanDetails);
        steering_comittee_desc = findViewById(R.id.steeringComitteeDescTextTimeplanDetails);
        milestone_proker_image = findViewById(R.id.milestoneProkerImageTimeplanProkerDetails);

        handleGetProker();

        back_image.setOnClickListener(view -> {
            moveActivity(TimeplanCalendarActivity.class);
        });

        milestone_proker_image.setOnClickListener(view -> {
            moveActivity(TimeplanProkerMilestoneActivity.class);
        });
    }

    public void handleGetProker() {
        String nama_proker = StaticUtils.SELECTED_PROKER;

        apiService.getProker(nama_proker).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                Proker proker = res.payload;

                nama_proker_desc.setText(proker.nama_proker);
                steering_comittee_desc.setText(proker.steering_comittee);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}