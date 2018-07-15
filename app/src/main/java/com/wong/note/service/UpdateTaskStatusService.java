package com.wong.note.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wong.note.R;
import com.wong.note.base.BaseApplication;
import com.wong.note.base.Constant;
import com.wong.note.greendao.entity.TaskEntity;
import com.wong.note.greendao.gen.DaoSession;
import com.wong.note.greendao.gen.TaskEntityDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateTaskStatusService extends Service {
    public UpdateTaskStatusService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean needUpdate = false;


                    BaseApplication app = BaseApplication.getInstance();
                    DaoSession daoSession = app.getDaoSession();
                    TaskEntityDao dao = daoSession.getTaskEntityDao();
                    List<TaskEntity> list = dao.loadAll();
                    for (TaskEntity e : list) {
                        if (isToday(e.getUpdateTime())) {
                            continue;
                        } else {
                            needUpdate = true;
                            e.setUpdateTime(new Date().getTime());
                            e.setStatus(0);
                            dao.update(e);
                        }

                    }

                    if (needUpdate) {  //发广播更新列表
                        Intent intent2 = new Intent(Constant.UPDATE_TASK_LIST);
                        intent2.setAction(Constant.UPDATE_TASK_LIST);
                        sendBroadcast(intent2);

                    }
                }

            }
        }).start();


        return Service.START_STICKY;  //返回Service.START_STICKY是为不service不会被杀死
    }

    @Override
    public void onDestroy() {
        //service+broadcast方式，就是当service调用到ondestory()的时候，发送一个自定义的广播，当收到广播的时候，重新启动service
        stopForeground(true);
        Intent intent = new Intent(Constant.UPDATE_TASK_STATUS_SERVICE_DESTROY);
        intent.setAction(Constant.UPDATE_TASK_STATUS_SERVICE_DESTROY);
        sendBroadcast(intent);
        super.onDestroy();
    }

    public boolean isToday(long day) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

}
