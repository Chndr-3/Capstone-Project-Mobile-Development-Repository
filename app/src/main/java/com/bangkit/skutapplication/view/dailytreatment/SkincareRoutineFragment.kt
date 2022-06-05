package com.bangkit.skutapplication.view.dailytreatment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.databinding.FragmentSkincareTreatmentBinding
import com.bangkit.skutapplication.helper.ItemViewModelFactory
import com.bangkit.skutapplication.model.DailyTreatmentItem
import java.text.SimpleDateFormat
import java.util.*


class SkincareRoutineFragment : Fragment() {
    private lateinit var binding: FragmentSkincareTreatmentBinding
    private lateinit var skincareTreatmentViewModel: DailyTreatmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        skincareTreatmentViewModel = obtainViewModel(activity as AppCompatActivity)
        binding = FragmentSkincareTreatmentBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSkincareTreatmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(activity, AddDailyTreatmentActivity::class.java)
            startActivity(intent)
        }
        NotificationChannel()
        val adapter = DailyTreatmentAdapter()
        binding.rvItem.layoutManager = LinearLayoutManager(context)
        binding.rvItem.setHasFixedSize(true)
        binding.rvItem.adapter = adapter
        adapter.setOnItemClickCallback(
            object : DailyTreatmentAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DailyTreatmentItem) {
                    skincareTreatmentViewModel.deleteItem(data.id)
                    cancelAlarm(data.id)
                }
            }
        )
        NotificationChannel()
        skincareTreatmentViewModel.getAllItem().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setListItem(it)
            }
            for (data in it) {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("HH:mm")
                val formattedDate = df.format(c.time)
                val date1: Date = df.parse(formattedDate) as Date
                val date2: Date = df.parse(data.time.toString()) as Date
                if (!(date1.after(date2))) {
                    setCalendar(data, data.id)
                    if (!(date1.equals(date2))) {
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

    private fun NotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "PASTICCINO"
            val description = "PASTICCINO`S CHANNEL"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel("Notification", name, importance)
            channel.description = description
            val notificationManager = requireContext().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setCalendar(item: DailyTreatmentItem, id: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, getHours(item.time))
        calendar.set(Calendar.MINUTE, getMinute(item.time))
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("name", item.product_name)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
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

    private fun cancelAlarm(id: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun getHours(time: String?): Int {
        return time?.substringBefore(":")?.toInt() ?: 0
    }

    private fun getMinute(time: String?): Int {
        return time?.substringAfter(":")?.toInt() ?: 0
    }


}