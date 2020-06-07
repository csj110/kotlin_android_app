package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(view:View) :RecyclerView.ViewHolder(view){
    private val temp=view.findViewById<TextView>(R.id.temp)
    private val desc=view.findViewById<TextView>(R.id.desc)

    fun bind(dailyForeCast: DailyForeCast){
        temp.text= String.format("%05.2f",dailyForeCast.temp)
        desc.text=dailyForeCast.desc
    }
}

class DailyForecastAdapter(
   private val  onItemClick:(item:DailyForeCast)->Unit
) :ListAdapter<DailyForeCast,DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object{
        val DIFF_CONFIG= object:DiffUtil.ItemCallback<DailyForeCast>(){
            override fun areItemsTheSame(oldItem: DailyForeCast, newItem: DailyForeCast) = oldItem===newItem
            override fun areContentsTheSame(oldItem: DailyForeCast, newItem: DailyForeCast) = oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_daily_forecast,parent,false)

        return  DailyForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            onItemClick(getItem(position))
        }
    }
}