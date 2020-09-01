package com.woosik.fcm_test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d("FCM__", "onNewToken() token=" + token);

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

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM__", "onMessageReceived()" + remoteMessage);
        if(remoteMessage.getNotification() == null) {
            return;
        }
        String msgTitle = remoteMessage.getNotification().getTitle();
        String msgBody = remoteMessage.getNotification().getBody();
        Log.d("FCM__", "Msg:" + remoteMessage.getNotification().getBody());
        Log.d("FCM__", "Msg:" + remoteMessage.getNotification().getTitle());

        // TODO Push message 처리.

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msgTitle)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());

    }
}
