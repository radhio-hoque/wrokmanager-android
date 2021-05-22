package com.radhio.myapplication.WorkManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.radhio.myapplication.EndPoints.GetDataService;
import com.radhio.myapplication.Entity.RetroPhoto;
import com.radhio.myapplication.Services.RetrofitClientInstance;
import com.radhio.myapplication.View.WorkManagerActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class WorkerB extends Worker {

    public static final String TAG = "WorkerB";

    public WorkerB(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "workB Started!");
        String taskDesc = getInputData().getString(TAG);
        WorkManagerActivity.callApi();
        Log.d(TAG, "workB Done!");
        return Result.success();
//        try {
//            Thread.sleep(5000);
//            //setting output data
//            Data data = new Data.Builder()
//                    .putString(TAG, "The conclusion of the task")
//                    .build();
//            Log.d(TAG, "workB Done!");
//            return Result.success(data);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            Log.d(TAG, "workB Failed!");
//            return Result.failure();
//        }
    }
}
