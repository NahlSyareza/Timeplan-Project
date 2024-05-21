package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.request.BaseApiService;
import com.nahlsyarezajbusaf.timeplan_frontend.request.UtilsApi;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBidangActivity extends TemplateActivity {

    private ImageView back_image;
    private EditText nama_bidang_field, password_bidang_field, nama_ketua_bidang_field, nama_pengurus_bidang_field;
    private Button edit_button;
    private BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bidang);

        apiService = UtilsApi.getApiService();
        back_image = findViewById(R.id.backImage);
        nama_bidang_field = findViewById(R.id.namaBidangFieldEditBidang);
        password_bidang_field = findViewById(R.id.passwordBidangFieldEditBidang);
        nama_ketua_bidang_field = findViewById(R.id.namaKetuaBidangFieldEditBidang);
        nama_pengurus_bidang_field = findViewById(R.id.namaPengurusBidangFieldEditBidang);
        edit_button = findViewById(R.id.editButtonEditBidang);

        handleGetBidang();

        edit_button.setOnClickListener(view -> {
            handleEditBidang();
            handleEditProker();
            StaticUtils.LOGGED_BIDANG = nama_bidang_field.getText().toString();
            moveActivity(MainActivity.class);
        });

        back_image.setOnClickListener(view -> {
            moveActivity(MainActivity.class);
        });
    }

    public void handleEditProker() {
        apiService.editProker(StaticUtils.LOGGED_BIDANG, nama_bidang_field.getText().toString()).enqueue(new Callback<BaseResponse<Proker>>() {
            @Override
            public void onResponse(Call<BaseResponse<Proker>> call, Response<BaseResponse<Proker>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Proker> res = response.body();
                Proker proker = res.payload;

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Proker>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void handleEditBidang() {
        apiService.editBidang(StaticUtils.LOGGED_BIDANG, nama_bidang_field.getText().toString(), "chamber", "Nahl Syareza", new String[]{"Shofiyah", "Rakha Raditya", "Ilham Ramadhan", "Damien Oktavius"}).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();
                Bidang bidang = res.payload;

                viewToast(res.message);
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    public void handleGetBidang() {
        apiService.getBidang(StaticUtils.LOGGED_BIDANG).enqueue(new Callback<BaseResponse<Bidang>>() {
            @Override
            public void onResponse(Call<BaseResponse<Bidang>> call, Response<BaseResponse<Bidang>> response) {
                if(!response.isSuccessful()) {
                    viewErrorToast(response.code());
                    return;
                }

                BaseResponse<Bidang> res = response.body();
                Bidang bidang = res.payload;

                nama_bidang_field.setText(bidang.nama_bidang);
                password_bidang_field.setText(bidang.password_bidang);
                nama_ketua_bidang_field.setText(bidang.nama_ketua_bidang);
                StringBuilder sb = new StringBuilder();
                for(String s : bidang.nama_pengurus_bidang) {
                    sb.append(s).append("\n");
                }
                sb.delete(sb.length() - 2, sb.length());
                nama_pengurus_bidang_field.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }
}
