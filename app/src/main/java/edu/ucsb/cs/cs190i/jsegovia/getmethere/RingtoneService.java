package edu.ucsb.cs.cs190i.jsegovia.getmethere;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import static edu.ucsb.cs.cs190i.jsegovia.getmethere.MainActivity.appContext;

/**
 * Created by JSegovia on 6/11/17.
 */

public class RingtoneService extends Service {
    public static final String URI_BASE = RingtoneService.class.getName() + ".";
    public static final String ACTION_DISMISS = URI_BASE + "ACTION_DISMISS";

    private int id;

    Vibrator vibrator;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        vibrator = (Vibrator)appContext.getSystemService(VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if(ACTION_DISMISS.equals(action)){
            id = intent.getIntExtra("ID", 0);
            Intent intent1 = new Intent(this, RingtoneService.class);
            stopService(intent1);

            intent1 = new Intent(appContext, AlarmReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, id, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(appContext.NOTIFICATION_SERVICE);
            notificationManager.cancel(id);
        }
        else {
            vibrator.vibrate(60000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        vibrator.cancel();
    }
}



















