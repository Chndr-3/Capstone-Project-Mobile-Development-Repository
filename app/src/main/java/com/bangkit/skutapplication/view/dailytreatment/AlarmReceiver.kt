package com.bangkit.skutapplication.view.dailytreatment


import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bangkit.skutapplication.R


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val repeating_Intent = Intent(context, NotificationActivity::class.java)
        repeating_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val photo = intent?.getStringExtra("photo")
        val name = intent?.getStringExtra("name")
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            repeating_Intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_face_retouching_natural_24)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_baseline_face_retouching_natural_24))
                .setContentTitle(name)
                .setContentText("This is a daily notification")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }
}