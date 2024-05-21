package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.ProkerDisplayAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

public class TimeplanDateDetailsActivity extends TemplateActivity {
    private ListView prokerList;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_date_details);

        apiService = UtilsApi.getApiService();
        prokerList = findViewById(R.id.TimeplanDateDetails_prokerList);

        prokerList.setAdapter(new ProkerDisplayAdapter(this, StaticUtils.ICBM_PROKER_DISPLAY_LIST));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveActivity(TimeplanCalendarActivity.class);
    }
}