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
import com.nahlsyarezajbusaf.timeplan_frontend.activity.ProkerDetailsActivity;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.List;

/**
 * Displays proker and navigates to the details page if clicked
 *
 */
public class ProkerDisplayAdapter extends ArrayAdapter<ProkerDisplay> {
    private TextView namaProkerText;
    private ImageView logoBidangImage;

    public ProkerDisplayAdapter(@NonNull Context context, List<ProkerDisplay> list) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, list);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_proker_display_layout, parent, false);
        }

        ProkerDisplay prokerDisplay = getItem(position);

        namaProkerText = convertView.findViewById(R.id.ProkerDisplayLayout_namaProkerText);
        logoBidangImage = convertView.findViewById(R.id.ProkerDisplayLayout_logoBidangImage);

        String namaProker = prokerDisplay.namaProker;
        String namaBidang = prokerDisplay.namaBidang;

        namaProkerText.setText(prokerDisplay.namaProker);
        logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(prokerDisplay.namaBidang));

        convertView.setOnClickListener(view -> {
            if (namaBidang.equals(StaticUtils.LOGGED_BIDANG) || StaticUtils.LOGGED_BIDANG.equals("KETULEM") || StaticUtils.LOGGED_BIDANG.equals("KESTARI")) {
                viewToast("Melihat proker " + namaProker);
                StaticUtils.SELECTED_PROKER_NAMA = namaProker;
                moveActivity(ProkerDetailsActivity.class);
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
