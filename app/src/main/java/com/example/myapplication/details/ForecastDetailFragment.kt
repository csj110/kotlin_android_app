package com.example.myapplication.details

import android.app.AlertDialog
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentForecastDetailsBinding

class ForecastDetailFragment : Fragment() {

    private val args:ForecastDetailFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory

    private val viewModel : ForecastDetailsViewModel by viewModels(
        factoryProducer = {viewModelFactory}
    )

    private var _binding:FragmentForecastDetailsBinding?=null
    // this property only valid between onCraateView and onDestroyView
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentForecastDetailsBinding.inflate(inflater,container,false)

        viewModelFactory=ForecastDetailsViewModelFactory(args)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailViewState>{
            //update the ui
            binding.temp.text="${it.temp}"
            binding.desc.text=it.description
            binding.detailIcon.load(it.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner,viewStateObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
