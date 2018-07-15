package com.wong.note.base;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.tencent.bugly.crashreport.CrashReport;
import com.wong.note.greendao.gen.DaoMaster;
import com.wong.note.greendao.gen.DaoSession;
import com.wong.note.service.UpdateTaskStatusService;

public class BaseApplication extends Application {


    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    //静态单例
    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //第三个参数为SDK调试模式开关
        CrashReport.initCrashReport(getApplicationContext(), "0d709ad329", false);
        instance = this;
        initDatabase();
        startUpdateTaskStatusService();

    }

    private void initDatabase(){
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }

    public static BaseApplication getInstance(){
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


    private void startUpdateTaskStatusService(){
        //启动Service
        Intent updateService = new Intent(this, UpdateTaskStatusService.class);
        startService(updateService);
    }
}
