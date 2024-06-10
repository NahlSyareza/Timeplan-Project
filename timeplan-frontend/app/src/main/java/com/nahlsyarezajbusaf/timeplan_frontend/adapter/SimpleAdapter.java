package com.nahlsyarezajbusaf.timeplan_frontend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nahlsyarezajbusaf.timeplan_frontend.R;

import java.util.List;

/**
 * Used for general purpose, such as dropdown
 *
 * @param <T>
 */
public class SimpleAdapter<T extends String> extends ArrayAdapter<T> {
    private TextView item_text;

    public SimpleAdapter(@NonNull Context context, List<T> list) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, list);
    }

    public SimpleAdapter(@NonNull Context context, T[] array) {
        super(context, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, array);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_simple_layout, parent, false);
        }

        T item = getItem(position);

        item_text = convertView.findViewById(R.id.itemTextSimpleAdapter);

        String new_item = item.replace("_", " ");
        item_text.setText(new_item);

        return convertView;
    }
}
