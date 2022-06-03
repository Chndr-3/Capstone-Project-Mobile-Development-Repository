package com.bangkit.skutapplication.view.dailytreatment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityDailyTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem
import java.text.SimpleDateFormat
import java.util.*


class DailyTreatmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyTreatmentBinding
    private lateinit var dailyTreatmentViewModel: DailyTreatmentViewModel
    private lateinit var adapter: DailyTreatmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_treatment)
        binding = ActivityDailyTreatmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dailyTreatmentViewModel = obtainViewModel(this@DailyTreatmentActivity)
        binding.floatingActionButton.setOnClickListener {
            intent = Intent(this, AddDailyTreatmentActivity::class.java)
            startActivity(intent)
        }
        adapter = DailyTreatmentAdapter()
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        binding.rvItem.setHasFixedSize(true)
        binding.rvItem.adapter = adapter
        NotificationChannel()
        dailyTreatmentViewModel.getAllItem().observe(this) {
            if (it != null) {
                adapter.setListItem(it)
            }
            for(data in it){
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("HH:mm")
                val formattedDate = df.format(c.time)
                val date1: Date = df.parse(formattedDate)
                val date2: Date = df.parse(data.time)
                if(!(date1.after(date2))) {
                    setCalendar(data, data.id)
                    if(!(date1.equals(date2))){
                        setCalendar(data, data.id)
                    }
                }

            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DailyTreatmentViewModel {
        val factory = ItemViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DailyTreatmentViewModel::class.java)
    }

    private fun getHours(time: String?): Int {
            return time?.substringBefore(":")?.toInt() ?: 0
    }

    private fun getMinute(time: String?): Int{
        return time?.substringAfter(":")?.toInt() ?: 0
    }

    private fun setCalendar(item: DailyTreatmentItem, id: Int){
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, getHours(item.time))
        calendar.set(Calendar.MINUTE, getMinute(item.time))
        val intent = Intent(this@DailyTreatmentActivity, AlarmReceiver::class.java)
        intent.putExtra("photo", item.product_image)
        intent.putExtra("name", item.product_name)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
            )
        }
    }
    private fun NotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "PASTICCINO"
            val description = "PASTICCINO`S CHANNEL"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notification", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}