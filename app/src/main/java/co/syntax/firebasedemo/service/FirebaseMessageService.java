package co.syntax.firebasedemo.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import co.syntax.firebasedemo.MainActivity;
import co.syntax.firebasedemo.R;

/**
 * Created by rukarayan on 19-Jan-17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    private static final String LOG_CAT = "FirebaseMessageService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(LOG_CAT, "From: " + remoteMessage.getFrom());
        String msg = remoteMessage.getData().toString();
        Log.d(LOG_CAT, "Message data payload: " + remoteMessage.getData().toString());

        Log.d(LOG_CAT, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        sendNotification(msg);

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon_firebase)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
