package com.bangkit.skutapplication.view.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.helper.HistoryDiffCallback
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bumptech.glide.Glide
import java.util.ArrayList

class FaceScanHistoryAdapter : RecyclerView.Adapter<FaceScanHistoryAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listItem = ArrayList<ListHistoryFaceItem>()
    fun setListItem(listItem: List<ListHistoryFaceItem>) {
        val diffCallback = HistoryDiffCallback(this.listItem, listItem)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listItem.clear()
        this.listItem.addAll(listItem)
        diffResult.dispatchUpdatesTo(this)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.history_item, viewGroup, false)
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val history = listItem[position]
        val time = history.timestamp.toString().substring(12, 20)
        val date = history.timestamp.toString().substring(0, 10)
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
        viewHolder.deleteButton.setOnClickListener {
            onItemClickCallback.onItemClicked(listItem[viewHolder.adapterPosition])
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val historyResult: TextView = view.findViewById(R.id.historyResult)
        val historyImage: ImageView = view.findViewById(R.id.historyImage)
        val historyTime: TextView = view.findViewById(R.id.historyTime)
        val historyDate: TextView = view.findViewById(R.id.historyDate)
        val deleteButton: ImageView = view.findViewById(R.id.buttonDelete)
    }

    override fun getItemCount() = listItem.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ListHistoryFaceItem)
    }
}