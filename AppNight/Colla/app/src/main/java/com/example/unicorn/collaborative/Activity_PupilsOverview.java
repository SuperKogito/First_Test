package com.example.unicorn.collaborative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class Activity_PupilsOverview extends AppCompatActivity {

    private ListAdapter_PupilsClass mAdapter;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pupils_list_activity);

        ArrayList samplePupilsList = new ArrayList();
        samplePupilsList.add(new Pupil("Lisa Müller"));
        samplePupilsList.add(new Pupil("Max Mustermann"));
        mAdapter = new ListAdapter_PupilsClass(this, R.layout.pupils_row, samplePupilsList);

        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.pupils_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
    }
}





