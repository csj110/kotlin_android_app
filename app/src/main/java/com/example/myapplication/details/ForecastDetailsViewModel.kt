package com.example.myapplication.details


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class ForecastDetailsViewModelFactory(private val args: ForecastDetailFragmentArgs) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)){
            return ForecastDetailsViewModel(args) as T
        }
        throw IllegalArgumentException("unknown viewModel class")
    }
}


class ForecastDetailsViewModel(args: ForecastDetailFragmentArgs) :ViewModel() {

    private val _viewState:MutableLiveData<ForecastDetailViewState> = MutableLiveData()
    val viewState:LiveData<ForecastDetailViewState> = _viewState

    init {
        _viewState.value= ForecastDetailViewState(
            temp = args.temp,
            description = args.desc,
            iconUrl = "http://openweathermap.org/img/wn/${args.iconId}@2x.png"
        )
    }

}