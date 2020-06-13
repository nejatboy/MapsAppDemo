package com.nejatboy.demoapp.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import com.nejatboy.demoapp.model.Comment
import com.nejatboy.demoapp.model.Location
import com.nejatboy.demoapp.model.Place
import com.nejatboy.demoapp.service.MyAPIService
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.system.exitProcess

class SearchViewModel(application: Application) : BaseViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val places = MutableLiveData<List<Place>>()
    val isLocationClose = MutableLiveData<Boolean>()
    val currentLocation = MutableLiveData<Location>()


    fun runData() {
        getDataFromAPI()
    }


    fun isLocationOn() {      // Kullanıcı konumunu açmamışsa uyarı verir
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


    private fun getDataFromAPI () {
        launch {
            val response = MyAPIService().getDataFromAPI()
            if (response.isSuccessful) {
                response.body()?.let {
                    places.value = it.results
                }
            }
        }
    }


    
}