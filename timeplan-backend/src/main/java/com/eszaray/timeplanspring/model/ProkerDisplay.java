package com.eszaray.timeplanspring.model;

public class ProkerDisplay {
    public String namaBidang;
    public String namaProker;
    public Bulan bulanStart;
    public Bulan bulanEnd;
    public int tanggalStart;
    public int tanggalEnd;

    /**
     * Same as proker, but it's data is adjusted with columns from proker_per_bulan table
     *
     * @param namaBidang
     * @param namaProker
     * @param bulanStart
     * @param bulanEnd
     * @param tanggalStart
     * @param tanggalEnd
     */

    public ProkerDisplay(String namaBidang, String namaProker, Bulan bulanStart, Bulan bulanEnd, int tanggalStart, int tanggalEnd) {
        this.namaBidang = namaBidang;
        this.namaProker = namaProker;
        this.bulanStart = bulanStart;
        this.bulanEnd = bulanEnd;
        this.tanggalStart = tanggalStart;
        this.tanggalEnd = tanggalEnd;
    }

    @Override
    public String toString() {
        return "ProkerDisplay{" +
                "namaBidang='" + namaBidang + '\'' +
                ", namaProker='" + namaProker + '\'' +
                ", bulanStart=" + bulanStart +
                ", bulanEnd=" + bulanEnd +
                ", tanggalStart=" + tanggalStart +
                ", tanggalEnd=" + tanggalEnd +
                '}';
    }
}