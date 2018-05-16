package com.example.carlos.tabproject1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Carlos on 4/30/2018.
 */

public class tabAdapter extends RecyclerView.Adapter<tabAdapter.ViewHolder> {
    int p;
   private ArrayList<TODO> todoArrayList;
   private Context context;
   private LayoutInflater layoutInflater;
   private TODO todo;

   tabAdapter(ArrayList todo){
        todoArrayList = todo;
   }

   tabAdapter(Context context,ArrayList<TODO> todoArrayList) {
      // this.context = context;
       layoutInflater = LayoutInflater.from(context);
       this.todoArrayList = todoArrayList;
   }

    @Override
    public tabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // return new ViewHolder(context, LayoutInflater.from(context).inflate(R.layout.listtask_view,parent,false));
        View v = layoutInflater.inflate(R.layout.listtask_view,parent,false);

        return new tabAdapter.ViewHolder(v,this);
    }


    @Override
    public void onBindViewHolder(final tabAdapter.ViewHolder holder, final int position) {
        final TODO todo = todoArrayList.get(position);
        p = position;
        holder.bindTo(todo);
        // TODO: 5/16/2018 checkbox listener
        holder.checkBox.setChecked(todoArrayList.get(position).isEditable());
        holder.checkBox.setTag(todoArrayList.get(position));
       holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(holder.checkBox.isChecked()){
                    CheckBox cb   = (CheckBox) buttonView;
                    TODO todo2 = (TODO) cb.getTag();
                    //todo2.getEditable(cb.isChecked());
                    todo2.getEditable(isChecked);
                    //todoArrayList.get(position).getEditable(cb.isChecked());
                    todoArrayList.get(position).getEditable(isChecked);
                    holder.checkBox.setChecked(isChecked);

                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /**todo build an dialog listener**/
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setMessage("Edit Entry").setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
             return true;    
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoArrayList.size();
    }

    void removeItem(TODO todo) {
        todoArrayList.remove(todo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
       //implement click listener
        private TextView nameTV,noteTV;
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
