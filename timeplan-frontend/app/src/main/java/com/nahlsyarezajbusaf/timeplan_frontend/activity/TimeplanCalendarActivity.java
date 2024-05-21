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
import com.nahlsyarezajbusaf.timeplan_frontend.model.JenisProker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
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
    private ImageView back, logo_bidang, show_owned, show_activities_color;
    private Button bulan_button, next_bulan_button, back_bulan_button;
    public Map<Bulan, Integer> month_map = new HashMap<>();
    public List<ProkerDisplay> proker_display_list = new ArrayList<>();
    private BaseApiService apiService;
    private final Bulan[] month_array = Bulan.values();
    public static boolean CONTROL_VAR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeplan_calendar);

        apiService = UtilsApi.getApiService();
        back = findViewById(R.id.backImage);
        logo_bidang = findViewById(R.id.logoBidangImageTimeplanCalendar);
        bulan_button = findViewById(R.id.bulanButtonTimeplanCalendar);
        next_bulan_button = findViewById(R.id.nextBulanButtonTimeplanCalendar);
        back_bulan_button = findViewById(R.id.backBulanButtonTimeplanCalendar);
        show_owned = findViewById(R.id.showOwnedImageTimeplanCalendar);
        show_activities_color = findViewById(R.id.showActivitiesByColorImageTimeplanCalendar);

        StaticUtils.handleMonthDates(month_map);
        logo_bidang.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));
        bulan_button.setText(month_array[StaticUtils.MONTH_ARRAY_INDEX].name());

        if (StaticUtils.SHOW_OWNED) {
            show_owned.setColorFilter(BRIGHT_COLOR);
        }

        if (StaticUtils.SHOW_ACTIVITIES_COLOR) {
            show_activities_color.setColorFilter(BRIGHT_COLOR);
        }

        show_owned.setOnClickListener(view -> {
            if (StaticUtils.SHOW_OWNED) {
                StaticUtils.SHOW_OWNED = false;
                show_owned.setColorFilter(DIM_COLOR);
                viewToast("Tampilkan semua proker");
            } else {
                StaticUtils.SHOW_OWNED = true;
                show_owned.setColorFilter(BRIGHT_COLOR);
                viewToast("Tampilkan hanya proker bidang");
            }

            moveActivity(TimeplanCalendarActivity.class);
        });

        show_activities_color.setOnClickListener(view -> {
            if (StaticUtils.SHOW_ACTIVITIES_COLOR) {
                StaticUtils.SHOW_ACTIVITIES_COLOR = false;
                show_owned.setColorFilter(DIM_COLOR);
                viewToast("Filter warna maksimal");
            } else {
                StaticUtils.SHOW_ACTIVITIES_COLOR = true;
                show_owned.setColorFilter(BRIGHT_COLOR);
                viewToast("Filter warna minimal");
            }

            moveActivity(TimeplanCalendarActivity.class);
        });

        next_bulan_button.setOnClickListener(view -> {
            if (StaticUtils.MONTH_ARRAY_INDEX < 11) {
                StaticUtils.MONTH_ARRAY_INDEX++;
            }
            StaticUtils.CURRENT_MONTH = month_array[StaticUtils.MONTH_ARRAY_INDEX];
            CONTROL_VAR = true;
            moveActivity(TimeplanCalendarActivity.class);
        });

        back_bulan_button.setOnClickListener(view -> {
            if (StaticUtils.MONTH_ARRAY_INDEX > 0) {
                StaticUtils.MONTH_ARRAY_INDEX--;
            }
            StaticUtils.CURRENT_MONTH = month_array[StaticUtils.MONTH_ARRAY_INDEX];
            CONTROL_VAR = true;
            moveActivity(TimeplanCalendarActivity.class);
        });

        back.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
        });

        logo_bidang.setOnClickListener(view -> {
            moveActivity(InfoBidangActivity.class);
        });

        handleGetProkerDisplay();

