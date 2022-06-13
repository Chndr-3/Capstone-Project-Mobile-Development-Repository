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
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentDailyTreatmentBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.DailyRoutine
import com.bangkit.skutapplication.view.profile.ProfileViewModel
import java.util.*


class DailyTreatmentFragment : Fragment() {

    private lateinit var binding: FragmentDailyTreatmentBinding
    private lateinit var userViewModel : ProfileViewModel
    private val Context.dataStore by preferencesDataStore(name = "profile")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.setCalendar()
    }
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
        setData(listAcneDailyRoutine)
        binding.acne.setOnCheckedChangeListener { button, b ->
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
        binding.rvDailyRoutine.adapter = DailyRoutineAdapter(item)
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
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
    private fun checkTime(){
        userViewModel.getUser().observe(viewLifecycleOwner){
            if(it.calendar.isEmpty()){
                setCalendar()
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(context.dataStore))
        )[ProfileViewModel::class.java]

    }

}