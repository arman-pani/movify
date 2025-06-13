package services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Log or show a notification
        if (remoteMessage.getNotification() != null) {
            Log.d("FCM", "Message Notification: " + remoteMessage.getNotification().getBody());
            // You can show a custom notification here
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM", "Refreshed token: " + token);
        // Send token to your server if needed
    }
}
