package com.nejatboy.demoapp.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.model.Place
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private lateinit var selectedPlace: Place
    private lateinit var  toLeftAnim: Animation
    private lateinit var  toRightAnim: Animation
    private var buttonDetailsIsGone = false


    private val callback = OnMapReadyCallback { googleMap ->
        arguments?.let {bundle ->
            val args = MapsFragmentArgs.fromBundle(bundle)
            selectedPlace = args.selectedPlace
            val latLng = LatLng(selectedPlace.geometry.location.lat, selectedPlace.geometry.location.lng)
            googleMap.addMarker(MarkerOptions().position(latLng).title(selectedPlace.name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

            googleMap.setOnMarkerClickListener(this)
            googleMap.setOnMapClickListener(this)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        setupAnimations()

        buttonDetail.setOnClickListener(buttonDetailsClicked)
    }


    private fun setupAnimations() {
        toLeftAnim = AnimationUtils.loadAnimation(context, R.anim.to_left)
        toLeftAnim.fillAfter = true

        toRightAnim = AnimationUtils.loadAnimation(context, R.anim.to_right)
        toRightAnim.fillAfter = true

        buttonDetail.startAnimation(toLeftAnim)
        buttonDetailsIsGone = true
    }



    override fun onMarkerClick(marker: Marker?): Boolean {
        if (buttonDetailsIsGone) {
            buttonDetail.startAnimation(toRightAnim)
            buttonDetailsIsGone = false
        }
        return false
    }


    override fun onMapClick(latLng: LatLng?) {
        if (!buttonDetailsIsGone) {
            buttonDetail.startAnimation(toLeftAnim)
            buttonDetailsIsGone = true
        }
    }


    private val buttonDetailsClicked = View.OnClickListener {
        val action = MapsFragmentDirections.actionMapsFragmentToDetailFragment(selectedPlace)
        Navigation.findNavController(it).navigate(action)
    }
}