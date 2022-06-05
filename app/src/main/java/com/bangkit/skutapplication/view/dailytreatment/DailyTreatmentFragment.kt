package com.bangkit.skutapplication.view.dailytreatment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentDailyTreatmentBinding
import com.bangkit.skutapplication.model.DailyRoutine
import com.bangkit.skutapplication.model.DailyTreatmentItem
import java.text.SimpleDateFormat
import java.util.*


class DailyTreatmentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentDailyTreatmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDailyTreatmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkTime()
        binding.acne.setOnClickListener{
            setData(listAcneDailyRoutine)
        }
        binding.rosacea.setOnClickListener {
            setData(listRosaceaDailyRoutine)
        }
        binding.eczema.setOnClickListener{
            setData(listEczemaDailyRoutine)
        }
    }

    private fun setData(item: ArrayList<DailyRoutine>){
        val layoutManager = LinearLayoutManager(activity)
        binding.rvDailyRoutine.layoutManager = layoutManager
        binding.rvDailyRoutine.adapter = SkincareRoutineAdapter(item)
    }

    private val listAcneDailyRoutine: ArrayList<DailyRoutine>
        get() {
            val dailyRoutine = resources.getStringArray(R.array.acneDailyRoutine)
            val dailyRoutineIcon = resources.obtainTypedArray(R.array.acneDailyRoutineIcon)
            val listDailyRoutine = ArrayList<DailyRoutine>()
            for (i in dailyRoutine.indices) {
                val tips = DailyRoutine(dailyRoutine[i],dailyRoutineIcon.getResourceId(i, -1))
                listDailyRoutine.add(tips)
            }
            return listDailyRoutine
        }

    private val listRosaceaDailyRoutine: ArrayList<DailyRoutine>
        get() {
            val dailyRoutine = resources.getStringArray(R.array.rosaceaDailyRoutine)
            val dailyRoutineIcon = resources.obtainTypedArray(R.array.rosaceaDailyRoutineIcon)
            val listDailyRoutine = ArrayList<DailyRoutine>()
            for (i in dailyRoutine.indices) {
                val tips = DailyRoutine(dailyRoutine[i],dailyRoutineIcon.getResourceId(i, -1))
                listDailyRoutine.add(tips)
            }
            return listDailyRoutine
        }

    private val listEczemaDailyRoutine: ArrayList<DailyRoutine>
        get() {
            val dailyRoutine = resources.getStringArray(R.array.eczemaDailyRoutine)
            val dailyRoutineIcon = resources.obtainTypedArray(R.array.eczemaDailyRoutineIcon)
            val listDailyRoutine = ArrayList<DailyRoutine>()
            for (i in dailyRoutine.indices) {
                val tips = DailyRoutine(dailyRoutine[i],dailyRoutineIcon.getResourceId(i, -1))
                listDailyRoutine.add(tips)
            }
            return listDailyRoutine
        }
    private fun setCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        calendar.set(Calendar.MINUTE, 0)
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
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
    private fun checkTime(){
        val c = Calendar.getInstance()
        val df = SimpleDateFormat("HH:mm")
        val formattedDate = df.format(c.time)
        val date1: Date = df.parse(formattedDate) as Date
        val date2: Date = df.parse("10:00") as Date
        if (!(date1.after(date2))) {
            setCalendar()
            if (date1 != date2) {
                setCalendar()
            }
        }
    }
}