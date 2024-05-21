package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.SimpleAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

public class TimeplanProkerMilestoneActivity extends TemplateActivity {

    private ImageView addMilestone;
    private ListView milestoneList;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_proker_milestone);

        apiService = UtilsApi.getApiService();
        milestoneList = findViewById(R.id.TimeplanProkerMilestone_milestoneList);
        addMilestone = findViewById(R.id.TimeplanProkerMilestoneaddMilestoneImage);

        List<String> list = new ArrayList<>();
        list.add("Nahl");
        list.add("Chamber");
        list.add("Tour de Force");
        list.add("Headhunter");
        list.add("Rendezvous");
        list.add("Neural Theft");
        list.add("Cyber Cage");
        milestoneList.setAdapter(new SimpleAdapter<>(this, list));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveActivity(PREVIOUS_CLASS);
    }
}