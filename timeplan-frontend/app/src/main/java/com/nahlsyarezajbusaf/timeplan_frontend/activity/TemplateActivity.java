package com.nahlsyarezajbusaf.timeplan_frontend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TemplateActivity extends AppCompatActivity {
    protected Context ctx = this;
    protected static Class<?> PREVIOUS_CLASS;
    protected static Class<?> CURRENT_CLASS;
    protected static List<Class<?>> PASSED_CLASS_LIST = new ArrayList<>();

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
    }
}
