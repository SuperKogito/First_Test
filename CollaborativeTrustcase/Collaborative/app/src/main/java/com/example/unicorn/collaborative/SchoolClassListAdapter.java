package com.example.unicorn.collaborative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unicorn on 28.11.15.
 */
public class SchoolClassListAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<SchoolClass> schools;

    public SchoolClassListAdapter(Context context, int resource) {

        super(context, resource);
        this.context=context;
    }


    public SchoolClassListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        TextView schoolNameText = (TextView) convertView.findViewById(R.id.textView);
        SchoolClass school = schools.get(position);

        String schoolName=school.getSchoolClassName();
        schoolNameText.setText(schoolName);

        ImageView schoolImage = (ImageView) convertView.findViewById(R.id.imageView2);
        int resId = school.getPhotoId();
        schoolImage.setImageDrawable(context.getResources().getDrawable(resId));




        convertView = inflater.inflate(R.layout.schools_row, null);

        return convertView;
    }
}
