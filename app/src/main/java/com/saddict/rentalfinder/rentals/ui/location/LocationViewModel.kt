package com.saddict.rentalfinder.rentals.ui.location

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saddict.rentalfinder.rentals.data.remote.remository.RemoteDataSource
import com.saddict.rentalfinder.rentals.model.remote.CityResults
import com.saddict.rentalfinder.rentals.model.remote.CountryResults
import com.saddict.rentalfinder.rentals.model.remote.NeighborhoodResults
import com.saddict.rentalfinder.rentals.model.remote.StateResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : ViewModel() {
    private val _countries = MutableStateFlow<List<CountryResults>>(emptyList())
    val countries: StateFlow<List<CountryResults>> = _countries

    private val _states = MutableStateFlow<List<StateResults>>(emptyList())
    val states: StateFlow<List<StateResults>> = _states

    private val _cities = MutableStateFlow<List<CityResults>>(emptyList())
    val cities: StateFlow<List<CityResults>> = _cities

    private val _neighborhoods = MutableStateFlow<List<NeighborhoodResults>>(emptyList())
    val neighborhoods: StateFlow<List<NeighborhoodResults>> = _neighborhoods

    val selectedCountry = mutableStateOf<CountryResults?>(null)
    val selectedState = mutableStateOf<StateResults?>(null)
    val selectedCity = mutableStateOf<CityResults?>(null)
    val selectedNeighborhood = mutableStateOf<NeighborhoodResults?>(null)

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val countryResponse = remoteDataSource.getCountries()
                    _countries.value = countryResponse.results
                } catch (e: Exception) {
                    Log.e(TAG, "fetchCountries: ${e.message}", e)
                }
            }
        }
    }

    fun onCountrySelected(country: CountryResults) {
        selectedCountry.value = country
        selectedState.value = null
        selectedCity.value = null
        selectedNeighborhood.value = null
        _states.value = emptyList()
        _cities.value = emptyList()
        _neighborhoods.value = emptyList()
        fetchStates(country.id)
    }

    private fun fetchStates(countryId: Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val states = remoteDataSource.getStates(countryId!!).results
                    if (states.isNotEmpty()) {
                        _states.value = states
                    } else {
                        fetchCities(null, countryId)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "fetchStates: ${e.message}", e)
                }
            }
        }
    }

    fun onStateSelected(state: StateResults, country: CountryResults) {
        selectedState.value = state
        selectedCity.value = null
        selectedNeighborhood.value = null
        _cities.value = emptyList()
        _neighborhoods.value = emptyList()
        fetchCities(state.id, country.id)
    }

    private fun fetchCities(stateId: Int?, countryId: Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _cities.value = remoteDataSource.getCities(stateId, countryId!!).results
                } catch (e: Exception) {
                    Log.e(TAG, "fetchCities: ${e.message}", e)
                }
            }
        }
    }

    fun onCitySelected(city: CityResults) {
        selectedCity.value = city
        selectedNeighborhood.value = null
        _neighborhoods.value = emptyList()
        fetchNeighborhoods(city.id)
    }

    private fun fetchNeighborhoods(cityId: Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _neighborhoods.value = remoteDataSource.getNeighborhoods(cityId!!).results
                } catch (e: Exception) {
                    Log.e(TAG, "fetchNeighborhoods: ${e.message}", e)
                }
            }
        }
    }

    fun onNeighborhoodSelected(neighborhood: NeighborhoodResults) {
        selectedNeighborhood.value = neighborhood
    }
}