package com.woosik.fcm_test;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM__", "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("FCM__", "FCM_TOKEN:" + token);
                        Toast.makeText(MainActivity.this, "FCM_TOKEN:" + token, Toast.LENGTH_LONG).show();

                        // TODO 아래 API로 messaging service token 등록 하기.
                        /*
                        HTTP-POST
                        http://129.254.194.138:8080/v1/ms/register
                        application/json
                        {
                            "holder": "did:icon:01:22222222",
                            "msToken": token
                        }
                         */

                    }
                });
    }
}
