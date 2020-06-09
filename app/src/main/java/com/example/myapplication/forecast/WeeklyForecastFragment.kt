package com.example.myapplication.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*

import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class WeeklyForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()

    private lateinit var locationRepo:LocationRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener{
            showLocationEntry()
        }

        //* observe the location change
        locationRepo= LocationRepo(requireContext())
        val savedLocationObserver=Observer<Location>{
            when(it){
                is Location.Zipcode -> forecastRepo.loadWeeklyForecast(it.zipcode)
            }
        }
        locationRepo.savedLocation.observe(viewLifecycleOwner,savedLocationObserver)
        //* endObserve

        val forecastList: RecyclerView = view.findViewById(R.id.list)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter() {
            showForecastDetails(it)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForeCast>> { items ->
            dailyForecastAdapter.submitList(items)
        }

        forecastRepo.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)


        locationRepo= LocationRepo(requireContext())
        return view
    }
    private fun showLocationEntry(){
        val action=WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }


    private fun showForecastDetails(item: DailyForeCast) {
        val action=WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailFragment(item.temp,item.desc)
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(code: String): WeeklyForecastFragment {
            val fragment = WeeklyForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, code)

            fragment.arguments = args

            return fragment
        }

    }
}
