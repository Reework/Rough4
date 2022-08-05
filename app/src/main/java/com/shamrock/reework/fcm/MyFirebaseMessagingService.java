package com.shamrock.reework.fcm;

import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.common.Helper;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.MyNotificationManager;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public static String notification = "", strNotification = "";
    public static String strNewTken = "";
    Context context;
    MyNotificationManager myNotificationManager;
    SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(this);
        myNotificationManager = new MyNotificationManager(this);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        // Saving Device id to shared preferences
        sessionManager.setStringValue(SessionManager.KEY_FCM_DEVICE_ID, s);
        Log.d("FCM Token", s);

        // Notify UI that registration has completed.
        Intent registrationComplete = new Intent(FcmConstants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", s);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "RemoteMessage:----> " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a data payload.
        Log.i("BODy", remoteMessage.getData().toString());

        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData().toString());

                if (sessionManager.isLoggedIn()) {
                    handleDataMessage(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        try {
            JSONObject data = json.getJSONObject("data");

            // extract data from JSON
            String title = data.getString("title");
            String message = data.getString("body");
            int count = data.getInt("count");


            if (message.trim().equalsIgnoreCase("Your account has been unfreezed"))
            {
                if (sessionManager!=null){
                    sessionManager.setStringValue("KeyIsFreezed", "false");



                }
            }


            if (message.equalsIgnoreCase("Your account has been freezed, Please contact to administrator"))
            {
                if (sessionManager!=null){
                    sessionManager.setStringValue("KeyIsFreezed", "true");


                    if (Helper.isAppRunning(this, "com.shamrock")) {
                        Intent intent = new Intent(this, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(intent);
                    } else {
                        // App is not running
                    }

                }
            } else {
//                if (sessionManager!=null){
//                    sessionManager.setStringValue("KeyIsFreezed", "false");
////
//                }

            }

            if (message.contains("Reecoach")&&message.contains("is assigned to you")){
                if (sessionManager!=null){
                    sessionManager.setStringValue("KeyAssingReecoach","true");
                }

            }


            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.setAction(FcmConstants.PUSH_NOTIFICATION);
            intent.putExtra(FcmConstants.FCM_TITLE, title);
            intent.putExtra(FcmConstants.FCM_MESSEGE, message);
            intent.putExtra(FcmConstants.FCM_COUNT, count);
            intent.putExtra("FromNoitification", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            myNotificationManager.showSmallNotification(MyFirebaseMessagingService.this, title, message, intent);
        } catch (JSONException e) {
            Log.i(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.i(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

   /* private void sendPushNotification(int messageTypeID, String message)
    {
        String title = "DesiMechi";

        MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        Bundle bundle = new Bundle();

        if (messageTypeID == 0 || messageTypeID == 1)
        {
            intent = new Intent(getApplicationContext(), MyOrderActivity.class);
        }

        if (messageTypeID == 2 || messageTypeID == 3)
        {
            intent = new Intent(getApplicationContext(), ActivityReferralList.class);
        }

        if (messageTypeID == 4)
        {
            bundle.putBoolean("ProfileID", true);
            intent = new Intent(getApplicationContext(), MyProfileActivity.class);
        }

        mNotificationManager.showSmallNotification(title, message, intent);

    }*/

  /*  private void handleDataMessage(JSONObject json)
    {
        Random random = new Random();

        try
        {
            // Data contain data
            JSONObject data = json.getJSONObject("data");

            // extract data from
            String title = data.getString("title");
            String message = data.getString("message");
            String action = data.getString("action");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = new NotificationChannel(FcmConstants.CHANNEL_ID, FcmConstants.CHANNEL_NAME, importance);
                mChannel.setDescription(FcmConstants.CHANNEL_DESCRIPTION);
                mChannel.enableLights(true);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(true);

                if (notificationManager != null)
                {
                    notificationManager.createNotificationChannel(mChannel);
                }
            }

            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                notificationBuilder = new NotificationCompat.Builder(this, FcmConstants.CHANNEL_ID)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message)
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setColor(getResources().getColor(R.color.colorPrimary));
            }
            else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentIntent(pendingIntent);
            }
            else
            {
                notificationBuilder = new NotificationCompat.Builder(this);
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle(title).setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setLargeIcon(drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)))
                        .setColor(getResources().getColor(android.R.color.transparent))
                        .setContentText(message)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            if (notificationManager != null)
            {
                notificationManager.notify(random.nextInt(1000), notificationBuilder.build());
            }

            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP, MyFirebaseMessagingService.class.getSimpleName());
            wl.acquire(15000);
        }
        catch (JSONException e)
        {
            Log.i(TAG, "Json Exception: " + e.getMessage());
        }
        catch (Exception e)
        {
            Log.i(TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
}