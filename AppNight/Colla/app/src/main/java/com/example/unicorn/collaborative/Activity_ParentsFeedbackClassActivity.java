package com.example.unicorn.collaborative;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by unicorn on 28.11.15.
 */
public class Activity_ParentsFeedbackClassActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutparentsfeedback_activity);

        ListView listview = (ListView) findViewById(R.id.parentfeedback);

        ArrayList<ListToNachverfolgung> list = new ArrayList<>();
        list.add(new ListToNachverfolgung("Klasse 7a", 9));
        list.add(new ListToNachverfolgung("Klasse 9a", 3));

        ListAdapter adapter = new ListAdapter_NachverfolgenClass(this, R.layout.listto_verfolgungrow, list);



        listview.setAdapter(adapter);
    }

}
