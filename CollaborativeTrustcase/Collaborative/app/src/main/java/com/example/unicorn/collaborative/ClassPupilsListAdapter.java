package com.example.unicorn.collaborative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class ClassPupilsListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Pupil> pupils;
    public ClassPupilsListAdapter(Context context, int resource, ArrayList list) {
        super(context, resource);
        this.pupils=list;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pupils_row, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.textView2);
        nameText.setText(pupils.get(position).getName());

        return convertView;
    }
}
