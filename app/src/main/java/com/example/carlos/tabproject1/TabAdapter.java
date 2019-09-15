package com.example.carlos.tabproject1;


import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Carlos on 4/30/2018.
 */

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {
    List<TaskList> taskList;
    private LayoutInflater layoutInflater;
    private String Tabname;


    TabAdapter(Context context, List<TaskList> taskList, String tabname) {
        // this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.Tabname = tabname;
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
        final TaskList taskList = this.taskList.get(position);

        holder.bindTo(taskList);
        holder.checkBox.setTag(this.taskList.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    CheckBox cb = (CheckBox) buttonView;
                    TaskList taskList2 = (TaskList) cb.getTag();
                    taskList2.readyForDeletion(isChecked);
//                    Toast.makeText(holder.checkBox.getContext(), taskList2.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditActivity.class);
                intent.putExtra("Tabname",Tabname);
                intent.putExtra("TaskName", taskList.getName());
                intent.putExtra("TaskNote", taskList.getNote());
                v.getContext().startActivity(intent);
//                Log.d(TAG, "onCreateViewHolder: " + Tabname);
//                Log.d(TAG, "onCreateViewHolder: " + p);
//                Log.d(TAG, "onClick: "+ taskList.getName());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: consider using this input to give user the option to place taskList item in the top
                Toast.makeText(holder.itemView.getContext(), taskList.getName(),Toast.LENGTH_LONG).show();
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
        private TaskList mTaskList;
        private TabAdapter adapter;

        public ViewHolder(View view, TabAdapter inflate) {
            super(view);

            nameTV = view.findViewById(R.id.nameTextView);
            noteTV = view.findViewById(R.id.notesTextView);
            checkBox = view.findViewById(R.id.completedCheckBox);
            adapter = inflate;



        }

        public void bindTo(TaskList taskList) {
            nameTV.setText(taskList.getName());
            noteTV.setText(taskList.getNote());
            mTaskList = taskList;
        }
    }
}
