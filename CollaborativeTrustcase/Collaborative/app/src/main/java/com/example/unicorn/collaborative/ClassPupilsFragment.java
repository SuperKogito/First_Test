package com.example.unicorn.collaborative;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class ClassPupilsFragment extends Fragment implements AbsListView.OnItemClickListener{



    private AbsListView mListView;
    private ListAdapter mAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classes_list_fragment, container, false);


        ArrayList samplePupilsList=new ArrayList();
        samplePupilsList.add(new Pupil("Lisa Müller"));
        samplePupilsList.add(new Pupil("Max Mustermann"));
        mAdapter = new ClassPupilsListAdapter(getContext(), R.layout.pupils_row, samplePupilsList);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.pupils_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
