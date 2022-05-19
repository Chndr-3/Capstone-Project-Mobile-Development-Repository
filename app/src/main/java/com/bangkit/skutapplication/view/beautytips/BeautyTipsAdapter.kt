package com.bangkit.skutapplication.view.beautytips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.BeautyTipsItem


class BeautyTipsAdapter(private val listTips: List<BeautyTipsItem>) : RecyclerView.Adapter<BeautyTipsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.beauty_tips_item, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val tips = listTips[position]
        viewHolder.number.text = tips.number.toString()
        viewHolder.beautyTips.text = tips.beautyTips
        viewHolder.beautyDescription.text = tips.beautyDescription

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.itemNumber)
        val beautyTips: TextView = view.findViewById(R.id.beautyTips)
        val beautyDescription: TextView = view.findViewById(R.id.beautyDescription)
    }

    override fun getItemCount() = listTips.size


}