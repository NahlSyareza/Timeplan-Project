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

/**
 * Shows the details of a proker such as steering comitte, name, and it's milestones
 *
 */
public class ProkerDetailsActivity extends TemplateActivity {
    private TextView namaProkerDesc, steeringComitteeDesc, jenisProkerDesc;
    private ImageView milestoneProkerImage, deleteProkerImage;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proker_details);

        apiService = UtilsApi.getApiService();
        namaProkerDesc = findViewById(R.id.ProkerDetails_namaProkerDesc);
        steeringComitteeDesc = findViewById(R.id.ProkerDetails_steeringComitteeDesc);
        milestoneProkerImage = findViewById(R.id.ProkerDetails_milestoneProkerImage);
        deleteProkerImage = findViewById(R.id.ProkerDetails_deleteProkerImage);
        jenisProkerDesc = findViewById(R.id.ProkerDetails_jenisProkerDesc);

        getNamaProker();

        deleteProkerImage.setOnClickListener(view -> {
            handleDeleteProker();
            moveActivity(MainActivity.class);
        });

        milestoneProkerImage.setOnClickListener(view -> {
            moveActivity(ProkerMilestoneActivity.class);
        });
    }

    /**
     * Can delete a proker from this page
     *
     */
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

    /**
     * Gets the clicked proker
     *
     */
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
                jenisProkerDesc.setText(proker.jenisProker.name().replace("_", " "));
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}