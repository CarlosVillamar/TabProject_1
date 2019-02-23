package com.example.carlos.tabproject1;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Carlos on 4/30/2018.
 */

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {
    private List<Task> taskList;
    private Context context;
    private LayoutInflater layoutInflater;
    private String Tabname;


    TabAdapter(List todo) {
        context.getApplicationContext();
        taskList = todo;
    }

    TabAdapter(Context context, List<Task> taskList,String tabname) {
        // this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.Tabname = tabname;
        this.taskList = taskList;
    }

    TabAdapter(Context context, List<Task> taskList) {
        // this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.taskList = taskList;
    }

    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(context, LayoutInflater.from(context).inflate(R.layout.listtask_view,parent,false));
        View v = layoutInflater.inflate(R.layout.listtask_view, parent, false);


        return new TabAdapter.ViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(final TabAdapter.ViewHolder holder, final int position) {
        final Task task = taskList.get(position);

        holder.bindTo(task);
        holder.checkBox.setTag(taskList.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    CheckBox cb = (CheckBox) buttonView;
                    Task task2 = (Task) cb.getTag();
                    task2.getEditable(isChecked);
//                    Toast.makeText(holder.checkBox.getContext(), task2.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditActivity.class);
                intent.putExtra("Tabname",Tabname);
                intent.putExtra("TaskName", task.getName());
                intent.putExtra("TaskNote",task.getNote());
                v.getContext().startActivity(intent);
//                Log.d(TAG, "onCreateViewHolder: " + Tabname);
//                Log.d(TAG, "onCreateViewHolder: " + p);
//                Log.d(TAG, "onClick: "+ task.getName());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: consider using this input to give user the option to place task item in the top
                Toast.makeText(holder.itemView.getContext(),task.getName(),Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        //implement click listener
        private TextView nameTV, noteTV;
        private Task mTask;
        private TabAdapter adapter;

        public ViewHolder(View view, TabAdapter inflate) {
            super(view);

            nameTV = view.findViewById(R.id.nameTextView);
            noteTV = view.findViewById(R.id.notesTextView);
            checkBox = view.findViewById(R.id.completedCheckBox);
            adapter = inflate;



        }

        public void bindTo(Task task) {
            nameTV.setText(task.getName());
            noteTV.setText(task.getNote());
            mTask = task;
        }
    }
}
