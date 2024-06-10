package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Milestone;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Creates a new milestone with a name
 *
 */
public class AddMilestoneActivity extends TemplateActivity {

    private EditText namaMilestoneField;
    private Button addButton;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milestone);

        apiService = UtilsApi.getApiService();
        namaMilestoneField = findViewById(R.id.AddMilestone_namaMilestoneField);
        addButton = findViewById(R.id.AddMilestone_addButton);

        addButton.setOnClickListener(view -> {
            String namaMilestone = namaMilestoneField.getText().toString();

            handleAddProkerMilestone(StaticUtils.SELECTED_PROKER_NAMA, namaMilestone);

            moveActivity(ProkerMilestoneActivity.class);
        });

    }

    /**
     * Shoots to the backend to add a new milestone to a specified proker
     *
     * @param namaProker
     * @param namaMilestone
     */
    public void handleAddProkerMilestone(String namaProker, String namaMilestone) {
        apiService.addProkerMilestone(namaProker, namaMilestone).enqueue(new Callback<BaseResponse<Milestone>>() {
            @Override
            public void onResponse(Call<BaseResponse<Milestone>> call, Response<BaseResponse<Milestone>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Milestone> res = response.body();

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Milestone>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}