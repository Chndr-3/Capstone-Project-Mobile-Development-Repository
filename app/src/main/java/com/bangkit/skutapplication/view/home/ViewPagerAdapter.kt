package com.bangkit.skutapplication.view.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.model.ViewPagerItem
import com.bangkit.skutapplication.view.disease.DiseaseActivity

class ViewPagerAdapter(private val listDisease: List<ViewPagerItem>) :
    RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemDiseaseName : TextView = itemView.findViewById(R.id.diseaseName)
        val itemDiseaseDescription: TextView = itemView.findViewById(R.id.diseaseDescription)
        fun bind(list: ViewPagerItem) {
            itemDiseaseName.text = list.diseaseName
            itemDiseaseDescription.text = list.diseaseDescription
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DiseaseActivity::class.java)
                intent.putExtra("LIST", list)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(itemDiseaseName, "name"),
                        Pair(itemDiseaseDescription, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val list = listDisease[position]
        holder.itemDiseaseName.text = list.diseaseName
        holder.itemDiseaseDescription.text = list.diseaseDescription
        holder.bind(listDisease[position])
    }

    override fun getItemCount(): Int {
        return listDisease.size
    }

}