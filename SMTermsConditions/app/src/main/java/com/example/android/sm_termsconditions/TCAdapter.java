package com.example.android.sm_termsconditions;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suyash on 12-01-2018.
 */

public class TCAdapter extends ArrayAdapter<String> {

    public TCAdapter(Activity context, ArrayList<String> points)
    {
        super(context, 0, points);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.tc_layout, parent, false);
        }

        String currentString = getItem(position);
        currentString = Integer.toString(position + 1) + ". " + currentString;

        TextView textView = (TextView) listItemView.findViewById(R.id.tc);
        textView.setText(currentString);

        return listItemView;
    }
}
