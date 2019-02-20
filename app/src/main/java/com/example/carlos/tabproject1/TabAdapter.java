package com.example.carlos.tabproject1;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Carlos on 4/30/2018.
 */

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> implements TextWatcher {
    int p;
    private List<Task> taskList;
    private Context context;
    private LayoutInflater layoutInflater;
    private Task task;
    private EditText nameEditText, notesEditText;


    TabAdapter(List todo) {
        context.getApplicationContext();
        taskList = todo;
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
        p = position;
        holder.bindTo(task);






        holder.checkBox.setChecked(taskList.get(position).isEditable());
        holder.checkBox.setTag(taskList.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    CheckBox cb = (CheckBox) buttonView;
                    Task task2 = (Task) cb.getTag();
                    //task2.getEditable(cb.isChecked());
                    task2.getEditable(isChecked);
                    //taskArrayList.get(position).getEditable(cb.isChecked());
//                    taskList.get(position).getEditable(isChecked);
                    holder.checkBox.setChecked(isChecked);
                    Toast.makeText(holder.checkBox.getContext(), "checkmate", Toast.LENGTH_LONG).show();

                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"Long Click Works",Toast.LENGTH_LONG).show();
                removeItem(task);

                return true;
                //Task: consider using this input as a delete option or giving user the option to place task item in the top
            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    void removeItem(Task task) {
        taskList.remove(task);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        //implement click listener
        private TextView nameTV, noteTV;
        private Context context;
        private Task mTask;
        private TabAdapter adapter;

        public ViewHolder(View context, TabAdapter inflate) {
            super(context);

            nameTV = context.findViewById(R.id.nameTextView);
            noteTV = context.findViewById(R.id.notesTextView);
            checkBox = context.findViewById(R.id.completedCheckBox);
            adapter = inflate;



        }

        public void bindTo(Task task) {
            nameTV.setText(task.getName());
            noteTV.setText(task.getNote());
            mTask = task;
        }
    }
}
