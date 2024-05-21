package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.adapter.SimpleAdapter;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.model.JenisProker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProkerActivity extends TemplateActivity {
    private EditText nama_proker_field;
    private Spinner nama_pengurus_spinner, jenis_proker_spinner;
    private Button add_button;
    private ImageView back_image, pilih_jadwal_image;
    private BaseApiService apiService;
    private Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_proker);

        apiService = UtilsApi.getApiService();
        nama_proker_field = findViewById(R.id.namaProkerFieldAddProker);
        back_image = findViewById(R.id.backImage);
        pilih_jadwal_image = findViewById(R.id.pilihJadwalImageAddProker);
        add_button = findViewById(R.id.addButtonAddProker);
        nama_pengurus_spinner = findViewById(R.id.namaPengurusSpinnerAddProker);
        jenis_proker_spinner = findViewById(R.id.jenisProkerSpinnerAddProker);

        handleGetBidang();

        JenisProker jenis_proker_array[] = JenisProker.values();
        List<String> jenis_proker_list = new ArrayList<>();
        for(JenisProker jp : jenis_proker_array) {
            String new_name = jp.name().replace("_", " ");
            jenis_proker_list.add(new_name);
        }

        jenis_proker_spinner.setAdapter(new SimpleAdapter<>(this, jenis_proker_list));

        pilih_jadwal_image.setOnClickListener(view -> {
            String nama_proker = nama_proker_field.getText().toString();
            if (nama_proker.isEmpty()) {
                viewToast("Nama proker tidak boleh kosong");
            } else {
                StaticUtils.TEMP_NAMA_PROKER = nama_proker_field.getText().toString();
                StaticUtils.SELECT_EXECUTION_MODE = true;
                moveActivity(TimeplanCalendarActivity.class);
            }
        });

        if (!StaticUtils.TEMP_NAMA_PROKER.isEmpty()) {
            nama_proker_field.setText(StaticUtils.TEMP_NAMA_PROKER);
        }

        back_image.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
        });

        add_button.setOnClickListener(view -> {
            StaticUtils.TEMP_NAMA_PROKER = "";
            handleAddProker();
            moveActivity(MainActivity.class);
        });
    }

    public void handleGetBidang() {
        String nama_bidang = StaticUtils.LOGGED_BIDANG;

        apiService.getBidang(nama_bidang).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();
                Bidang bidang = res.payload;
                List<String> list = new ArrayList<>();
                list.add(bidang.nama_ketua_bidang);
                for(String s : bidang.nama_pengurus_bidang) {
                    list.add(s);
                }
                nama_pengurus_spinner.setAdapter(new SimpleAdapter(ctx, list));
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void handleAddProker() {
        String nama_bidang = StaticUtils.LOGGED_BIDANG;
        String nama_proker = nama_proker_field.getText().toString();
        String steering_comittee = (String) nama_pengurus_spinner.getSelectedItem();
        String jenis_proker = (String) jenis_proker_spinner.getSelectedItem();
        String new_jenis_proker = jenis_proker.replace(" ", "_");

        apiService.addProker(nama_bidang, nama_proker, steering_comittee, JenisProker.valueOf(new_jenis_proker), StaticUtils.SELECTED_TANGGAL_START, StaticUtils.SELECTED_BULAN_START, StaticUtils.SELECTED_TANGGAL_END, StaticUtils.SELECTED_BULAN_END).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if (!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}