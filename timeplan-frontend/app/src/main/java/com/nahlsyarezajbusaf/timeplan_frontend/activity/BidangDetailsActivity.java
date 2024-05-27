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

public class BidangDetailsActivity extends TemplateActivity {

    private TextView namaBidangDesc, namaKetuaBidangDesc, namaPengurusBidangDesc;
    private ImageView logoBidangImage, logoutImage, listProkerImage, editBidangImage;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidang_details);

        apiService = UtilsApi.getApiService();
        logoBidangImage = findViewById(R.id.BidangDetails_logoBidangImage);
        namaBidangDesc = findViewById(R.id.BidangDetails_namaBidangDesc);
        namaKetuaBidangDesc = findViewById(R.id.BidangDetails_namaKetuaBidangDesc);
        namaPengurusBidangDesc = findViewById(R.id.BidangDetails_namaPengurusBidangDesc);
        logoutImage = findViewById(R.id.BidangDetails_logoutImage);
        listProkerImage = findViewById(R.id.BidangDetails_listProkerImage);
        editBidangImage = findViewById(R.id.BidangDetails_editBidangImage);

        handleGetBidang();

        logoBidangImage.setImageResource(StaticUtils.handleBidangProfile(StaticUtils.LOGGED_BIDANG));

        editBidangImage.setOnClickListener(view -> {
            moveActivity(EditBidangActivity.class);
        });

        listProkerImage.setOnClickListener(view -> {
            moveActivity(ListProkerBidangActivity.class);
        });

        logoutImage.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
            StaticUtils.LOGGED_BIDANG = "NONE";
            viewToast("Bidang berhasil logout!");
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

                String[] arr = res.payload.namaPengurusBidang;
                StringBuilder sb = new StringBuilder();

                for (String s : arr) {
                    sb.append(s).append(", ");
                }

                sb.delete(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 1);

                namaBidangDesc.setText(res.payload.namaBidang);
                namaKetuaBidangDesc.setText(res.payload.namaKetuaBidang);
                namaPengurusBidangDesc.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}