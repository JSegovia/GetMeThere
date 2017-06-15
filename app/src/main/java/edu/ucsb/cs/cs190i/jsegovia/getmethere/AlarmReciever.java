package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by JSegovia on 6/11/17.
 */

public class AlarmReciever extends BroadcastReceiver {
    private int id;
    @Override
    public void onReceive(Context context, Intent intent){
        Intent intent1 = new Intent(context, RingtoneService.class);
        id = intent.getIntExtra("ID", 0);
        intent1.putExtra("ID", id);
        context.startService(intent1);

        createNotification(context);
    }
    private void createNotification(Context context){
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("LEAVE NOW")
                .setContentText("Alarm")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //dismiss button
        Intent dismiss = new Intent(context, RingtoneService.class);
        dismiss.setAction(RingtoneService.ACTION_DISMISS);
        PendingIntent pendingIntent1 = PendingIntent.getService(context, id, dismiss, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action
                (android.R.drawable.ic_lock_idle_alarm,"DISMISS", pendingIntent1);
        builder.addAction(action);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.addAction(action);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(id, notification);

    }
}
