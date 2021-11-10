package com.globallogic.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.globallogic.app.R
import com.globallogic.app.data.Moves
import com.globallogic.app.data.Stats
import com.globallogic.app.databinding.RvItemStatisticsBinding
import com.globallogic.app.databinding.RvMoveItemBinding

class RvStatisticsAdapter : RecyclerView.Adapter<RvStatisticsAdapter.StatisticsViewHolder>() {

    private var stats :List<Stats> = mutableListOf()

    fun setStatisticsData(stats :List<Stats>) {
        this.stats = stats
        notifyDataSetChanged()
    }

    inner class StatisticsViewHolder(private val binding:RvItemStatisticsBinding)
        :RecyclerView.ViewHolder(binding.root) {
            fun onBind(stats:Stats) {
                binding.setVariable(BR.stats_name, stats.stat)
                binding.setVariable(BR.base_stats_value, stats)
                binding.executePendingBindings()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val binding : RvItemStatisticsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context)
            , R.layout.rv_item_statistics, parent, false)
        return StatisticsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
       var stats = stats[position]
        holder.onBind(stats)
    }

    override fun getItemCount(): Int {
        return stats.size
    }
}