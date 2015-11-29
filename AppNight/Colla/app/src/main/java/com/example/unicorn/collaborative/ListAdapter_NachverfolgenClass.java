package com.example.unicorn.collaborative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 29.11.2015.
 */
public class ListAdapter_NachverfolgenClass extends ArrayAdapter {

    private Context context;
    private ArrayList<ListToNachverfolgung> parents;
    public ListAdapter_NachverfolgenClass(Context context, int resource, ArrayList list) {
        super(context, resource);
        this.parents=list;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listto_verfolgungrow, null);

        TextView nameText = (TextView) convertView.findViewById(R.id.textView3);
        nameText.setText(parents.get(position).getName());



        return convertView;
    }
}
