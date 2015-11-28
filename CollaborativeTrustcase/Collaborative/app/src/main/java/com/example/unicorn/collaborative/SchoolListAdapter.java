package com.example.unicorn.collaborative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by unicorn on 28.11.15.
 */
public class SchoolListAdapter extends ArrayAdapter {
    private Context context;

    public SchoolListAdapter(Context context, int resource) {

        super(context, resource);
        this.context=context;
    }


    public SchoolListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);


        convertView = inflater.inflate(R.layout.schools_row, null);

        return convertView;
    }
}
