package com.nejatboy.demoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.model.Place
import com.nejatboy.demoapp.util.dowloadFromUrl
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var  selectedPlace: Place


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {bundle ->
            val args = MapsFragmentArgs.fromBundle(bundle)
            selectedPlace = args.selectedPlace

            setupInterface()
        }
    }


    private fun setupInterface() {
        textViewName.text = selectedPlace.name
        textViewVicinity.text = selectedPlace.vicinity
        imageViewIcon.dowloadFromUrl(selectedPlace.icon)
        ratingBar.rating = selectedPlace.rating.toFloat()

        val rating = "${selectedPlace.rating} / ${selectedPlace.user_ratings_total}"
        textViewRating.text = rating

        var types = ""
        for (type in selectedPlace.types) {
            types = types + type + "\n"
        }
        println(types)
        textViewTypes.text = types
    }
}