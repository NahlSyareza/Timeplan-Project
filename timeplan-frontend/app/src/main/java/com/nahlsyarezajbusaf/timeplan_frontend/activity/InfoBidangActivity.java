package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.JenisProker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoBidangActivity extends TemplateActivity {

    private TextView nama_bidang_desc, nama_ketua_bidang_desc, nama_pengurus_bidang_desc;
    private ImageView logo_bidang, back_image, logout, list_proker, edit_bidang_image;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bidang);

        apiService = UtilsApi.getApiService();
        logo_bidang = findViewById(R.id.logoBidangImageInfoBidang);
        back_image = findViewById(R.id.backImage);
        nama_bidang_desc = findViewById(R.id.namaBidangDescTextInfoBidang);
        nama_ketua_bidang_desc = findViewById(R.id.namaKetuaBidangDescTextInfoBidang);
        nama_pengurus_bidang_desc = findViewById(R.id.namaPengurusBidangDescTextInfoBidang);
        logout = findViewById(R.id.logoutImageInfoBidang);
        list_proker = findViewById(R.id.listProkerImageInfoBidang);
        edit_bidang_image = findViewById(R.id.editBidangImageInfoBidang);

        handleGetBidang();

        logo_bidang.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));

        edit_bidang_image.setOnClickListener(view -> {
            moveActivity(EditBidangActivity.class);
        });

        list_proker.setOnClickListener(view -> {
        });

        logout.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
            StaticUtils.LOGGED_BIDANG = "NONE";
            viewToast("Bidang berhasil logout!");
        });

        back_image.setOnClickListener(view -> {
            moveActivity(PREVIOUS_CLASS);
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

                String[] arr = res.payload.nama_pengurus_bidang;
                StringBuilder sb = new StringBuilder();

                for (String s : arr) {
                    sb.append(s).append(", ");
                }

                sb.delete(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 1);

                nama_bidang_desc.setText(res.payload.nama_bidang);
                nama_ketua_bidang_desc.setText(res.payload.nama_ketua_bidang);
                nama_pengurus_bidang_desc.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}