package com.example.unicorn.collaborative;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class DisplaySchoolClassesActivity extends Activity  {


    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classes_list_activity);


        mListView = (AbsListView) findViewById(R.id.schools_list);

        final ArrayList<SchoolClass> sampleSchoolList= new ArrayList<>();
        sampleSchoolList.add(new SchoolClass("Klasse 7a", "nervig", R.drawable.ic_menu_camera, "sampleid1"));
        sampleSchoolList.add(new SchoolClass("Klasse 9d", "doof", R.drawable.ic_menu_gallery, "sampleid2"));
        final ListAdapter listAdapter = new SchoolClassListAdapter(this, R.layout.schools_row, sampleSchoolList);

        final Activity activity=this;

mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SchoolClass schoolClass = (SchoolClass) listAdapter.getItem(position);

                String classId=schoolClass.getSchoolClassId();

                Bundle bundle= new Bundle();
                bundle.putString("groupId", classId);


                Intent intent = new Intent(activity, PupilsOverviewActivity.class);
                startActivity(intent);

                //TODO: display pupils activity


            }
        });

    }
}
