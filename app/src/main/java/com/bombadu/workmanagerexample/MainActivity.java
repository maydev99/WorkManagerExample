package com.bombadu.workmanagerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(getApplicationContext()).enqueue(request);
            }
        });

        final TextView textView = findViewById(R.id.textView);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        String status = workInfo.getState().name();
                        textView.append(status + "\n");
                    }
                });
    }
}
