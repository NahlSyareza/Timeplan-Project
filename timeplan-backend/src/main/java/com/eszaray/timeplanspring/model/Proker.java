package com.eszaray.timeplanspring.model;

public class Proker {
    public String namaBidang;
    public String namaProker;
    public String steeringComittee;
    public JenisProker jenisProker;

    /**
     * Model for proker that is adjusted with columns from proker_ime table so we can transmit data from backend to frontend and vice versa I guess?
     *
     * @param namaBidang
     * @param namaProker
     * @param steeringComittee
     * @param jenisProker
     */
    public Proker(String namaBidang, String namaProker, String steeringComittee, JenisProker jenisProker) {
        this.namaBidang = namaBidang;
        this.namaProker = namaProker;
        this.steeringComittee = steeringComittee;
        this.jenisProker = jenisProker;
    }

    @Override
    public String toString() {
        return "Proker{" +
                "namaBidang='" + namaBidang + '\'' +
                ", namaProker='" + namaProker + '\'' +
                ", steeringComittee='" + steeringComittee + '\'' +
                ", jenisProker=" + jenisProker +
                '}';
    }
}
