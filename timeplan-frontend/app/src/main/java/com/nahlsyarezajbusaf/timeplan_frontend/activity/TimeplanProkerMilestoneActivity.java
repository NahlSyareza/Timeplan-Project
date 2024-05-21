package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

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

    private ImageView back_image, add_milestone;
    private ListView milestone_list;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_proker_milestone);

        apiService = UtilsApi.getApiService();
        back_image = findViewById(R.id.backImage);
        milestone_list = findViewById(R.id.milestoneListTimeplanProkerMilestone);
        add_milestone = findViewById(R.id.addMilestoneImageTimeplanProkerMilestone);

        List<String> list = new ArrayList<>();
        list.add("Nahl");
        list.add("Chamber");
        list.add("Tour de Force");
        list.add("Headhunter");
        list.add("Rendezvous");
        list.add("Neural Theft");
        list.add("Cyber Cage");
        milestone_list.setAdapter(new SimpleAdapter<>(this, list));

        back_image.setOnClickListener(view -> {
            moveActivity(PREVIOUS_CLASS);
        });
    }
}