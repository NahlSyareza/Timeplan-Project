package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends TemplateActivity {

    public ImageView logo_bidang, view_timeplan, add_timeplan;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = UtilsApi.getApiService();
        logo_bidang = findViewById(R.id.logoBidangImageMain);
        view_timeplan = findViewById(R.id.viewTimeplanImageMain);
        add_timeplan = findViewById(R.id.addTimeplanImageMain);

        view_timeplan.setOnClickListener(view -> {
            if(StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                viewToast("Bidang harus login!");
            } else {
                moveActivity(TimeplanCalendarActivity.class);
            }
        });

        add_timeplan.setOnClickListener(view -> {
            if(StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                viewToast("Bidang harus login!");
            } else {
                moveActivity(AddProkerActivity.class);
            }
        });

        if (!StaticUtils.LOGGED_BIDANG.equals("NONE")) {
            logo_bidang.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));
        }

        logo_bidang.setOnClickListener(view -> {
            if (!StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                moveActivity(InfoBidangActivity.class);
            } else {
                moveActivity(LoginBidangActivity.class);
            }
        });
    }
}