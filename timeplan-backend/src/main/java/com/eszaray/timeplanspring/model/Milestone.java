package com.eszaray.timeplanspring.model;

import java.util.Arrays;

public class Milestone {
    public String nama_proker;
    public String nama_milestone;
    public String[] target_milestone;
    public String[] progres_milestone;

    public Milestone(String nama_proker, String nama_milestone, String[] target_milestone, String[] progres_milestone) {
        this.nama_proker = nama_proker;
        this.nama_milestone = nama_milestone;
        this.target_milestone = target_milestone;
        this.progres_milestone = progres_milestone;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "nama_proker='" + nama_proker + '\'' +
                ", nama_milestone='" + nama_milestone + '\'' +
                ", target_milestone=" + Arrays.toString(target_milestone) +
                ", progres_milestone=" + Arrays.toString(progres_milestone) +
                '}';
    }
}
