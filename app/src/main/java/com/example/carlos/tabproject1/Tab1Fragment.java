package com.example.carlos.tabproject1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ArrayList<TODO> todoArrayList;
    CheckBox checkBox;
    tabAdapter adapter;
    TODO mTodo;
    RecyclerView recyclerView;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = v.findViewById(R.id.recycleView);

        //databaseReference = FirebaseDatabase.getInstance().getReference();
        checkBox = v.findViewById(R.id.completedCheckBox);
        todoArrayList = new ArrayList<TODO>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        adapter = new tabAdapter(getContext(), todoArrayList);
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();

        initializeData();
        return v;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menuAddTask:

                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 1);

                adapter.notifyDataSetChanged();
                break;
            case R.id.menuDelete:
                if (todoArrayList.size() >= 1) {
                    todoArrayList.remove(todoArrayList.size() - 1);
                        checkBox.isSelected();
                    checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
                    checkBox.getTag();
                    todoArrayList.remove(checkBox.isChecked());
                    adapter.notifyDataSetChanged();
                } else {
                    break;
                }
                break;

            default:

        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeData() {

        String[] nameArray = getResources().getStringArray(R.array.Name);
        String[] noteArray = getResources().getStringArray(R.array.Notes);

        todoArrayList.clear();


        for (int i = 0; i < nameArray.length; i++) {
            todoArrayList.add(new TODO(nameArray[i], noteArray[i]));

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            String s = data.getStringExtra("name");
            String v = data.getStringExtra("note");

            mTodo = new TODO(s, v);
            todoArrayList.add(mTodo);
            adapter.notifyDataSetChanged();

        }

    }


}
