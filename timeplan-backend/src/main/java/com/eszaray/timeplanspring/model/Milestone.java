package com.eszaray.timeplanspring.model;

import java.util.Arrays;

public class Milestone {
    public String namaProker;
    public String namaMilestone;
    public Status progresMilestone;
    public String deskripsiMilestone;

    /**
     * Model for milestone so we can transfer data from backend to frontend. Or vice versa
     *
     * @param namaProker
     * @param namaMilestone
     * @param progresMilestone
     * @param deskripsiMilestone
     */
    public Milestone(String namaProker, String namaMilestone, Status progresMilestone, String deskripsiMilestone) {
        this.namaProker = namaProker;
        this.namaMilestone = namaMilestone;
        this.progresMilestone = progresMilestone;
        this.deskripsiMilestone = deskripsiMilestone;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "namaProker='" + namaProker + '\'' +
                ", namaMilestone='" + namaMilestone + '\'' +
                ", progresMilestone=" + progresMilestone +
                ", deskripsiMilestone='" + deskripsiMilestone + '\'' +
                '}';
    }
}
