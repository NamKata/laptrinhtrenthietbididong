package com.example.lastdiaryapp.AddOrEditEventCrl;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lastdiaryapp.R;
import com.example.lastdiaryapp.TabCrlActivity;

public class GetNotificalinEvent extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String event=intent.getStringExtra("title");
        String time=intent.getStringExtra("time");
        int notId=intent.getIntExtra("id",0);
        Intent activityIntent=new Intent(context, TabCrlActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,activityIntent,PendingIntent.FLAG_ONE_SHOT);

        String chanelId="channel_id";
        CharSequence name="channel_name";
        String times="content";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(chanelId,name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(times);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification=new NotificationCompat.Builder(context,chanelId)
                .setSmallIcon(R.mipmap.ic_logo)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(event)
                .setContentText(time)
                .setDeleteIntent(pendingIntent)
                .setGroup("Group_calendar_view")
                .build();
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notId,notification);
    }
}
