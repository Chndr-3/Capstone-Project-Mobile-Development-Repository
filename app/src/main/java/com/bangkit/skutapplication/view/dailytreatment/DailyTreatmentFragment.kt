package com.bangkit.skutapplication.view.dailytreatment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.FragmentDailyTreatmentBinding
import com.bangkit.skutapplication.model.BeautyTipsItem
import com.bangkit.skutapplication.model.DailyRoutine
import java.util.ArrayList


class DailyTreatmentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentDailyTreatmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyTreatmentBinding.inflate(inflater, container, false)
        return binding.getRoot();
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.acne.setOnClickListener{
            setData(listAcneDailyRoutine)
        }
        binding.rosacea.setOnClickListener {
            setData(listRosaceaDailyRoutine)
        }
        binding.cancer.setOnClickListener{
            setData(listSkinCancerDailyRoutine)
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
    private val listSkinCancerDailyRoutine: ArrayList<DailyRoutine>
        get() {
            val dailyRoutine = resources.getStringArray(R.array.skinCancerDailyRoutine)
            val dailyRoutineIcon = resources.obtainTypedArray(R.array.skinCancerDailyRoutineIcon)
            val listDailyRoutine = ArrayList<DailyRoutine>()
            for (i in dailyRoutine.indices) {
                val tips = DailyRoutine(dailyRoutine[i],dailyRoutineIcon.getResourceId(i, -1))
                listDailyRoutine.add(tips)
            }
            return listDailyRoutine
        }


}