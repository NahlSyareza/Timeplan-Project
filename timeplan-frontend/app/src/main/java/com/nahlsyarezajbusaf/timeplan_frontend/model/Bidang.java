package com.nahlsyarezajbusaf.timeplan_frontend.model;

import java.util.Arrays;

public class Bidang {
    public String nama_bidang;
    public String password_bidang;
    public String nama_ketua_bidang;
    public String[] nama_pengurus_bidang;

    public Bidang(String nama_bidang, String password_bidang, String nama_ketua_bidang, String[] nama_pengurus_bidang) {
        this.nama_bidang = nama_bidang;
        this.password_bidang = password_bidang;
        this.nama_ketua_bidang = nama_ketua_bidang;
        this.nama_pengurus_bidang = nama_pengurus_bidang;
    }

    @Override
    public String toString() {
        return "Bidang{" +
                "nama_bidang='" + nama_bidang + '\'' +
                ", password_bidang='" + password_bidang + '\'' +
                ", nama_ketua_bidang='" + nama_ketua_bidang + '\'' +
                ", nama_pengurus_bidang=" + Arrays.toString(nama_pengurus_bidang) +
                '}';
    }
}
