package com.eszaray.timeplanspring.model;

public class Proker {
    public String nama_bidang;
    public String nama_proker;
    public String steering_comittee;
    public JenisProker jenis_proker;

    public Proker(String nama_bidang, String nama_proker, String steering_comittee, JenisProker jenis_proker) {
        this.nama_bidang = nama_bidang;
        this.nama_proker = nama_proker;
        this.steering_comittee = steering_comittee;
        this.jenis_proker = jenis_proker;
    }

    @Override
    public String toString() {
        return "Proker{" +
                "nama_bidang='" + nama_bidang + '\'' +
                ", nama_proker='" + nama_proker + '\'' +
                ", steering_comittee='" + steering_comittee + '\'' +
                ", jenis_proker=" + jenis_proker +
                '}';
    }
}
