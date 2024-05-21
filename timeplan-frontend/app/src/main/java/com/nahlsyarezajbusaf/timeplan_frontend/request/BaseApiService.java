package com.nahlsyarezajbusaf.timeplan_frontend.request;


import com.nahlsyarezajbusaf.timeplan_frontend.model.BaseResponse;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bidang;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.JenisProker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Proker;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @POST("bidang/registerBidang")
    Call<BaseResponse<Bidang>> registerBidang(
            @Query("nama_bidang") String nama_bidang,
            @Query("password_bidang") String password_bidang,
            @Query("nama_ketua_bidang") String nama_ketua_bidang,
            @Query("nama_pengurus_bidang") String[] nama_pengurus_bidang
    );

    @GET("bidang/loginBidang")
    Call<BaseResponse<Bidang>> loginBidang(
            @Query("nama_bidang") String nama_bidang,
            @Query("password_bidang") String password_bidang
    );

    @GET("bidang/getBidang")
    Call<BaseResponse<Bidang>> getBidang(
            @Query("nama_bidang") String nama_bidang
    );

    @POST("bidang/editBidang")
    Call<BaseResponse<Bidang>> editBidang(
            @Query("nama_bidang_old") String nama_bidang_old,
            @Query("nama_bidang_new") String nama_bidang_new,
            @Query("password_bidang") String password_bidang,
            @Query("nama_ketua_bidang") String nama_ketua_bidang,
            @Query("nama_pengurus_bidang") String[] nama_pengurus_bidang
    );

    @POST("proker/addProker")
    Call<BaseResponse<Proker>> addProker(
            @Query("nama_bidang") String nama_bidang,
            @Query("nama_proker") String nama_proker,
            @Query("steering_comittee") String steering_comittee,
            @Query("jenis_proker") JenisProker jenis_proker,
            @Query("tanggal_start") int tanggal_start,
            @Query("bulan_start") Bulan bulan_start,
            @Query("tanggal_end") int tanggal_end,
            @Query("bulan_end") Bulan bulan_end
    );

    @GET("proker/getProker")
    Call<BaseResponse<Proker>> getProker(
        @Query("nama_proker") String nama_proker
    );

    @GET("proker/getProkerDisplay")
    Call<BaseResponse<List<ProkerDisplay>>> getProkerDisplay(
    );

    @POST("proker/editProker")
    Call<BaseResponse<Proker>> editProker(
        @Query("nama_bidang_old") String nama_bidang_old,
        @Query("nama_bidang_new") String nama_bidang_new
    );
}
