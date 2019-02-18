package com.example.carlos.tabproject1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 4/30/2018.
 */

public class tabAdapter extends RecyclerView.Adapter<tabAdapter.ViewHolder> implements TextWatcher {
    int p;
    private List<TODO> todoList;
    private Context context;
    private LayoutInflater layoutInflater;
    private TODO todo;
    private EditText nameEditText, notesEditText;


    tabAdapter(List todo) {
        context.getApplicationContext();
        todoList = todo;
    }

    tabAdapter(Context context, List<TODO> todoList) {
        // this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.todoList = todoList;
    }

    @Override
    public tabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // return new ViewHolder(context, LayoutInflater.from(context).inflate(R.layout.listtask_view,parent,false));
        View v = layoutInflater.inflate(R.layout.listtask_view, parent, false);

        return new tabAdapter.ViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(final tabAdapter.ViewHolder holder, final int position) {
        final TODO todo = todoList.get(position);
        p = position;
        holder.bindTo(todo);






        holder.checkBox.setChecked(todoList.get(position).isEditable());
        holder.checkBox.setTag(todoList.get(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.checkBox.isChecked()) {
                    CheckBox cb = (CheckBox) buttonView;
                    TODO todo2 = (TODO) cb.getTag();
                    //todo2.getEditable(cb.isChecked());
                    todo2.getEditable(isChecked);
                    //todoArrayList.get(position).getEditable(cb.isChecked());
                    todoList.get(position).getEditable(isChecked);
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
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    void removeItem(TODO todo) {
        todoList.remove(todo);
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
        private TODO mTODO;
        private tabAdapter adapter;

        public ViewHolder(View context, tabAdapter inflate) {
            super(context);

            nameTV = context.findViewById(R.id.nameTextView);
            noteTV = context.findViewById(R.id.notesTextView);
            checkBox = context.findViewById(R.id.completedCheckBox);
            adapter = inflate;



        }

        public void bindTo(TODO todo) {
            nameTV.setText(todo.getName());
            noteTV.setText(todo.getNote());
            mTODO = todo;
        }
    }
}
