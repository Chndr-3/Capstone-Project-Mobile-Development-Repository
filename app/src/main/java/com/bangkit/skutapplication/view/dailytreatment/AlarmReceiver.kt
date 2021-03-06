package com.bangkit.skutapplication.view.dailytreatment


import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bangkit.skutapplication.R


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val repeatingIntent = Intent(context, DailyTreatmentActivity::class.java)
        repeatingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val name = intent?.getStringExtra("name")
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        repeatingIntent.putExtra("tab2", "1")
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            repeatingIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_face_retouching_natural_24)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_baseline_face_retouching_natural_24))
                .setContentTitle(titleChecker(name))
                .setContentText(contentTextChecker(name))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(uri)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(200, builder.build())
    }

    private fun titleChecker(item: String?): String{
        return item ?: "Daily Routine Reminder"
        }
    }

    private fun contentTextChecker(item: String?): String{
        return if(item != null){
            "This is the time for $item skincare routine"
        } else{
            "Don't forget to check your daily routine"
        }
}