package com.bangkit.skutapplication.view.history

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.BeautyTipsItem
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bumptech.glide.Glide
import kotlin.math.absoluteValue

class FaceScanHistoryAdapter(private val listHistory: List<ListHistoryFaceItem>) : RecyclerView.Adapter<FaceScanHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.history_item, viewGroup, false)
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val history = listHistory[position]
        val time = history.timestamp.toString().substring(11, 18)
        val date = history.timestamp.toString().substring(0, 9)
        viewHolder.historyTime.text = time
        viewHolder.historyDate.text = date
        Glide.with(viewHolder.itemView)
            .load(history.imgLink.toString())
            .into(viewHolder.historyImage)
        val map: Map<String, Double?> = mapOf("Acne" to history.acne, "Rosacea" to history.rosacea, "Eksim" to history.eksim, "Normal" to history.normal)
        val maxValue = map.maxOf { it.value!! }
        val keys = map.filterValues { it == maxValue }.keys.first().toString()
        val numberPercentage = (maxValue * 100).toString().substring(0,2)
        viewHolder.historyResult.text = "$keys : $numberPercentage%"
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val historyResult: TextView = view.findViewById(R.id.historyResult)
        val historyImage: ImageView = view.findViewById(R.id.historyImage)
        val historyTime: TextView = view.findViewById(R.id.historyTime)
        val historyDate: TextView = view.findViewById(R.id.historyDate)
    }

    override fun getItemCount() = listHistory.size
}