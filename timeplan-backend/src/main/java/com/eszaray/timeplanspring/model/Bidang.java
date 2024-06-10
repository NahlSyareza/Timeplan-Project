package com.eszaray.timeplanspring.model;

import java.util.Arrays;

public class Bidang {
    public String namaBidang;
    public String passwordBidang;
    public String namaKetuaBidang;
    public String[] namaPengurusBidang;

    /**
     * Model for bidang so we can login/register and transfer data from backend to frontend and also vice versa
     *
     * @param namaBidang
     * @param passwordBidang
     * @param namaKetuaBidang
     * @param namaPengurusBidang
     */
    public Bidang(String namaBidang, String passwordBidang, String namaKetuaBidang, String[] namaPengurusBidang) {
        this.namaBidang = namaBidang;
        this.passwordBidang = passwordBidang;
        this.namaKetuaBidang = namaKetuaBidang;
        this.namaPengurusBidang = namaPengurusBidang;
    }

    @Override
    public String toString() {
        return "Bidang{" +
                "namaBidang='" + namaBidang + '\'' +
                ", passwordBidang='" + passwordBidang + '\'' +
                ", namaKetuaBidang='" + namaKetuaBidang + '\'' +
                ", namaPengurusBidang=" + Arrays.toString(namaPengurusBidang) +
                '}';
    }
}