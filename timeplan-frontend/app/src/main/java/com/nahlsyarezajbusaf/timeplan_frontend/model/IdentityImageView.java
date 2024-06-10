package com.nahlsyarezajbusaf.timeplan_frontend.model;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom image view to render the calendar box
 * Also holds the proker that is runnning in that tanggal
 *
 */
public class IdentityImageView extends androidx.appcompat.widget.AppCompatImageView {

    public List<ProkerDisplay> prokerDisplayList;
    public int index;

    public IdentityImageView(int index, @NonNull Context context, List<ProkerDisplay> prokerDisplayList) {
        super(context);
        this.index = index;
        this.prokerDisplayList = prokerDisplayList;
    }
}
