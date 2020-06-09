package com.example.myapplication.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.ForecastRepo
import com.example.myapplication.Location
import com.example.myapplication.LocationRepo

import com.example.myapplication.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var locationRepo:LocationRepo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationRepo= LocationRepo(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)
        val input: EditText = view.findViewById(R.id.input)
        val button: Button = view.findViewById(R.id.button)

        button.setOnClickListener {
            val code: String = input.text.toString()
            if (code.length == 6) {
                Toast.makeText(requireContext(), code, Toast.LENGTH_SHORT).show()
            } else {
                locationRepo.saveLocation(Location.Zipcode(code))
                findNavController().navigateUp()
            }
        }
        return view
    }


}
