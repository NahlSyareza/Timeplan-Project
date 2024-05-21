package com.eszaray.timeplanspring.model;

public class ProkerDisplay {
    public String nama_bidang;
    public String nama_proker;
    public Bulan bulan_start;
    public Bulan bulan_end;
    public int tanggal_start;
    public int tanggal_end;

    public ProkerDisplay(String nama_bidang, String nama_proker, Bulan bulan_start, Bulan bulan_end, int tanggal_start, int tanggal_end) {
        this.nama_bidang = nama_bidang;
        this.nama_proker = nama_proker;
        this.bulan_start = bulan_start;
        this.bulan_end = bulan_end;
        this.tanggal_start = tanggal_start;
        this.tanggal_end = tanggal_end;
    }

    @Override
    public String toString() {
        return "ProkerDisplay{" +
                "nama_bidang='" + nama_bidang + '\'' +
                ", nama_proker='" + nama_proker + '\'' +
                ", bulan_start=" + bulan_start +
                ", bulan_end=" + bulan_end +
                ", tanggal_start=" + tanggal_start +
                ", tanggal_end=" + tanggal_end +
                '}';
    }
}