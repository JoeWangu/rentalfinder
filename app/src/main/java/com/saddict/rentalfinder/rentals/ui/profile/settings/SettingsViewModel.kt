package com.saddict.rentalfinder.rentals.ui.profile.settings

import androidx.lifecycle.ViewModel
import com.saddict.rentalfinder.rentals.data.manager.PreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {
    suspend fun logout() {
        preferenceDataStore.setToken("")
    }
}