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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Creates a new proker with the required instances
 *
 */
public class AddProkerActivity extends TemplateActivity {
    private EditText namaProkerField;
    private Spinner namaPengurusSpinner, jenisProkerSpinner;
    private Button addButton;
    private ImageView pilihJadwalImage;
    private BaseApiService apiService;
    private Context ctx = this;

    /**
     * Acts as a main function for this activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_proker);

        apiService = UtilsApi.getApiService();
        namaProkerField = findViewById(R.id.AddProker_namaProkerField);
        pilihJadwalImage = findViewById(R.id.AddProker_pilihJadwalImage);
        addButton = findViewById(R.id.AddProker_addButton);
        namaPengurusSpinner = findViewById(R.id.AddProker_namaPengurusSpinner);
        jenisProkerSpinner = findViewById(R.id.AddProker_jenisProkerSpinner);

        handleGetBidang();

        JenisProker jenis_proker_array[] = JenisProker.values();
        List<String> jenis_proker_list = new ArrayList<>();
        for(JenisProker jp : jenis_proker_array) {
            String new_name = jp.name().replace("_", " ");
            jenis_proker_list.add(new_name);
        }

        jenisProkerSpinner.setAdapter(new SimpleAdapter<>(this, jenis_proker_list));

        pilihJadwalImage.setOnClickListener(view -> {
            String nama_proker = namaProkerField.getText().toString();
            if (nama_proker.isEmpty()) {
                viewToast("Nama proker tidak boleh kosong");
            } else {
                StaticUtils.TEMP_NAMA_PROKER = namaProkerField.getText().toString();
                StaticUtils.SELECT_EXECUTION_MODE = true;
                moveActivity(TimeplanCalendarActivity.class);
            }
        });

        if (!StaticUtils.TEMP_NAMA_PROKER.isEmpty()) {
            namaProkerField.setText(StaticUtils.TEMP_NAMA_PROKER);
        }

        addButton.setOnClickListener(view -> {
            StaticUtils.TEMP_NAMA_PROKER = "";
            handleAddProker();
            moveActivity(MainActivity.class);
        });
    }

    /**
     * Gets a bidang, and then get it's pengurus and ketua so we can display it in the spinner
     *
     */
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
                list.add(bidang.namaKetuaBidang);
                for(String s : bidang.namaPengurusBidang) {
                    list.add(s);
                }
                namaPengurusSpinner.setAdapter(new SimpleAdapter(ctx, list));
            }

            @Override
            public void onFailure(Call<BaseResponse<Bidang>> call, Throwable t) {
                viewInternalErrorToast();
            }
        });
    }

    /**
     * Shoots to the backend to add a new proker after we require all the needed instances
     *
     */
    public void handleAddProker() {
        String nama_bidang = StaticUtils.LOGGED_BIDANG;
        String nama_proker = namaProkerField.getText().toString();
        String steering_comittee = (String) namaPengurusSpinner.getSelectedItem();
        String jenis_proker = (String) jenisProkerSpinner.getSelectedItem();
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