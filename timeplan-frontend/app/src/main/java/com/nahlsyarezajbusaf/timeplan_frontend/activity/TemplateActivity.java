package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nahlsyarezajbusaf.timeplan_frontend.utils.LambdaExpression;
import com.nahlsyarezajbusaf.timeplan_frontend.utils.StaticUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TemplateActivity extends AppCompatActivity {
    protected Context ctx = this;
    protected static Class<?> PREVIOUS_CLASS;
    protected static Class<?> CURRENT_CLASS;
    protected static List<Class<?>> PASSED_CLASS_LIST = new ArrayList<>();
    protected static int CLASS_INDEXER = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    protected void moveActivity(Class<?> classis) {
        PREVIOUS_CLASS = this.getClass();
        Intent intent = new Intent(ctx, classis);
        CURRENT_CLASS = classis;
        PASSED_CLASS_LIST.add(this.getClass());
        CLASS_INDEXER = PASSED_CLASS_LIST.size();
        Log.i("CECEP", "Current CLASS_INDEXER : " + CLASS_INDEXER);
        startActivity(intent);
    }

    protected void backActivity(Class<?> classis) {
        Intent intent = new Intent(ctx, classis);
        startActivity(intent);
    }

    protected void viewErrorToast(int code) {
        Toast.makeText(ctx, "Error code : " + code, Toast.LENGTH_SHORT).show();
    }

    protected void viewInternalErrorToast() {
        Toast.makeText(ctx, "Internal server error", Toast.LENGTH_SHORT).show();
    }

    protected void viewToast(String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(this instanceof AddProkerActivity) {
            moveActivity(MainActivity.class);
        }

        if(this instanceof EditBidangActivity) {
            moveActivity(InfoBidangActivity.class);
        }

        if(this instanceof InfoBidangActivity) {
            moveActivity(MainActivity.class);
        }

        if(this instanceof LoginBidangActivity) {
            doStuff(() -> {
                moveActivity(MainActivity.class);
            });
        }

        if(this instanceof MainActivity) {
            String[] voiceLines = {"You want to play? Let's play!", "My territory my rules!", "Nowhere to run!"};
            Random random = new Random();
            int i = random.nextInt(voiceLines.length);
            viewToast(voiceLines[i]);
        }

        if(this instanceof RegisterBidangActivity) {
            moveActivity(LoginBidangActivity.class);
        }

        if(this instanceof TimeplanCalendarActivity) {
            moveActivity(MainActivity.class);
        }

        if(this instanceof TimeplanDateDetailsActivity) {
            moveActivity(TimeplanCalendarActivity.class);
        }

        if(this instanceof TimeplanProkerDetailsActivity) {
            moveActivity(TimeplanCalendarActivity.class);
        }

        if(this instanceof TimeplanProkerMilestoneActivity) {
            moveActivity(PREVIOUS_CLASS);
        }

        if(this instanceof ListProkerBidangActivity) {
            moveActivity(InfoBidangActivity.class);
        }

//        if(CLASS_INDEXER > 0) {
//            CLASS_INDEXER--;
//            backActivity(PASSED_CLASS_LIST.get(CLASS_INDEXER));
////            int max = PASSED_CLASS_LIST.size();
////            PASSED_CLASS_LIST.remove(max);
//
//            if(PASSED_CLASS_LIST.get(CLASS_INDEXER).equals(LoginBidangActivity.class)) {
//                Log.i("CECEP", "Logged out! Current CLASS_INDEXER : " + CLASS_INDEXER);
//                StaticUtils.LOGGED_BIDANG = "NONE";
//            } else {
//                Log.i("CECEP", "Current CLASS_INDEXER : " + CLASS_INDEXER);
//            }
//        }
    }

    public void doStuff(LambdaExpression expression) {
        expression.expression();
    }
}
