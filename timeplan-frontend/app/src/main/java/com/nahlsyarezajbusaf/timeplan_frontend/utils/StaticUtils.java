package com.nahlsyarezajbusaf.timeplan_frontend.utils;

import com.nahlsyarezajbusaf.timeplan_frontend.R;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Bulan;
import com.nahlsyarezajbusaf.timeplan_frontend.model.ProkerDisplay;
import com.nahlsyarezajbusaf.timeplan_frontend.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Every data that is required across activities is in here
 *
 */
// Everything STATIC goes here
public class StaticUtils {
    public static String SELECTED_PROKER_NAMA = "";
    public static String SELECTED_MILESTONE_DESKRIPSI = "";
    public static String SELECTED_MILESTONE_NAMA = "";
    public static Status SELECTED_MILESTONE_PROGRES = Status.NO_DESC;
    public static String TEMP_NAMA_PROKER = "";
    public static int SELECT_EXECUTION_STATE = 0;
    public static Bulan SELECTED_BULAN_START = Bulan.JANUARI;
    public static Bulan SELECTED_BULAN_END = Bulan.DESEMBER;
    public static int SELECTED_TANGGAL_START = 1;
    public static int SELECTED_TANGGAL_END = 31;
    public static boolean SELECT_EXECUTION_MODE = false;
    public static boolean SHOW_OWNED = false;
    public static boolean SHOW_ACTIVITIES_COLOR = false;
    public static int CURRENT_BULAN_INDEX = 0;
    public static String LOGGED_BIDANG = "NONE";
    public static Bulan CURRENT_MONTH = Bulan.JANUARI;
    public static List<ProkerDisplay> ICBM_PROKER_DISPLAY_LIST = new ArrayList<>();

    public static int handleBidangProfile(String name) {
        switch (name) {
            case "KETULEM":
                return R.mipmap.logo_ketulem_round;

            case "PIPTEK":
                return R.mipmap.logo_piptek_round;

            case "KOMINFO":
                return R.mipmap.logo_kominfo_round;

            case "SIWA":
                return R.mipmap.logo_siwa_round;

            case "KASTRAT":
                return R.mipmap.logo_kastrat_round;

            case "KEMA":
                return R.mipmap.logo_kema_round;

            case "KEWIRUS":
                return R.mipmap.logo_kewirus_round;

            case "TOUR DE FORCE":
                return R.mipmap.logo_tour_de_force_round;

            case "KESTARI":
                return R.mipmap.logo_kestari_round;

            default:
                return R.drawable.baseline_person_24;
        }
    }

    public static Map<Bulan, Integer> handleMonthDates(Map<Bulan, Integer> map) {
        map.clear();
        map.put(Bulan.JANUARI, 31);
        map.put(Bulan.FEBRUARI, 29);
        map.put(Bulan.MARET, 31);
        map.put(Bulan.APRIL, 30);
        map.put(Bulan.MEI, 31);
        map.put(Bulan.JUNI, 30);
        map.put(Bulan.JULI, 31);
        map.put(Bulan.AGUSTUS, 31);
        map.put(Bulan.SEPTEMBER, 30);
        map.put(Bulan.OKTOBER, 31);
        map.put(Bulan.NOVEMBER, 30);
        map.put(Bulan.DESEMBER, 31);

        return map;
    }
}
