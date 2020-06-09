package com.example.myapplication.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.api.CurrentWeather
import com.example.myapplication.api.DailyForecast

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_current_forecast.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()

    private lateinit var locationRepo:LocationRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener{
            showLocationEntry()
        }

        val locationName=view.findViewById<TextView>(R.id.locationName)

        val tempView=view.findViewById<TextView>(R.id.tempView)

        //* observe the location change
        locationRepo= LocationRepo(requireContext())
        val savedLocationObserver=Observer<Location>{
            when(it){
                is Location.Zipcode -> forecastRepo.loadCurrentForecast(it.zipcode)
            }
        }
        locationRepo.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)
        //* endObserve


        val currentWeatherObserver = Observer<CurrentWeather> { item ->
            locationName.text=item.name
            tempView.text=item.forecast.temp.toString()
        }

        forecastRepo.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)



        return view
    }

    private fun showLocationEntry(){
        val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }


//    private fun showForecastDetails(item: DailyForecast) {
//    val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailFragment(item.temp,item.desc)
//    findNavController().navigate(action)
//}

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(code: String): CurrentForecastFragment {
            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, code)

            fragment.arguments = args

            return fragment
        }

    }
}
