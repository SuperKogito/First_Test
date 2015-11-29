package com.example.unicorn.collaborative;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class Fragment_PupilsClass extends Fragment implements AbsListView.OnItemClickListener{



    private AbsListView mListView;
    private ListAdapter mAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pupils_list_fragment, container, false);



        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
