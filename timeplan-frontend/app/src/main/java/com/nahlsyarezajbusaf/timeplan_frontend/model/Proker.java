package com.nahlsyarezajbusaf.timeplan_frontend.model;

public class Proker {
    public String namaBidang;
    public String namaProker;
    public String steeringComittee;
    public JenisProker jenisProker;

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
