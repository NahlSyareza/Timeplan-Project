package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.IdentityImageView;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeplanCalendarActivity extends TemplateActivity {

    private static final int DIM_COLOR = Color.rgb(153, 115, 2);
    private static final int BRIGHT_COLOR = Color.rgb(255, 193, 7);
    private ImageView logoBidangImage, showOwnedImage, showActivitiesByColorImage;
    private Button bulanButton, nextBulanButton, backBulanButton;
    public Map<Bulan, Integer> bulanMap = new HashMap<>();
    public List<ProkerDisplay> prokderDisplayList = new ArrayList<>();
    private BaseApiService apiService;
    private final Bulan[] monthArray = Bulan.values();
    public static boolean CONTROL_VAR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_calendar);

        apiService = UtilsApi.getApiService();
        logoBidangImage = findViewById(R.id.TimeplanCalendar_logoBidangImage);
        bulanButton = findViewById(R.id.TimeplanCalendar_bulanButton);
        nextBulanButton = findViewById(R.id.TimeplanCalendar_nextBulanButton);
        backBulanButton = findViewById(R.id.TimeplanCalendar_backBulanButton);
        showOwnedImage = findViewById(R.id.TimeplanCalendar_showOwnedImage);
        showActivitiesByColorImage = findViewById(R.id.TimeplanCalendar_showActivitiesByColorImage);

        StaticUtils.handleMonthDates(bulanMap);
        logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));
        bulanButton.setText(monthArray[StaticUtils.CURRENT_BULAN_INDEX].name());

        if (StaticUtils.SHOW_OWNED) {
            showOwnedImage.setColorFilter(BRIGHT_COLOR);
        }

        if (StaticUtils.SHOW_ACTIVITIES_COLOR) {
            showActivitiesByColorImage.setColorFilter(BRIGHT_COLOR);
        }

        showOwnedImage.setOnClickListener(view -> {
            if (StaticUtils.SHOW_OWNED) {
                StaticUtils.SHOW_OWNED = false;
                showOwnedImage.setColorFilter(DIM_COLOR);
                viewToast("Tampilkan semua proker");
            } else {
                StaticUtils.SHOW_OWNED = true;
                showOwnedImage.setColorFilter(BRIGHT_COLOR);
                viewToast("Tampilkan hanya proker bidang");
            }

            moveActivity(TimeplanCalendarActivity.class);
        });

        showActivitiesByColorImage.setOnClickListener(view -> {
            if (StaticUtils.SHOW_ACTIVITIES_COLOR) {
                StaticUtils.SHOW_ACTIVITIES_COLOR = false;
                showOwnedImage.setColorFilter(DIM_COLOR);
                viewToast("Filter warna maksimal");
            } else {
                StaticUtils.SHOW_ACTIVITIES_COLOR = true;
                showOwnedImage.setColorFilter(BRIGHT_COLOR);
                viewToast("Filter warna minimal");
            }

            moveActivity(TimeplanCalendarActivity.class);
        });

        nextBulanButton.setOnClickListener(view -> {
            if (StaticUtils.CURRENT_BULAN_INDEX < 11) {
                StaticUtils.CURRENT_BULAN_INDEX++;
            }
            StaticUtils.CURRENT_MONTH = monthArray[StaticUtils.CURRENT_BULAN_INDEX];
            CONTROL_VAR = true;
            moveActivity(TimeplanCalendarActivity.class);
        });

        backBulanButton.setOnClickListener(view -> {
            if (StaticUtils.CURRENT_BULAN_INDEX > 0) {
                StaticUtils.CURRENT_BULAN_INDEX--;
            }
            StaticUtils.CURRENT_MONTH = monthArray[StaticUtils.CURRENT_BULAN_INDEX];
            CONTROL_VAR = true;
            moveActivity(TimeplanCalendarActivity.class);
        });

        logoBidangImage.setOnClickListener(view -> {
            moveActivity(InfoBidangActivity.class);
        });

        handleGetProkerDisplay();
    }

    public void handleGetProkerDisplay() {
        apiService.getProkerDisplay().enqueue(new Callback<BaseResponse<List<ProkerDisplay>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ProkerDisplay>>> call, Response<BaseResponse<List<ProkerDisplay>>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<List<ProkerDisplay>> res = response.body();

                prokderDisplayList.clear();
                prokderDisplayList = res.payload;

                ConstraintLayout layout = findViewById(R.id.timeplanCalendar);

                for (int j = 0; j < 7; j++) {
                    for (int i = 0; i < 5; i++) {
                        int index = 1 + i + (j * 5);
                        if (index < bulanMap.get(StaticUtils.CURRENT_MONTH) + 1) {
                            printCalendarBox(layout, index, i, j);
                        } else {
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ProkerDisplay>>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void printCalendarBox(ConstraintLayout layout, int index, int i, int j) {
        List<ProkerDisplay> relevant_proker_display = new ArrayList<>();
        List<ProkerDisplay> proker_list = new ArrayList<>();

        for (ProkerDisplay pd : prokderDisplayList) {
            int st = getIndex(monthArray, pd.bulan_start);
            int ed = getIndex(monthArray, pd.bulan_end);

            if (st == StaticUtils.CURRENT_BULAN_INDEX && ed == StaticUtils.CURRENT_BULAN_INDEX) {
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (st == StaticUtils.CURRENT_BULAN_INDEX) {
                pd.tanggal_end = 31;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (ed == StaticUtils.CURRENT_BULAN_INDEX) {
                pd.tanggal_start = 1;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (st < StaticUtils.CURRENT_BULAN_INDEX && StaticUtils.CURRENT_BULAN_INDEX < ed) {
                pd.tanggal_start = 1;
                pd.tanggal_end = 31;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            }
        }

        if (CONTROL_VAR) {
            StringBuilder sb = new StringBuilder();
            sb.append(monthArray[StaticUtils.CURRENT_BULAN_INDEX]).append(": ");
            for (ProkerDisplay pd : relevant_proker_display) {
                sb.append(pd.nama_proker).append(" (").append(pd.tanggal_start).append(" - ").append(pd.tanggal_end).append("), ");
            }
            sb.delete(sb.length() - 2, sb.length());
            Log.i("CECEP", sb.toString());
            CONTROL_VAR = false;
        }

        for (ProkerDisplay pd : relevant_proker_display) {
            if (index >= pd.tanggal_start && index <= pd.tanggal_end) {
                proker_list.add(pd);
            }
        }

        TextView text = new TextView(this);
        text.setText(String.valueOf(index));
        text.setTextColor(Color.rgb(255, 193, 7));
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        IdentityImageView image = new IdentityImageView(index, this, proker_list);
        image.setOnClickListener(view -> {
            if (!StaticUtils.SELECT_EXECUTION_MODE) {
                StaticUtils.ICBM_PROKER_DISPLAY_LIST = image.proker_list;
                moveActivity(TimeplanDateDetailsActivity.class);
            } else {
                if (StaticUtils.SELECT_EXECUTION_STATE == 0) {
                    StaticUtils.SELECTED_BULAN_START = StaticUtils.CURRENT_MONTH;
                    StaticUtils.SELECTED_TANGGAL_START = index;
                    StaticUtils.SELECT_EXECUTION_STATE++;
                    image.setColorFilter(Color.GRAY);
                } else {
                    StaticUtils.SELECTED_BULAN_END = StaticUtils.CURRENT_MONTH;
                    StaticUtils.SELECTED_TANGGAL_END = index;
                    image.setColorFilter(Color.GRAY);
                    StaticUtils.SELECT_EXECUTION_STATE = 0;
                    StaticUtils.SELECT_EXECUTION_MODE = false;
                    moveActivity(AddProkerActivity.class);
                }

            }
        });

        int txtOffsetX = 10;
        int txtOffsetY = 300;
        int txtGap = 190;
        int imgOffsetX = -60;
        int imgOffsetY = 350;
        int imgGap = 190;

        image.setImageResource(R.drawable.rounded_rectangle_background);
//        image.setMinimumHeight(180);
//        image.setMinimumWidth(180);
        text.setScaleX(1.0F);
        text.setScaleY(1.0F);
        float imageScale = 1.0F;
        image.setScaleX(imageScale);
        image.setScaleY(imageScale);
        text.setX(80 + txtOffsetX + (txtGap * i));
        text.setY(1080 - txtOffsetY + (txtGap * j));
        image.setX(130 + imgOffsetX + (imgGap * i));
        image.setY(1125 - imgOffsetY + (imgGap * j));
        layout.addView(image);
        layout.addView(text);

        if (image.proker_list.size() >= 3 && !StaticUtils.SHOW_ACTIVITIES_COLOR) {
            text.setTextColor(Color.rgb(27, 27, 27));
            image.setImageResource(R.drawable.level_3_critical);
        } else if (image.proker_list.size() == 2 && !StaticUtils.SHOW_ACTIVITIES_COLOR) {
            text.setTextColor(Color.rgb(27, 27, 27));
            image.setImageResource(R.drawable.level_2_warning);
        } else if (image.proker_list.size() >= 1) {
            text.setTextColor(Color.rgb(27, 27, 27));
            image.setImageResource(R.drawable.level_1_alert);
        }
    }

    public static <T> int getIndex(T[] array, T find) {
        int index = 0;
        for (T t : array) {
            if (t.equals(find)) {
                break;
            }
            index++;
        }

        return index;
    }
}