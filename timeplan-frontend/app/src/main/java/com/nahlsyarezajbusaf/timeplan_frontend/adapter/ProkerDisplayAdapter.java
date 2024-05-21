package com.nahlsyarezajbusaf.timeplan_frontend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.activity.TimeplanProkerDetailsActivity;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProkerDisplayAdapter extends ArrayAdapter<ProkerDisplay> {
    private TextView nama_proker;
    private ImageView logo_bidang;

    public ProkerDisplayAdapter(@NonNull Context context, List<ProkerDisplay> list) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, list);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.proker_display_layout, parent, false);
        }

        ProkerDisplay proker_display = getItem(position);

        nama_proker = convertView.findViewById(R.id.namaProkerTextProkerLayout);
        logo_bidang = convertView.findViewById(R.id.logoBidangImageProkerLayout);

        nama_proker.setText(proker_display.nama_proker);
        logo_bidang.setImageResource(StaticUtils.handleBidangProfile(proker_display.nama_bidang));

        convertView.setOnClickListener(view -> {
            if(proker_display.nama_bidang.equals(StaticUtils.LOGGED_BIDANG)) {
                viewToast("Melihat proker " + proker_display.nama_proker.toString());
                StaticUtils.SELECTED_PROKER = proker_display.nama_proker;
                moveActivity(TimeplanProkerDetailsActivity.class);
            }
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
