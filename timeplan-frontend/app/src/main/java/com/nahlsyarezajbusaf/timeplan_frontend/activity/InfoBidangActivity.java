package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoBidangActivity extends TemplateActivity {

    private TextView namaBidangDesc, namaKetuaBidangDesc, namaPengurusBidangDesc;
    private ImageView logoBidangImage, logoutImage, listProkerImage, editBidangImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bidang);

        apiService = UtilsApi.getApiService();
        logoBidangImage = findViewById(R.id.InfoBidang_logoBidangImage);
        namaBidangDesc = findViewById(R.id.InfoBidang_namaBidangDesc);
        namaKetuaBidangDesc = findViewById(R.id.InfoBidang_namaKetuaBidangDesc);
        namaPengurusBidangDesc = findViewById(R.id.InfoBidang_namaPengurusBidangDesc);
        logoutImage = findViewById(R.id.InfoBidang_logoutImage);
        listProkerImage = findViewById(R.id.InfoBidang_listProkerImage);
        editBidangImage = findViewById(R.id.InfoBidang_editBidangImage);

        handleGetBidang();

        logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));

        editBidangImage.setOnClickListener(view -> {
            moveActivity(EditBidangActivity.class);
        });

        listProkerImage.setOnClickListener(view -> {
        });

        logoutImage.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
            StaticUtils.LOGGED_BIDANG = "NONE";
            viewToast("Bidang berhasil logout!");
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveActivity(PREVIOUS_CLASS);
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

                namaBidangDesc.setText(res.payload.nama_bidang);
                namaKetuaBidangDesc.setText(res.payload.nama_ketua_bidang);
                namaPengurusBidangDesc.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}