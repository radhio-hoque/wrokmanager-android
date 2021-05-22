package com.radhio.myapplication.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.radhio.myapplication.R;
import com.radhio.myapplication.WorkManager.WorkerA;
import com.radhio.myapplication.WorkManager.WorkerB;
import com.radhio.myapplication.WorkManager.WorkerC;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private boolean isOpen = false;
    private ConstraintSet layout2;
    private ConstraintLayout constraintLayout;
    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ConstraintSet();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ConstraintSet(){
        ConstraintSet layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        ImageView imageView = findViewById(R.id.profile);
        Button button = findViewById(R.id.button);
        constraintLayout = findViewById(R.id.constraint_layout);

        layout2.clone(this,R.layout.profile_extended);
        layout1.clone(constraintLayout);

        imageView.setOnClickListener(v -> {
            if (!isOpen){
                TransitionManager.beginDelayedTransition(constraintLayout);
                layout2.applyTo(constraintLayout);
                isOpen = !isOpen;
            }
            else {
                TransitionManager.beginDelayedTransition(constraintLayout);
                layout1.applyTo(constraintLayout);
                isOpen = !isOpen;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkManagerActivity.class);
                startActivity(intent);
            }
        });
    }

}