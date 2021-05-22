package com.radhio.myapplication.WorkManager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkerC  extends Worker {

    private static final String TAG = "WorkerC";

    public WorkerC(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show());
        Log.d(TAG, "workC Finished!");
        return Result.success();
    }
}
