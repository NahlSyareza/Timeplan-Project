package com.eszaray.timeplanspring;

import com.eszaray.timeplanspring.model.Bulan;

/**
 * Just a test program for easy dynamic display on the custom tour-de-force calendar
 *
 */
public class MonthAdjuster {
    public static Bulan[] BULAN_ARRAY = Bulan.values();

    public static void main(String[] args) {
        Bulan start = Bulan.JANUARI;
        Bulan end = Bulan.MEI;

        // JAN -> FEB -> MAR -> APR -> MEI

        int st = getIndex(BULAN_ARRAY, start);
        int ed = getIndex(BULAN_ARRAY, end);

        for(int i = st ; i <= ed ; i++) {
            System.out.println(i);
        }
    }

    public static <T> int getIndex(T[] array, T find) {
        int index = 0;
        for(T t : array) {
            if(t.equals(find)) {
                break;
            }
            index++;
        }

        return index;
    }
}
