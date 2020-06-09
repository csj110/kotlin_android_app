package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.myapplication.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATA_FORMAT=SimpleDateFormat("MM-dd-yyyy")

class DailyForecastViewHolder(view:View) :RecyclerView.ViewHolder(view){
    private val temp=view.findViewById<TextView>(R.id.temp)
    private val desc=view.findViewById<TextView>(R.id.desc)
    private val dateText=view.findViewById<TextView>(R.id.dateText)
    private val icon=view.findViewById<ImageView>(R.id.icon)

    fun bind(dailyForeCast: DailyForecast){
        temp.text= String.format("%05.2f",dailyForeCast.temp.max)
        desc.text=dailyForeCast.weather[0].description
        dateText.text=DATA_FORMAT.format(Date(dailyForeCast.date*1000))

        val iconId=dailyForeCast.weather[0].icon
        icon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
    }
}

class DailyForecastAdapter(
   private val  onItemClick:(item:DailyForecast)->Unit
) :ListAdapter<DailyForecast,DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object{
        val DIFF_CONFIG= object:DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast) = oldItem===newItem
            override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast) = oldItem==newItem
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