package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.MilestoneAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Milestone;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Shows the milestone of a proker
 *
 */
public class ProkerMilestoneActivity extends TemplateActivity {

    private ImageView addMilestone;
    private ListView milestoneList;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proker_milestone);

        apiService = UtilsApi.getApiService();
        milestoneList = findViewById(R.id.ProkerMilestone_milestoneList);
        addMilestone = findViewById(R.id.ProkerMilestoneaddMilestoneImage);

        handleGetProkerMilestone();

        addMilestone.setOnClickListener(view -> {
            moveActivity(AddMilestoneActivity.class);
        });
    }

    /**
     * Gets the already available milestones of a proker
     *
     */
    public void handleGetProkerMilestone() {
        String namaProker = StaticUtils.SELECTED_PROKER_NAMA;

        apiService.getProkerMilestone(namaProker).enqueue(new Callback<BaseResponse<List<Milestone>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Milestone>>> call, Response<BaseResponse<List<Milestone>>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<List<Milestone>> res = response.body();
                List<Milestone> list = res.payload;

                milestoneList.setAdapter(new MilestoneAdapter<>(ctx, list));

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Milestone>>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}