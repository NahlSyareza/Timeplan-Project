package com.nahlsyarezajbusaf.timeplan_frontend.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.activity.ProkerMilestoneDetailsActivity;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Milestone;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Status;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.List;

import retrofit2.http.POST;

/**
 * Custom adapter for milestone
 * Navigates to a page when clicked
 *
 * @param <T>
 */
public class MilestoneAdapter<T extends Milestone> extends ArrayAdapter<T> {

    private TextView namaMilestoneText;
    private ImageView progresMilestoneImage;
    private BaseApiService apiService = UtilsApi.getApiService();

    public MilestoneAdapter(@NonNull Context context, List<T> list) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, list);
    }

    public MilestoneAdapter(@NonNull Context context, T[] array) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, array);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_milestone_layout, parent, false);
        }

        Milestone milestone = getItem(position);

        namaMilestoneText = convertView.findViewById(R.id.MilestoneLayout_namaMilestone);
        progresMilestoneImage = convertView.findViewById(R.id.MilestoneLayout_progresMilestoneImage);

        String namaMilestone = milestone.namaMilestone;
        String deskripsiMilestone = milestone.deskripsiMilestone;
        Status progresMilestone = milestone.progresMilestone;

        switch (progresMilestone) {
            case FINISH:
                progresMilestoneImage.setImageResource(R.drawable.baseline_check_24);
                progresMilestoneImage.setColorFilter(Color.rgb(0, 255, 0));
                break;

            default:
                progresMilestoneImage.setImageResource(R.drawable.baseline_clear_24);
                progresMilestoneImage.setColorFilter(Color.rgb(255, 0, 0));
                break;
        }

        namaMilestoneText.setText(namaMilestone);
        namaMilestoneText.setOnClickListener(view -> {
            StaticUtils.SELECTED_MILESTONE_NAMA = namaMilestone;
            StaticUtils.SELECTED_MILESTONE_DESKRIPSI = deskripsiMilestone;
            StaticUtils.SELECTED_MILESTONE_PROGRES = progresMilestone;
            moveActivity(ProkerMilestoneDetailsActivity.class);
//            Log.i("CECEP", "You are currently clicking on " + position);
        });

        return convertView;
    }

    protected void viewToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void moveActivity(Class<?> classis) {
        Intent intent = new Intent(getContext(), classis);
        getContext().startActivity(intent);
    }
}
