package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.io.IOException;

public class MainActivity extends TemplateActivity {

    public ImageView logoBidangImage, viewTimeplanImage, addTimeplanImage;
    private BaseApiService apiService;
    private static boolean INITIAL = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = UtilsApi.getApiService();
        logoBidangImage = findViewById(R.id.Main_logoBidangImage);
        viewTimeplanImage = findViewById(R.id.Main_viewTimeplanImage);
        addTimeplanImage = findViewById(R.id.Main_addTimeplanImage);

        if(INITIAL) {
            INITIAL = false;
//            handleBackgroundSong(this);
        }

        viewTimeplanImage.setOnClickListener(view -> {
            if (StaticUtils.LOGGED_BIDANG.equals("NONE")) {
                viewToast("Bidang harus login!");
            } else {
                moveActivity(TimeplanCalendarActivity.class);
            }
        });

        addTimeplanImage.setOnClickListener(view -> {
            if (StaticUtils.LOGGED_BIDANG.equals("NONE")) {
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

    public void handleBackgroundSong(Context ctx) {
        MediaPlayer mediaPlayer = MediaPlayer.create(ctx, R.raw.orch_compression);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.45F, 0.45F);
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}