package com.radhio.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.radhio.myapplication.Adapter.CustomAdapter;
import com.radhio.myapplication.EndPoints.GetDataService;
import com.radhio.myapplication.Entity.RetroPhoto;
import com.radhio.myapplication.R;
import com.radhio.myapplication.Services.RetrofitClientInstance;
import com.radhio.myapplication.WorkManager.WorkerA;
import com.radhio.myapplication.WorkManager.WorkerB;
import com.radhio.myapplication.WorkManager.WorkerC;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class WorkManagerActivity extends AppCompatActivity {
    public static final String TAG = "WorkManagerActivity";
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    static Response<List<RetroPhoto>> listResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);
        recyclerView = findViewById(R.id.recycler);
        setContent();
    }


    private void setContent() {
        progressDialog = new ProgressDialog(WorkManagerActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ChainWorkRequest();
    }

    public static void callApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<RetroPhoto>> call = service.getAllPhotos();
        try {
            listResponse = call.execute();
            Log.d(TAG, "API CALL DONE");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        call.enqueue(new Callback<List<RetroPhoto>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<RetroPhoto>> call, @NonNull Response<List<RetroPhoto>> response) {
////                generateDataList(response.body());
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d(TAG, "API CALL DONE");
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<RetroPhoto>> call, @NonNull Throwable t) {
////                Toast.makeText(WorkManagerActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void OneTimeWorkManager() {
/*        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();*/
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerA.class)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(oneTimeWorkRequest);
    }

    private void PeriodicWorkRequest() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                WorkerA.class, 1, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest);
    }

    private void ChainWorkRequest() {

        //creating a data object
        //to pass the data with workRequest
        //we can put as many variables needed
        Data data = new Data.Builder()
                .putString(WorkerB.TAG, "The task data passed from MainActivity")
                .build();

        OneTimeWorkRequest oneTimeWorkRequestA = new OneTimeWorkRequest.Builder(WorkerA.class).build();
        OneTimeWorkRequest oneTimeWorkRequestB = new OneTimeWorkRequest.Builder(WorkerB.class)
                .setInputData(data)
                .build();
        OneTimeWorkRequest oneTimeWorkRequestC = new OneTimeWorkRequest.Builder(WorkerC.class).build();

        /*synchronization Request*/
/*        WorkManager.getInstance(getApplicationContext())
                .beginWith(oneTimeWorkRequestA)
                .then(oneTimeWorkRequestB)
                .then(oneTimeWorkRequestC)
                .enqueue();*/

        /*asynchronization Request*/
        WorkManager.getInstance(getApplicationContext())
                .beginWith(Arrays.asList(oneTimeWorkRequestA, oneTimeWorkRequestB))
                .then(oneTimeWorkRequestC)
                .enqueue();

        //Listening to the work status
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequestB.getId())
                .observe(this, workInfo -> {
                    //receiving back the data
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        progressDialog.dismiss();
                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), listResponse.body());
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        Log.d(TAG, "workB Layout Done!");
                    }
//                        textView.append(workInfo.getOutputData().getString(WorkerB.TAG) + "\n");
                    //Displaying the status into TextView
//                        textView.append(workInfo.getState().name() + "\n");

                });
    }
}