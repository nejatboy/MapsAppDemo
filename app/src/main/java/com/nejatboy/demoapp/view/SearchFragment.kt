package com.nejatboy.demoapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.adapter.PlaceRecyclerAdapter
import com.nejatboy.demoapp.model.Location
import com.nejatboy.demoapp.model.Place
import com.nejatboy.demoapp.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private var currentLocation: Location? = null
    private var places = ArrayList<Place>()

    private val MY_API_KEY = "AIzaSyCd0Ruh6iW2OeT8UoEyQ493ZifqHH9eHbE"
    private val RADIUS = 1500       // Yarıçap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        observeLiveData()

        buttonSearch.setOnClickListener(buttonSearchClicked)

    }


    override fun onResume() {
        super.onResume()
        viewModel.isLocationOn()        //Konum açık mı
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty()) {
                context?.let {context ->
                    if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {     //İlk izin verilmesi durumu
                        viewModel.getCurrentLocation()
                    } else {
                        exitProcess(0)
                    }
                }
            }
        }
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
            } else {        //Konum açıksa
                requestLocationPermission()
            }
        })

        viewModel.places.observe(viewLifecycleOwner, Observer {
            places.clear()
            places.addAll(it)
            println(it.size)
            showPlaces()
        })

        viewModel.currentLocation.observe(viewLifecycleOwner, Observer {
            currentLocation = it
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })

        viewModel.isThereError.observe(viewLifecycleOwner, Observer {
            if (it) {
                textViewError.visibility = View.VISIBLE
            } else {
                textViewError.visibility = View.GONE
            }
        })
    }


    private fun requestLocationPermission() {
        activity?.let {activity  ->
            context?.let {context ->
                if (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)

                } else {        //İzin var
                    viewModel.getCurrentLocation()
                }
            }
        }
    }


    private val buttonSearchClicked = View.OnClickListener {

        currentLocation?.let {currentLocation ->
            val keyword = editTextSearch.text.toString().toLowerCase(Locale.getDefault())

            if (keyword.isEmpty()) {
                editTextSearch.error = "Lütfen aramak istediğiniz mekanı yazınız."
                return@OnClickListener
            }

            val lat = currentLocation.lat.toString()
            val lng = currentLocation.lng.toString()
            val location = "$lat,$lng"

            viewModel.getData(location, RADIUS, keyword, MY_API_KEY)
            editTextSearch.setText("")

        } ?: run {
            Toast.makeText(context, "Konumunuz alınamadı lütfen daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun showPlaces() {
        val adapter = PlaceRecyclerAdapter(places)
        recyclerView.adapter = adapter
    }
}