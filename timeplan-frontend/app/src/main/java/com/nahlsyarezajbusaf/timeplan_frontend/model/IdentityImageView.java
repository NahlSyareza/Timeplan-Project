package com.nahlsyarezajbusaf.timeplan_frontend.model;

import android.content.Context;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.List;

public class IdentityImageView extends AppCompatImageView {

    public final int id;
    public List<ProkerDisplay> proker_list;

    public IdentityImageView(int id, Context context, List<ProkerDisplay> proker_list) {
        super(context);
        this.id = id;
        this.proker_list = proker_list;
    }
}
