package com.example.lastdiaryapp.AddOrEditEventCrl;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.lastdiaryapp.FragmentCrl.FragmentImage;
import com.example.lastdiaryapp.R;
import com.example.lastdiaryapp.TabCrlActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String event=intent.getStringExtra("thongbao");
        String noidung=intent.getStringExtra("noidung");
        int notId=intent.getIntExtra("DataEvent",0);
        Intent activityResult=new Intent(context, FragmentImage.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,activityResult,PendingIntent.FLAG_ONE_SHOT);

        String channelID="channel_ID";
        CharSequence name="channel_name";
        String description="description";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(channelID,name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.BigTextStyle bigText=new NotificationCompat.BigTextStyle();
        bigText.bigText(noidung.toString().trim());
        Notification notification=new NotificationCompat.Builder(context,channelID)
                .setSmallIcon(R.mipmap.ic_logo_foreground)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(event)
                .setContentText("Nhắc nhở "+noidung)
                .setDeleteIntent(pendingIntent)
                .setGroup("Nhắc nhớ")
                .build();
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notId,notification);
    }
}
