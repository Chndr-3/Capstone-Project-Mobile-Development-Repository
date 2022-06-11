package com.bangkit.skutapplication.helper

import androidx.recyclerview.widget.DiffUtil
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem

class HistoryDiffCallback(private val mOldItem: List<ListHistoryFaceItem>, private val mNewItem: List<ListHistoryFaceItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldItem.size
    }

    override fun getNewListSize(): Int {
        return mNewItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldItem[oldItemPosition].scanId == mNewItem[newItemPosition].scanId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldItem[oldItemPosition]
        val newItem = mNewItem[newItemPosition]
        return oldItem.scanId == newItem.scanId
    }
}