package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

public class MainActivity extends TemplateActivity {

    public ImageView logoBidangImage, viewTimeplanImage, addTimeplanImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = UtilsApi.getApiService();
        logoBidangImage = findViewById(R.id.Main_logoBidangImage);
        viewTimeplanImage = findViewById(R.id.Main_viewTimeplanImage);
        addTimeplanImage = findViewById(R.id.Main_addTimeplanImage);

        viewTimeplanImage.setOnClickListener(view -> {
            if(StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                viewToast("Bidang harus login!");
            } else {
                moveActivity(TimeplanCalendarActivity.class);
            }
        });

        addTimeplanImage.setOnClickListener(view -> {
            if(StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                viewToast("Bidang harus login!");
            } else {
                moveActivity(AddProkerActivity.class);
            }
        });

        if (!StaticUtils.LOGGED_BIDANG.equals("NONE")) {
            logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));
        }

        logoBidangImage.setOnClickListener(view -> {
            if (!StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                moveActivity(InfoBidangActivity.class);
            } else {
                moveActivity(LoginBidangActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}