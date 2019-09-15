package com.example.carlos.tabproject1;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Carlos on 4/30/2018.
 */

public class TabAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<TodoTask> todoTask;
    private LayoutInflater layoutInflater;
    private String Tabname;


    TabAdapter(Context context, List<TodoTask> todoTask, String tabname) {
        layoutInflater = LayoutInflater.from(context);
        this.Tabname = tabname;
        this.todoTask = todoTask;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.listtask_view, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TodoTask todoTask = this.todoTask.get(position);

        holder.bindTo(todoTask);
        holder.checkBox.setTag(this.todoTask.get(position));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    CheckBox cb = (CheckBox) buttonView;
                    TodoTask todoTask2 = (TodoTask) cb.getTag();
                    todoTask2.readyForDeletion(isChecked);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditActivity.class);
                intent.putExtra("Tabname", Tabname);
                intent.putExtra("TaskName", todoTask.getName());
                intent.putExtra("TaskNote", todoTask.getNote());
                v.getContext().startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO: consider using this input to give user the option to place todoTask item in the top
                Toast.makeText(holder.itemView.getContext(), todoTask.getName(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoTask.size();
    }

}
