package khan.solution;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import khan.solution.Activities.CustomerNavigationActivity;
import khan.solution.Fragments.AdminOrderFragment;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

//    private String name ,email, phone,  address,  total_bill, message;
//    private Context context;
//
//    public MyFirebaseInstanceIDService() {
//    }
//
//    public MyFirebaseInstanceIDService(String name, String email, String phone, String address, String total_bill, Context context) {
//        this.name = name;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.total_bill = total_bill;
//        this.context=context;
//    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }

//    public void message() {
//         message= "MyFirebaseInstanceIDService{" +
//                "name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                ", address='" + address + '\'' +
//                ", total_bill='" + total_bill + '\'' +
//                '}';
//         showNotification(message);
//    }

    public void showNotification(String messages){

        PendingIntent pendingIntent=PendingIntent.
                getActivity(this,0,
                        new Intent(this, CustomerNavigationActivity.class),0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("New Order Placed")
                .setContentText(messages)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
}