//        Log.i("CECEP", StaticUtils.CURRENT_MONTH + " " + month_map.get(StaticUtils.CURRENT_MONTH));
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

                proker_display_list.clear();
                proker_display_list = res.payload;

                ConstraintLayout layout = findViewById(R.id.timeplanCalendar);

                for (int j = 0; j < 7; j++) {
                    for (int i = 0; i < 5; i++) {
                        int index = 1 + i + (j * 5);
                        if (index < month_map.get(StaticUtils.CURRENT_MONTH) + 1) {
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

        for (ProkerDisplay pd : proker_display_list) {
            int st = getIndex(month_array, pd.bulan_start);
            int ed = getIndex(month_array, pd.bulan_end);

            if (st == StaticUtils.MONTH_ARRAY_INDEX) {
                pd.tanggal_end = 31;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (ed == StaticUtils.MONTH_ARRAY_INDEX) {
                pd.tanggal_start = 1;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (st < StaticUtils.MONTH_ARRAY_INDEX && StaticUtils.MONTH_ARRAY_INDEX < ed) {
                pd.tanggal_start = 1;
                pd.tanggal_end = 31;
                if (StaticUtils.SHOW_OWNED) {
                    if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG))
                        relevant_proker_display.add(pd);
                } else {
                    relevant_proker_display.add(pd);
                }
            } else if (st == StaticUtils.MONTH_ARRAY_INDEX && ed == StaticUtils.MONTH_ARRAY_INDEX) {
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
            sb.append(month_array[StaticUtils.MONTH_ARRAY_INDEX]).append(": ");
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
        text.setTextColor(Color.BLACK);
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

        if (image.proker_list.size() >= 3 && !StaticUtils.SHOW_ACTIVITIES_COLOR) {
            image.setColorFilter(Color.RED);
        } else if (image.proker_list.size() == 2 && !StaticUtils.SHOW_ACTIVITIES_COLOR) {
            image.setColorFilter(Color.YELLOW);
        } else if (image.proker_list.size() >= 1) {
            image.setColorFilter(Color.GREEN);
        }

        int offset_x = 10;
        int offset_y = 300;
        int gap = 190;

        image.setImageResource(R.drawable.calendar_square);
        text.setScaleX(1.0F);
        text.setScaleY(1.0F);
        image.setScaleX(3.75F);
        image.setScaleY(3.75F);
        text.setX(80 + offset_x + (gap * i));
        text.setY(1080 - offset_y + (gap * j));
        image.setX(130 + offset_x + (gap * i));
        image.setY(1125 - offset_y + (gap * j));
        layout.addView(image);
        layout.addView(text);
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

    /*
        List<ProkerDisplay> relevant_proker_display = new ArrayList<>();
        List<ProkerDisplay> proker_list = new ArrayList<>();

        for (ProkerDisplay pd : proker_display_list) {
            if (pd.bulan_start.equals(pd.bulan_end)) {
                if (pd.bulan_start.equals(StaticUtils.CURRENT_MONTH)) {
                    if (StaticUtils.SHOW_OWNED) {
                        if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG)) {
                            relevant_proker_display.add(pd);
                        }
                    } else {
                        relevant_proker_display.add(pd);
                    }
                }
            } else {
                int original_tanggal_end = pd.tanggal_end;
                int original_tanggal_start = pd.tanggal_start;

                if (pd.bulan_start.equals(StaticUtils.CURRENT_MONTH)) {
                    if (StaticUtils.SHOW_OWNED) {
                        if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG)) {
                            relevant_proker_display.add(pd);
                            pd.tanggal_start = original_tanggal_start;
                            pd.tanggal_end = 31;
                        }
                    } else {
                        relevant_proker_display.add(pd);
                        pd.tanggal_start = original_tanggal_start;
                        pd.tanggal_end = 31;
                    }
                }

                if (pd.bulan_end.equals(StaticUtils.CURRENT_MONTH)) {
                    if (StaticUtils.SHOW_OWNED) {
                        if (pd.nama_bidang.equals(StaticUtils.LOGGED_BIDANG)) {
                            relevant_proker_display.add(pd);
                            pd.tanggal_start = 1;
                            pd.tanggal_end = original_tanggal_end;
                        }
                    } else {
                        relevant_proker_display.add(pd);
                        pd.tanggal_start = 1;
                        pd.tanggal_end = original_tanggal_end;
                    }
                }
            }
        }

        for (ProkerDisplay pd : relevant_proker_display) {
            if (index >= pd.tanggal_start && index <= pd.tanggal_end) {
                proker_list.add(pd);
            }
        }

        TextView text = new TextView(this);
        text.setText(String.valueOf(index));
        text.setTextColor(Color.BLACK);
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

        if (image.proker_list.size() >= 3) {
            image.setColorFilter(Color.RED);
        } else if (image.proker_list.size() == 2) {
            image.setColorFilter(Color.YELLOW);
        } else if (image.proker_list.size() == 1){
            image.setColorFilter(Color.GREEN);
        }

        int offset_x = 10;
        int offset_y = 300;
        int gap = 190;

        image.setImageResource(R.drawable.calendar_square);
        text.setScaleX(1.0F);
        text.setScaleY(1.0F);
        image.setScaleX(3.75F);
        image.setScaleY(3.75F);
        text.setX(80 + offset_x + (gap * i));
        text.setY(1080 - offset_y + (gap * j));
        image.setX(130 + offset_x + (gap * i));
        image.setY(1125 - offset_y + (gap * j));
        layout.addView(image);
        layout.addView(text);
     */
}