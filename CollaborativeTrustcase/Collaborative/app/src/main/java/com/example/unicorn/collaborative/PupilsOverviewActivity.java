package com.example.unicorn.collaborative;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class PupilsOverviewActivity extends Activity {

    private ClassPupilsListAdapter mAdapter;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pupils_list_activity);

        ArrayList samplePupilsList=new ArrayList();
        samplePupilsList.add(new Pupil("Lisa Müller"));
        samplePupilsList.add(new Pupil("Max Mustermann"));
        mAdapter = new ClassPupilsListAdapter(this, R.layout.pupils_row, samplePupilsList);

        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.pupils_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks



    }



}
