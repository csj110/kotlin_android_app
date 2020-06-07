package com.example.myapplication.forecast

import android.content.Context
import android.content.Intent
import android.database.CursorIndexOutOfBoundsException
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

import com.example.myapplication.details.ForecastDetailFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

    private val forecastRepo = ForecastRepo()

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

        val code = arguments?.getString(KEY_ZIPCODE) ?: ""


        val forecastList: RecyclerView = view.findViewById(R.id.list)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter() {
            showForecastDetails(it)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForeCast>> { items ->
            dailyForecastAdapter.submitList(items)
        }

        forecastRepo.weeklyForecast.observe(this, weeklyForecastObserver)

        forecastRepo.loadForecast(code)
        return view
    }

    private fun showLocationEntry(){
        val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }


    private fun showForecastDetails(item: DailyForeCast) {
        val action=CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailFragment(item.temp,item.desc)
        findNavController().navigate(action)
    }

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
