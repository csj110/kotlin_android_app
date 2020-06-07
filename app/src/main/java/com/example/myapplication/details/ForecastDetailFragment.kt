package com.example.myapplication.details

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R

class ForecastDetailFragment : Fragment() {

    private val args:ForecastDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_forecast_details ,container,false)

        val temp=view.findViewById<TextView>(R.id.temp)
        val desc=view.findViewById<TextView>(R.id.desc)
        temp.text="${args.temp} *"
        desc.text=args.desc

        return  view
    }


}
