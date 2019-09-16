package com.example.carlos.tabproject1.views;

import android.view.View;

import com.example.carlos.tabproject1.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass for out first tab.
 */

public class RecyclerViewHelper{
    RecyclerView recyclerView;

    RecyclerViewHelper(View view, LinearLayoutManager linearLayoutManager, TabAdapter adapter){
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView  getRecyclerView() {
        return recyclerView;
    }
}
