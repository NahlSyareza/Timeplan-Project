package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.ProkerDisplayAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeplanDateDetailsActivity extends TemplateActivity {
    private ListView proker_list;
    private ImageView back_image;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_date_details);

        apiService = UtilsApi.getApiService();
        proker_list = findViewById(R.id.prokerListTimeplanDateDetails);
        back_image = findViewById(R.id.backImage);

        back_image.setOnClickListener(view -> {
            moveActivity(TimeplanCalendarActivity.class);
        });

        proker_list.setAdapter(new ProkerDisplayAdapter(this, StaticUtils.ICBM_PROKER_DISPLAY_LIST));
    }
}