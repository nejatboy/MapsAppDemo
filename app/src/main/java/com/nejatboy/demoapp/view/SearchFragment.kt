package com.nejatboy.demoapp.view

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.viewmodel.SearchViewModel
import kotlin.system.exitProcess


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        observeLiveData()

        viewModel.runData()
    }


    override fun onResume() {
        super.onResume()
        viewModel.isLocationOn()        //Konum açık mı
    }


    private fun observeLiveData() {
        viewModel.isLocationClose.observe(viewLifecycleOwner, Observer {isLocationClose ->
            if (isLocationClose) {
                context?.let { context ->
                    val alert = AlertDialog.Builder(context)
                    alert.setTitle("Konum Kapalı")
                    alert.setMessage("Uygulamayı kullanabilmek için konumu etkinleştirmelisiniz")
                    alert.setPositiveButton("Ayarlara Git") { _, _ ->
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(intent)
                    }
                    alert.setNegativeButton("İptal") { _, _ ->
                        exitProcess(0)      //System.exit(0)
                    }
                    alert.show()
                }
            }
        })


        viewModel.places.observe(viewLifecycleOwner, Observer {
            for (place in it) {
                //println(place.name)
                //println(place.scope)
            }
        })
    }



}