package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Milestone;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Status;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProkerMilestoneDetailsActivity extends TemplateActivity {

    private BaseApiService apiService;
    private TextView namaMilestoneText;
    private EditText deskripsiMilestoneField;
    private ImageView progresMilestoneImage, deleteMilestoneImage;
    private Status currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proker_milestone_details);

        apiService = UtilsApi.getApiService();
        namaMilestoneText = findViewById(R.id.ProkerMilestoneDetails_namaMilestoneText);
        deskripsiMilestoneField = findViewById(R.id.ProkerMilestoneDetails_deskripsiMilestoneField);
        progresMilestoneImage = findViewById(R.id.ProkerMilestoneDetails_progresMilestoneImage);
        deleteMilestoneImage = findViewById(R.id.ProkerMilestoneDetails_deleteMilestoneImage);

        deskripsiMilestoneField.setText(StaticUtils.SELECTED_MILESTONE_DESKRIPSI);

        currentStatus = StaticUtils.SELECTED_MILESTONE_PROGRES;

        switch (currentStatus) {
            case FINISH:
                progresMilestoneImage.setImageResource(R.drawable.baseline_check_24);
                progresMilestoneImage.setColorFilter(Color.rgb(0, 255, 0));
                break;

            default:
                progresMilestoneImage.setImageResource(R.drawable.baseline_clear_24);
                progresMilestoneImage.setColorFilter(Color.rgb(255, 0, 0));
                break;
        }

        deleteMilestoneImage.setOnClickListener(view -> {
            viewToast("Tour de Force");
        });

        progresMilestoneImage.setOnClickListener(view -> {
            switch (currentStatus) {
                case FINISH:
                    progresMilestoneImage.setImageResource(R.drawable.baseline_clear_24);
                    progresMilestoneImage.setColorFilter(Color.rgb(255, 0, 0));
                    currentStatus = Status.START;
                    break;

                default:
                    progresMilestoneImage.setImageResource(R.drawable.baseline_check_24);
                    progresMilestoneImage.setColorFilter(Color.rgb(0, 255, 0));
                    currentStatus = Status.FINISH;
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handleEditProkerMilestone();
    }

    public void handleEditProkerMilestone() {
        String namaProker = StaticUtils.SELECTED_PROKER;
        String namaMilestone = StaticUtils.SELECTED_MILESTONE_NAMA;
        Status progresMilestone = currentStatus;
        String deskripsiMilestone = deskripsiMilestoneField.getText().toString();
        apiService.editProkerMilestone(namaProker, namaMilestone, progresMilestone, deskripsiMilestone).enqueue(new Callback<BaseResponse<Milestone>>() {
            @Override
            public void onResponse(Call<BaseResponse<Milestone>> call, Response<BaseResponse<Milestone>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Milestone> res = response.body();
                Milestone milestone = res.payload;
                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Milestone>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}