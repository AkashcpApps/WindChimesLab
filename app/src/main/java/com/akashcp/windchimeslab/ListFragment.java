package com.akashcp.windchimeslab;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akashcp.windchimeslab.model.Model;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Model> productList;
    DatabaseReference dbProducts;
    ValueEventListener postListener;
    String radiodomain;
    Character chr;
    ImageView imageView;
    int android_count=0,opengl_count=0,sql_count=0,internet_count=0,java_count,power_count=0,ppt_count=0,web_count=0,python_count=0;



    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_list, container, false);
         setHasOptionsMenu(true);
        recyclerView =view.findViewById(R.id.card_recycler_view);
        final ImageView imageView=view.findViewById(R.id.recyclerimage);
        final TextView textView=view.findViewById(R.id.no_records);

        productList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         dbProducts = FirebaseDatabase.getInstance().getReference("Project_list");
         postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                if(dataSnapshot.exists()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);

                    for (final DataSnapshot innerDataSnapshot : dataSnapshot.getChildren()) {
                        Model post = innerDataSnapshot.getValue(Model.class);
                        productList.add(post);
                    }
                    adapter = new ProductAdapter(getContext(), productList);
                    recyclerView.setAdapter(new ProductAdapter(getContext(), productList));
                    adapter.notifyDataSetChanged();
                }
                else
                    {
                        recyclerView.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        adapter = new ProductAdapter(getContext(), productList);
                        recyclerView.setAdapter(new ProductAdapter(getContext(), productList));
                        adapter.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dbProducts.addValueEventListener(postListener);
                            }
                        },2000);

                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.i( "loadPost:onCancelled", databaseError.toException().toString());
                // ...
            }
        };
        dbProducts.addValueEventListener(postListener);
        dbProducts.keepSynced(true);
        adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(new ProductAdapter(getContext(),productList));
        recyclerView.invalidate();
        adapter.notifyDataSetChanged();





        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Name...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
               // Toast.makeText(getContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                if (query.equals(""))
                {
                    recyclerView.invalidate();
                    adapter.notifyDataSetChanged();
                    dbProducts.addValueEventListener(postListener);
                }else{
                query = query.toLowerCase();
                Query query1=FirebaseDatabase.getInstance()
                        .getReference("Project_list")
                        .orderByChild("name")
                        .startAt(query).endAt(query+"\uf8ff");
                    //Toast.makeText(getContext(), ""+query, Toast.LENGTH_SHORT).show();

                query1.addListenerForSingleValueEvent(postListener);
                recyclerView.invalidate();
                adapter.notifyDataSetChanged();
              //  query1.addValueEventListener(postListener);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Here is where we are going to implement the filter logic
                if (newText.equals("")) {
                    recyclerView.invalidate();
                    adapter.notifyDataSetChanged();
                    dbProducts.addValueEventListener(postListener);
                } else {
                    newText = newText.toLowerCase();
                    //Toast.makeText(getContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
                    Query query1 = FirebaseDatabase.getInstance()
                            .getReference("Project_list")
                            .orderByChild("name")
                            .startAt(newText).endAt(newText + "\uf8ff");
                    query1.addListenerForSingleValueEvent(postListener);
                    recyclerView.invalidate();
                    adapter.notifyDataSetChanged();
                }
                    return true;
                }


        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.filter:


                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.filter_domain);
                RadioGroup radioGroup=(RadioGroup)dialog.findViewById(R.id.filtergroup);
                final Button button=(Button)dialog.findViewById(R.id.dailogbutton);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i)
                        {
                            case R.id.radio_android:
                                radiodomain="android";
                                chr='a';
                                Toast.makeText(getContext(), "Android", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_iot:
                                radiodomain="internet of things";
                                chr='i';
                                Toast.makeText(getContext(), "internet of things", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_java:
                                radiodomain="java";
                                chr='j';
                                Toast.makeText(getContext(), "JAVA", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_Opengl:
                                radiodomain="opengl";
                                chr='o';
                                Toast.makeText(getContext(), "OpenGL", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_ppt:
                                radiodomain="power point presentation";
                                chr='p';
                                Toast.makeText(getContext(), "Power Point Presentation", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_python:
                                radiodomain="python";
                                chr='p';
                                Toast.makeText(getContext(), "Python", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_sql:
                                radiodomain="sql";
                                chr='s';
                                Toast.makeText(getContext(), "SQL", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.radio_webapplication:
                                radiodomain="web application";
                                chr='w';
                                Toast.makeText(getContext(), "Web Application", Toast.LENGTH_SHORT).show();
                                break;


                        }
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Query query789=FirebaseDatabase.getInstance()
                                        .getReference("Project_list")
                                        .orderByChild("domain").equalTo(radiodomain);
                                query789.addListenerForSingleValueEvent(postListener);
                                recyclerView.invalidate();
                                adapter.notifyDataSetChanged();
                                Log.i("quty",query789.toString()+"\n"+radiodomain);
                                dialog.dismiss();

                                /*Query query1 = FirebaseDatabase.getInstance()
                                        .getReference("Project_list")
                                        .orderByChild("domain")
                                        .startAt(newText).endAt(newText + "\uf8ff");
                                query1.addListenerForSingleValueEvent(postListener);
                                recyclerView.invalidate();
                                adapter.notifyDataSetChanged();*/

                            }
                        });


                    }
                });
                dialog.show();
                Toast.makeText(getContext(), "Filter", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.invalidate();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView.invalidate();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}


