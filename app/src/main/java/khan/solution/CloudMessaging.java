package khan.solution;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CloudMessaging extends FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID="MY_NOTIFICATION_ID";
    private FirebaseAuth auth;
    private String user;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Recieving all notification
    auth=FirebaseAuth.getInstance();
    user=auth.getCurrentUser().getUid();

    String notificationType=remoteMessage.getData().get("NotificationType");

    }
}
