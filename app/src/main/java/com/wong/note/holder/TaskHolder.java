package com.wong.note.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wong.note.R;
import com.wong.note.adapter.TaskAdapter;
import com.wong.note.base.BaseApplication;
import com.wong.note.greendao.entity.TaskEntity;
import com.wong.note.layout.SwipeItemLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_title)TextView mTvTitle;
    @BindView(R.id.tv_time)TextView mTvTime;
    @BindView(R.id.tv_days)TextView mTvDays;
    @BindView(R.id.tv_check)TextView mTvCheck;
    @BindView(R.id.sil_task_main)SwipeItemLayout mSilLayout;

    private TaskEntity entity;
    private RecyclerView.Adapter adapter;
    private int position;
    private TaskAdapter.DeleteCallBack callBack;

    public TaskHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(TaskEntity entity, RecyclerView.Adapter adapter, TaskAdapter.DeleteCallBack callBack, int position){

        this.callBack = callBack;
        this.adapter = adapter;
        this.position = position;
        this.entity = entity;
        mTvTitle.setText(entity.getTitle());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        mTvTime.setText(df.format(new Date(entity.getTime())));
        String days = String.valueOf(entity.getDays())+"天";
        mTvDays.setText(days);
        if(entity.getStatus() == 1){
            mTvCheck.setEnabled(false);
        }else {
            mTvCheck.setEnabled(true);
        }


    }

    @OnClick(R.id.tv_check) void check(){

        if(entity != null && adapter != null){
            entity.setTime(new Date().getTime());
            entity.setDays(entity.getDays()+1);
            entity.setStatus(1);
            BaseApplication.getInstance().getDaoSession().getTaskEntityDao().update(entity);
            adapter.notifyItemChanged(position);
        }
    }

    @OnClick(R.id.delete) void delete(){
        if(entity != null && callBack != null && adapter != null){
            BaseApplication.getInstance().getDaoSession().getTaskEntityDao().delete(entity);
            callBack.delete(position);
            adapter.notifyItemChanged(position);
        }
    }

}
