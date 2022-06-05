package com.bangkit.skutapplication.view.dailytreatment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.DailyRoutine

class SkincareRoutineAdapter(private val listTips: List<DailyRoutine>) : RecyclerView.Adapter<SkincareRoutineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.daily_routine_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tips = listTips[position]
        viewHolder.dailyRoutine.text = tips.dailyRoutine
        viewHolder.image.setImageResource(tips.dailyRoutineIcon)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.dailyRoutineIcon)
        val dailyRoutine: TextView = view.findViewById(R.id.dailyRoutine)
    }

    override fun getItemCount() = listTips.size


}