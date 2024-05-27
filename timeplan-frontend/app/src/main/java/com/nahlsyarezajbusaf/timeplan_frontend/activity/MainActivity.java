package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

public class MainActivity extends TemplateActivity {

    public ImageView logoBidangImage, viewTimeplanImage, addTimeplanImage, chatImage;
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
        chatImage = findViewById(R.id.Main_chatImage);

        if(INITIAL) {
            INITIAL = false;
//            handleBackgroundSong(this);
        }

        viewTimeplanImage.setOnClickListener(view -> {
            switch (StaticUtils.LOGGED_BIDANG){
                case "NONE":
                    viewToast("Bidang harus login!");
                    break;

                default:
                    moveActivity(TimeplanCalendarActivity.class);
                    break;
            }
        });

        chatImage.setOnClickListener(view -> {
            switch (StaticUtils.LOGGED_BIDANG) {
                case "NONE":
                    viewToast("Bidang harus login!");
                    break;

                default:
                    viewToast("You want to play? Let's play!");
                    break;
            }
        });

        addTimeplanImage.setOnClickListener(view -> {
            switch (StaticUtils.LOGGED_BIDANG) {
                case "NONE":
                    viewToast("Bidang harus login!");
                    break;

                case "KETULEM":
                    viewToast("KETULEM tidak memiliki proker!");
                    break;

                default:
                    moveActivity(AddProkerActivity.class);
                    break;
            }
        });

        if (!StaticUtils.LOGGED_BIDANG.equals("NONE")) {
            logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));
        }

        logoBidangImage.setOnClickListener(view -> {
            switch (StaticUtils.LOGGED_BIDANG) {
                case "NONE":
                    moveActivity(LoginBidangActivity.class);
                    break;

                default:
                    moveActivity(BidangDetailsActivity.class);
                    break;
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