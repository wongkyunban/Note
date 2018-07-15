package com.wong.note.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wong.note.R;
import com.wong.note.adapter.TaskAdapter;
import com.wong.note.base.BaseApplication;
import com.wong.note.base.Constant;
import com.wong.note.greendao.entity.TaskEntity;
import com.wong.note.greendao.gen.DaoSession;
import com.wong.note.greendao.gen.TaskEntityDao;
import com.wong.note.layout.SwipeItemLayout;
import com.wong.note.service.UpdateTaskStatusService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_task_main) RecyclerView mRvTaskMain;

    List<TaskEntity> list = new ArrayList<>();
    TaskAdapter adapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Constant.UPDATE_TASK_LIST.equals(intent.getAction())) {
                update();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMainView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });



        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.UPDATE_TASK_LIST);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void addNewTask(){

        new MaterialDialog.Builder(this)
                .title("任务")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请简单描述一下任务内容", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        TaskEntity task = new TaskEntity();
                        long time = new Date().getTime();
                        task.setTime(time);
                        task.setStatus(0);
                        task.setDays(0);
                        task.setLayoutType(TaskAdapter.LAYOUT_TASK);
                        task.setTitle(input.toString());
                        task.setUpdateTime(time);
                        BaseApplication.getInstance().getDaoSession().getTaskEntityDao().insert(task);
                        refresh(task);

                    }
                }).show();
    }
    private void initMainView(){
        initData();
        adapter = new TaskAdapter(list);
        mRvTaskMain.setLayoutManager(new LinearLayoutManager(this));

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.line);
        if(drawable != null){

            divider.setDrawable(drawable);
            mRvTaskMain.addItemDecoration(divider);
        }

        mRvTaskMain.setAdapter(adapter);
        mRvTaskMain.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }
    private void initData(){
        BaseApplication app  = BaseApplication.getInstance();
        DaoSession daoSession = app.getDaoSession();
        TaskEntityDao dao = daoSession.getTaskEntityDao();
        list = dao.loadAll();
    }

    private void refresh(TaskEntity task){
        adapter.addTask(task);
    }
    private void update(){

        list.clear();
        initData();
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
