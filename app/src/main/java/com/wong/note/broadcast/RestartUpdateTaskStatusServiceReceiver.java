package com.wong.note.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wong.note.base.Constant;
import com.wong.note.service.UpdateTaskStatusService;

public class RestartUpdateTaskStatusServiceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (Constant.UPDATE_TASK_STATUS_SERVICE_DESTROY.equals(intent.getAction())) {
            Intent service = new Intent(context,UpdateTaskStatusService.class);
            context.startService(service);
        }
    }
}
