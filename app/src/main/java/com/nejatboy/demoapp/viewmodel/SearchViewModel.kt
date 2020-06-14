package com.nejatboy.demoapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.nejatboy.demoapp.model.Location
import com.nejatboy.demoapp.model.Place
import com.nejatboy.demoapp.service.MyAPIService
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


    val places = MutableLiveData<List<Place>>()
    val isLocationClose = MutableLiveData<Boolean>()
    val currentLocation = MutableLiveData<Location>()
    val isLoading = MutableLiveData<Boolean>()
    val isThereError = MutableLiveData<Boolean>()


    fun getData(location:String, radius:Int, keyword:String, key:String) {
        getDataFromAPI(location, radius, keyword, key)
    }


    fun isLocationOn() {
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (!gpsEnabled && !networkEnabled) {       //Kapalı
            isLocationClose.value = true
        } else {        //Açık
            isLocationClose.value = false
        }
    }


    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20, 20f, locationListener)   //Konum al (Düşük  doğruluk)
    }


    private fun getDataFromAPI (location:String, radius:Int, keyword:String, key:String) {
        launch {
            isLoading.value = true
            val response = MyAPIService().getDataFromAPI(location, radius, keyword, key)
            if (response.isSuccessful) {
                response.body()?.let {
                    places.value = it.results
                    isLoading.value = false
                    isThereError.value = false
                } ?: run {
                    isLoading.value = false
                    isThereError.value = true
                }
            } else {
                isLoading.value = false
                isThereError.value = true
            }
        }
    }


    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: android.location.Location?) {
            location?.let {
                currentLocation.value = Location(it.latitude, it.longitude)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }
}