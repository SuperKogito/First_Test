package com.example.unicorn.collaborative;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class ClassPupilsListAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Pupil> pupils;
    public ClassPupilsListAdapter(Context context, int resource) {
        super(context, resource);
    }



}
