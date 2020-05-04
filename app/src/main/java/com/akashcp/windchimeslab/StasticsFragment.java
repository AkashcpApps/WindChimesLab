package com.akashcp.windchimeslab;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StasticsFragment extends Fragment {


    public StasticsFragment() {
        // Required empty public constructor
    }

    View view;

    String mainstr;
    List<String> list;
    int[] imageId = {
            R.drawable.android,
            R.drawable.wifi,
            R.drawable.java,
            R.drawable.opengl,
            R.drawable.ppt,
            R.drawable.python,
            R.drawable.database,
            R.drawable.settings


    };
    String[] web = {
            "Android",
            "Internet of Things",
            "Java",
            "OpenGl",
            "PPT",
            "Python",
            "SQL",
            "Web Applications"
    };
    String[] scripts;
    ArrayList<String> al = new ArrayList<String>();
    int android_count1=0,opengl_count1=0,sql_count1=0,internet_count1=0,java_count1=0,power_count1=0,ppt_count1=0,web_count1=0,python_count1=0;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stastics, container, false);
    tv1=(TextView)view.findViewById(R.id.android_count);
     tv2=(TextView)view.findViewById(R.id.iot_count);
    tv3=(TextView)view.findViewById(R.id.java_count);
     tv4=(TextView)view.findViewById(R.id.opengl_count);
   tv5=(TextView)view.findViewById(R.id.ppt_count);
   tv6=(TextView)view.findViewById(R.id.python_count);
 tv7=(TextView)view.findViewById(R.id.sql_count);
  tv8=(TextView)view.findViewById(R.id.web_count);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Project_list");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.e("sdg", childDataSnapshot.getKey()); //displays the key for the node
                    if (childDataSnapshot.child("domain").getValue().toString().equals("android")) {
                        android_count1+= 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("sql")) {
                        sql_count1 += 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("java")) {
                        java_count1+= 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("python")) {
                        python_count1 += 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("internet of things")) {
                        internet_count1 += 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("web application")) {
                        web_count1 += 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("power point presentation")) {
                        ppt_count1+= 1;

                    }
                    if (childDataSnapshot.child("domain").getValue().toString().equals("opengl")) {
                        opengl_count1 += 1;

                    }

                }


                tv1.setText(String.valueOf(android_count1));
                tv2.setText(String.valueOf(internet_count1));
                tv3.setText(String.valueOf(java_count1));
                tv4.setText(String.valueOf(opengl_count1));
                tv5.setText(String.valueOf(ppt_count1));
                tv6.setText(String.valueOf(python_count1));
                tv7.setText(String.valueOf(sql_count1));
                tv8.setText(String.valueOf(web_count1));
                android_count1=0;
            opengl_count1=0;
            sql_count1=0;
            internet_count1=0;
            java_count1=0;
            power_count1=0;
            ppt_count1=0;
            web_count1=0;
            python_count1=0;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

    }
}
