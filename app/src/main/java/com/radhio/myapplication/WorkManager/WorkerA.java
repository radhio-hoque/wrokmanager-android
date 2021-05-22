package com.radhio.myapplication.WorkManager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.radhio.myapplication.View.WorkManagerActivity;

public class WorkerA extends Worker {

    public static final String TAG = "WorkerA";

    public WorkerA(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "workA Started!");

        Log.d(TAG, "workA Done!");
        return Result.success();
    }

//    private void fetchInfoFromRepository() {
//        String taskDesc = getInputData().getString(TAG);
//        Log.d(TAG, taskDesc);
//    }
}
