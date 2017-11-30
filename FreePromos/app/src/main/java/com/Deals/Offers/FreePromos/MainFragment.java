package com.Deals.Offers.FreePromos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Priya on 25-05-2017.
 */

public class MainFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<HashMap<String,String>> notification_list;
    HashMap<String,String> notification_new;
    notifyadpt notification_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =lf.inflate(R.layout.main_fragment,container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notifications");

        notification_list=new ArrayList<>();
        notification_adapter=new notifyadpt(this.getActivity(),notification_list);
        ListView notification_lisview=(ListView) view.findViewById(R.id.notification_list);
        notification_lisview.setAdapter(notification_adapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notification_new=(HashMap<String, String>) dataSnapshot.getValue();
                notification_list.add(notification_new);
                notification_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                notification_new=(HashMap<String, String>) dataSnapshot.getValue();
                notification_list.remove(notification_new);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return view;



    }
}
