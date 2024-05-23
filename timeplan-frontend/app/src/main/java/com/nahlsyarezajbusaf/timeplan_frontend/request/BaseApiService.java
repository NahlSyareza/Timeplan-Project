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
            @Query("namaBidang") String namaBidang,
            @Query("namaProker") String namaProker,
            @Query("steeringComittee") String steeringComittee,
            @Query("jenisProker") JenisProker jenisProker,
            @Query("tanggalStart") int tanggalStart,
            @Query("bulanStart") Bulan bulanStart,
            @Query("tanggalEnd") int tanggalEnd,
            @Query("bulanEnd") Bulan bulanEnd
    );

    @GET("proker/getNamaProker")
    Call<BaseResponse<Proker>> getNamaProker(
        @Query("namaProker") String namaProker
    );

    @GET("proker/getBidangProker")
    Call<BaseResponse<List<Proker>>> getBidangProker(
            @Query("namaBidang") String namaBidang
    );

    @GET("proker/getProkerDisplay")
    Call<BaseResponse<List<ProkerDisplay>>> getProkerDisplay(
    );

    @POST("proker/editProker")
    Call<BaseResponse<Proker>> editProker(
        @Query("namaBidangOld") String namaBidangOld,
        @Query("namaBidangNew") String namaBidangNew
    );
}
