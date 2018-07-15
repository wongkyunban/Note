package com.wong.note.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wong.note.R;
import com.wong.note.greendao.entity.TaskEntity;
import com.wong.note.holder.TaskHolder;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter{

    public final static int LAYOUT_TASK = 1;  //任务

    private List<TaskEntity> list;


    public TaskAdapter(List<TaskEntity> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case LAYOUT_TASK:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
                return new TaskHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case LAYOUT_TASK:
                ((TaskHolder) holder).bind(list.get(position), this,callBack, position);
                break;
        }

    }

    public void addTask(TaskEntity entity) {
        list.add(entity);
        notifyItemChanged(list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getLayoutType();
    }

    public void setData(List<TaskEntity> list){
        this.list.clear();
        this.list = list;
    }
    private DeleteCallBack callBack = new DeleteCallBack() {
        @Override
        public void delete(int position) {
            if (list != null && list.size() > position) {
                list.remove(position);
            }
        }
    };

    public interface DeleteCallBack {
        void delete(int position);
    }


}
