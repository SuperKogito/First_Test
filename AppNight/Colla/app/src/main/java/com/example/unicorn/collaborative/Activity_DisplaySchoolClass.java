package com.example.unicorn.collaborative;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.trustcase.client.TrustCase;
import com.trustcase.client.TrustCaseClient;
import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.responses.RoomListResponseItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by unicorn on 28.11.15.
 */
public class Activity_DisplaySchoolClass extends AppCompatActivity {
    //private ArrayAdapter<String> mAdapter;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.classes_list_activity);

        //private ArrayAdapter<String> mAdapter;
        mListView = (AbsListView) findViewById(R.id.schools_list);

        final ArrayList<SchoolClass> sampleSchoolList = new ArrayList<>();
        sampleSchoolList.add(new SchoolClass("Klasse 7a", "nervig", R.drawable.ic_menu_camera, "sampleid1"));
        sampleSchoolList.add(new SchoolClass("Klasse 9d", "doof", R.drawable.ic_menu_gallery, "sampleid2"));
        final ListAdapter listAdapter = new ListAdapter_SchoolClass(this, R.layout.row_schools, sampleSchoolList);

        final Activity activity = this;

        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SchoolClass schoolClass = (SchoolClass) listAdapter.getItem(position);

                String classId = schoolClass.getSchoolClassId();

                Bundle bundle = new Bundle();
                bundle.putString("groupId", classId);


                Intent intent = new Intent(activity, Activity_PupilsOverview.class);
                startActivity(intent);

                //TODO: display pupils activity
                /***
                 // TODO: Change Adapter to display your content
                mAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, items);

                mListView = (AbsListView) findViewById(R.id.overview_list);
                ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Intent intent = new Intent(activity, Activity_DisplaySchoolClass.class);
                            startActivity(intent);

                        } else if (position == 1) {
                            Intent intent = new Intent(activity, Activity_ParentsFeedbackClassActivity.class);
                            startActivity(intent);
                        }
                    }
                });*
                 */
            }
        });

        new AsyncTask<Object,Void, List>() {
            @Override
            protected List doInBackground(Object[] params) {
                final String endpoint = "https://api.trustcase.com/api/v0.6";
                final String jid = "6MB5S98G";
                final String privateKey = "6ebb05d98e7c34ddcdc77341da2b5581478438ef2ca3cf9badcd6f20f8ae6872";
                final String publicKey = "2be5f7bde50463b3e8b42647e0e7582c0c8f5a399aa7f1ac1604d2b298402e46";
                final String password = "88e66feeb4eb49fe95bfaced46106f7e";
                TrustCaseClient client = TrustCase.newBuilder().apiEndpoint(endpoint).jid(jid).privateKey(privateKey)
                        .publicKey(publicKey).password(password).build();

                List rooms=null;
                try {
                 rooms  = client.listRooms();
                } catch (TrustCaseClientException e) {
                    e.printStackTrace();
                }
                return rooms;
            }

            @Override
            protected void onPostExecute(List o) {

                    List<RoomListResponseItem> classes = o;
                    for (RoomListResponseItem item: classes)
                    {
                        Log.d("TAG", "id:" +item.getId());
                        Log.d("TAG", "sonstiges: "+item.getLastMessageBy());
                    }

             //   super.onPostExecute(o);
            }
        }.execute();
    }
}