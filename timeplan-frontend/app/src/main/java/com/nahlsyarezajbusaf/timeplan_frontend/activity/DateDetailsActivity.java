package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.ProkerDisplayAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

/**
 * See what proker is running on a specified date!
 *
 */
public class DateDetailsActivity extends TemplateActivity {
    private ListView prokerList;
    private BaseApiService apiService;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_details);

        apiService = UtilsApi.getApiService();
        prokerList = findViewById(R.id.DateDetails_prokerList);

        prokerList.setAdapter(new ProkerDisplayAdapter(this, StaticUtils.ICBM_PROKER_DISPLAY_LIST));
    }
}